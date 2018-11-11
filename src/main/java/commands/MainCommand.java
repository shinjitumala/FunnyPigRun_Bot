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
  public void addModule(ICommand... command) {
    for (ICommand c : command) {
      try {
        c.getClass().getAnnotation(ACommand.class);
      } catch (NullPointerException e) {
        FPR.logger.fatal("MainCommand: Tried to add invalid module to commands.");
        System.exit(1);
      }
      commands.add(c);
    }
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
    FPR.logger.info(event.getMessage().getAuthor().getDiscriminatedName() + ": " + message);
    // ***************************************************************//
    // Non Commands
    // ***************************************************************//
    // Call leveling system
    FPR.levelcore.chat(event);

    // ***************************************************************//
    // Commands
    // ***************************************************************//
    if (!message.startsWith(PREFIX)) {
      return; // Ignore if message does not have prefix.
    }

    FPR.logger.debug("MainCommand: Command recieved.");
    // New scanner for parsing commands.
    Scanner scanner = new Scanner(message);
    scanner.skip(PREFIX);
    String command = scanner.next().toLowerCase();

    boolean help = false;
    if (command.equals("help")) {
      help = true;
      command = scanner.next().toLowerCase();
    }
    for (ICommand c : commands) {
      ACommand annotation = c.getClass().getAnnotation(ACommand.class);
      if (annotation.command().equals(command)) {
        try {
          if (!help && c.run(event, scanner)) {
            return;
          } else if (help) {
            EmbedBuilder embed = UTemplates
                .helpTemplate(annotation.command(), annotation.help(),
                    FPR.roles.get(annotation.permission().toString()));
            event.getChannel().sendMessage(embed);
            return;
          } else {
            throw new ExCommandException("MainCommand: Command returned with status 1.");
          }
        } catch (ExCommandException e) {
          EmbedBuilder embed = UTemplates.errorTemplate(e.toString(), command);
          event.getChannel().sendMessage(embed);
        }
      }
    }
    FPR.logger.debug("MainCommand: No such command!");
  }
}
