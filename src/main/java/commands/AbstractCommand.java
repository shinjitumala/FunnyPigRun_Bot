package commands;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import main.FPR;
import myutils.UClass;
import myutils.URoles;
import myutils.UTemplates;

/**
 * Abstract class for commands
 */
public abstract class AbstractCommand {
  private final List<Method> commands;

  public AbstractCommand() {
    commands = UClass.getCommands(this.getClass());
  }

  /**
   * Parses the creates message.
   *
   * @param event message event
   * @return returns true if command was successfully parsed. if command is not
   *         from this module, returns false. error while executing will be thrown
   *         as an exception with reason.
   */
  public boolean parse(MessageCreateEvent event, Scanner scanner) throws ExCommandException {
    System.out.println(this.getClass().getName());
    if (!scanner.hasNext()) {
      return false;
    }
    String token = scanner.next();
    boolean help = false;

    // Sets flag if it is a help command.
    if (token.equals("help")) {
      help = true;
      token = scanner.next();
    }

    for (Method m : commands) {
      if (m.getAnnotation(ACommand.class).command().equals(token)) {
        if (!help) {
          // Normal command
          if (URoles
              .hasPermission(event.getMessage().getUserAuthor().get(), event.getServer().get(),
                  FPR.getRole(m.getAnnotation(ACommand.class).permission().toString()))) {
            try {
              try {
                if (m.getParameterTypes().length == 2) {
                  m.invoke(this, event, scanner);
                } else if (m.getParameterTypes().length == 1) {
                  m.invoke(this, event);
                }
              } catch (InvocationTargetException e) {
                if (e.getCause() instanceof NoSuchElementException) {
                  throw (NoSuchElementException) e.getCause();
                } else if (e.getCause() instanceof IllegalStateException) {
                  throw (IllegalStateException) e.getCause();
                } else {
                  throw new ExCommandException(e.getCause().toString());
                }
              }
              return true;
            } catch (IllegalAccessException | IllegalArgumentException e) {
              e.printStackTrace();
              FPR.log().fatal("ACommand: Wrong method invocation.");
              throw new ExCommandException("Method invocation error.");
            } catch (NoSuchElementException e) {
              throw new ExCommandException(
                  "Insufficient arguments. Type ` --help` after the command (make sure to put the space there!) for help.");
            } catch (IllegalStateException e) {
              throw new ExCommandException("Scanner was closed while parsing command.");
            }
          } else {
            throw new ExCommandException("You have insufficient power to execute this command.");
          }
        } else {
          // Help command
          EmbedBuilder embed = UTemplates
              .helpTemplate(m.getAnnotation(ACommand.class).command(),
                  m.getAnnotation(ACommand.class).help(),
                  FPR.getRole((m.getAnnotation(ACommand.class).permission().toString())));
          event.getChannel().sendMessage(embed);
        }
      }
    }
    return false;
  }
}
