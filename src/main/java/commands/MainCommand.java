package commands;

import java.util.Optional;
import java.util.Vector;

import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import main.FPR;
import myutils.MyUtils;

public class MainCommand implements MessageCreateListener {
  private static String    PREFIX;
  private Vector<ICommand> commands;

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
  public void addModule(ICommand command) {
    commands.add(command);
  }

  /**
   * Called when a message is created.
   *
   * @param event MessageCreateEvent
   */
  @Override
  public void onMessageCreate(MessageCreateEvent event) {
    Optional<Server> server = event.getServer();
    if (server.isPresent()) {
      if (server.get() != FPR.server()) { // Ignore messages not from funny.pig.run Gaming.
        return;
      }
    } else if (event.isPrivateMessage()) { // Do not ignore private messages.
    } else {
      return; // Ignore if not server message.
    }

    Optional<User> user = event.getMessage().getUserAuthor();
    if (user.isPresent()) {
      if (user.get().isBot()) { // Ignore messages from bot.
        return;
      }
    } else {
      return; // Ignore if message was from a web hook and such.
    }

    // Call leveling system
    FPR.level().chat(event);

    if (!event.getMessage().getContent().startsWith(PREFIX)) {
      return; // Ignore if message does not have prefix.
    }

    FPR.log().debug("MainCommand: Command recieved.");
    FPR.log().info(event.getMessage().getAuthor().getDiscriminatedName() + ": " + event.getMessage().getContent());
    for (ICommand c : commands) {
      try {
        if (c.parse(event)) {
          FPR.log().debug("MainCommand: Command execution success!");
          return;
        }
      } catch (ECommandExecutionException e) {
        if (e.toString().equals("Permission denied.")) {
          FPR
              .log()
              .info("MainCommand: User " + user.get().getMentionTag() + " has no permission for command at "
                  + c.getClass().getName() + ".");
        } else {
          FPR
              .log()
              .error("MainCommand: Error executing command at " + c.getClass().getName() + "! Reason: \"" + e.toString()
                  + "\"");
          event.getChannel().sendMessage(MyUtils.errorTemplate(e.toString(), e.command()));
          return;
        }
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
