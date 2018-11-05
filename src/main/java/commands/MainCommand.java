package commands;

import java.util.Optional;
import java.util.Scanner;
import java.util.Vector;

import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import main.FPR;
import myutils.UTemplates;

public class MainCommand implements MessageCreateListener {
  private static String           PREFIX;
  private Vector<AbstractCommand> commands;

  /**
   * Default constructor.
   */
  public MainCommand(String prefix) {
    commands = new Vector<>();
    PREFIX = prefix;
  }

  /**
   * Adds a new command module.
   *
   * @param command new command module
   */
  public void addModule(AbstractCommand command) {
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

    FPR
        .log()
          .info(event.getMessage().getAuthor().getDiscriminatedName() + ": "
              + event.getMessage().getContent());
    // ***************************************************************//
    // Non Commands
    // ***************************************************************//
    // Call leveling system
    FPR.level().chat(event);

    // ***************************************************************//
    // Commands
    // ***************************************************************//
    if (!event.getMessage().getContent().startsWith(PREFIX)) {
      return; // Ignore if message does not have prefix.
    }

    // New scanner for parsing commands.
    Scanner scanner;

    FPR.log().debug("MainCommand: Command recieved.");
    for (AbstractCommand c : commands) {
      scanner = new Scanner(event.getMessage().getContent());
      // Skips the prefix.
      scanner.skip(PREFIX);
      try {
        if (c.parse(event, scanner)) {
          FPR.log().debug("MainCommand: Command execution success!");
          return;
        }
      } catch (ExCommandException e) {
        FPR
            .log()
              .error("MainCommand: Error executing command at " + c.getClass().getName()
                  + "! Reason: \"" + e.toString() + "\"");
        event.getChannel().sendMessage(UTemplates.errorTemplate(e.toString(), "<command>"));
        return;
      }
    }
    FPR.log().debug("MainCommand: No such command!");
  }

  /**
   * Gets the prefix.
   *
   * @return prefix
   */
  public static String prefix() {
    return PREFIX;
  }
}
