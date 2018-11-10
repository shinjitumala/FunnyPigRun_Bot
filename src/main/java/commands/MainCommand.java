package commands;

import java.util.Optional;
import java.util.Scanner;
import java.util.Vector;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import main.FPR;
import myutils.UTemplates;

public class MainCommand implements MessageCreateListener {
  public static final String PREFIX = "<FPR>";
  private Vector<ICommand>   commands;

  /**
   * Default constructor.
   */
  public MainCommand() {
    commands = new Vector<>();
  }

  /**
   * Adds a new command module.
   *
   * @param command new command module
   */
  public void addModule(ICommand command) {
    try {
      command.getClass().getAnnotation(ACommand.class);
    } catch (NullPointerException e) {
      FPR.log().fatal("MainCommand: Tried to add invalid module to commands.");
      System.exit(1);
    }
    commands.add(command);
  }

  /**
   * Called when a message is created.
   *
   * @param event MessageCreateEvent
   */
  @Override
  public void onMessageCreate(MessageCreateEvent event) {
    // ***************************************************************//
    // Response Conditions
    // ***************************************************************//
    Optional<Server> server = event.getServer();
    if (server.isPresent()) {
      // Ignore messages not from funny.pig.run Gaming.
      if (server.get() != FPR.server()) {
        return;
      }

    } else if (event.isPrivateMessage()) {
      // Do not ignore private messages.
    } else {
      // Ignore if not server message or private message.
      return;
    }

    Optional<User> user = event.getMessage().getUserAuthor();
    if (user.isPresent()) {
      // Ignore messages from bot.
      if (user.get().isBot()) {
        return;
      }
    } else {
      // Ignore if message was from a web hook and such.
      return;
    }

    String message = event.getMessageContent();
    FPR.log().info(event.getMessage().getAuthor().getDiscriminatedName() + ": " + message);
    // ***************************************************************//
    // Non Commands
    // ***************************************************************//
    // Call leveling system
    FPR.level().chat(event);

    // ***************************************************************//
    // Commands
    // ***************************************************************//
    if (!message.startsWith(PREFIX)) {
      return; // Ignore if message does not have prefix.
    }

    FPR.log().debug("MainCommand: Command recieved.");
    // New scanner for parsing commands.
    Scanner scanner = new Scanner(message);
    scanner.skip(PREFIX);
    String command = scanner.next().toLowerCase();

    boolean help = false;
    if (scanner.hasNext()) {
      if (scanner.next().toLowerCase().equals("--help")) {
        help = true;
      }
      scanner.close();
      scanner = new Scanner(message);
    }
    for (ICommand c : commands) {
      ACommand annotation = c.getClass().getAnnotation(ACommand.class);
      if (annotation.command().equals(command)) {
        try {
          if (c.run(event, scanner) && !help) {
            return;
          } else if (help) {
            EmbedBuilder embed = UTemplates
                .helpTemplate(annotation.command(), annotation.help(),
                    FPR.getRole(annotation.permission().toString()));
            event.getChannel().sendMessage(embed);
          } else {
            throw new ExCommandException("MainCommand: Command returned with status 1.");
          }
        } catch (ExCommandException e) {
          EmbedBuilder embed = UTemplates
              .errorTemplate("Error", e.toString())
                .addField("Help", "For more information about the command, use `"
                    + MainCommand.PREFIX + command + " --help`.\n"
                    + "If you think this is a bug, please feel free to create a new issue on github by "
                    + "[clicking here](https://github.com/shinjitumala/FunnyPigRun_Bot/issues).");
          event.getChannel().sendMessage(embed);
        }
      }
    }
    FPR.log().debug("MainCommand: No such command!");
  }
}
