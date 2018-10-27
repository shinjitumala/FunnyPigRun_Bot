package myutils;

import java.awt.Color;
import java.util.List;
import java.util.Optional;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import commands.ECommandExecutionException;
import commands.MainCommand;
import main.FPR;

public class MyUtils {
  public static EmbedBuilder embedTemplate(String title, String Description, Color color) {
    EmbedBuilder ret = new EmbedBuilder().setTimestampToNow()
        .setAuthor(FPR.bot().getDisplayName(FPR.server()), "https://github.com/shinjitumala/FunnyPigRun_Bot",
            FPR.bot().getAvatar())
        .setFooter("Created by " + FPR.me().getName(), FPR.me().getAvatar()).setTitle(title).setDescription(Description)
        .setColor(color);

    return ret;
  }

  /**
   * Checks whether a user has a role in a server
   *
   * @param user   user
   * @param server server
   * @param role   role
   * @return returns true if user has the role in server
   */
  public static boolean hasRole(User user, Server server, Role role) {
    List<Role> roles = user.getRoles(server);
    for (Role r : roles) {
      if (r.equals(role)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Overload. This one takes Optional<User> in place of user
   *
   * @param OUser  Optional<User>
   * @param server server
   * @param role   role
   * @return will also return false if user is not present
   */
  public static void hasRole(Optional<User> user, Server server, Role role) throws ECommandExecutionException {
    if (user.isPresent()) {
      if (MyUtils.hasRole(user.get(), server, role)) {
        return;
      }
    }
    throw new ECommandExecutionException("Permission denied.");
  }

  /**
   * Checks whether the user has a higher role that the specified role in the
   * server
   *
   * @param user   user
   * @param server server
   * @param role   role
   * @throws ECommandExecutionException if the user has insufficient permission
   */
  public static void hasPermission(Optional<User> user, Server server, Role role) throws ECommandExecutionException {
    int neededPermission = role.getPosition();
    int maximumPermission = -1;
    int i;
    if (user.isPresent()) {
      for (Role r : user.get().getRoles(server)) {
        i = r.getPosition();
        if (i > maximumPermission) {
          maximumPermission = i;
        }
      }
    }
    if (maximumPermission >= neededPermission) {
      return;
    }
    throw new ECommandExecutionException("Permission denied.");
  }

  /**
   * Splits the command into segments. All commands will be converted to lower
   * case letters.
   *
   * @param event MessageCreateEvent
   * @return the command turned into segments
   */
  public static String[] getSegments(MessageCreateEvent event) {
    String message = event.getMessage().getContent();
    message = message.replaceFirst("^" + MainCommand.prefix(), "");
    String segments[] = message.split(" ");
    for (int i = 0; i < segments.length; i++) {
      if (!segments[i].contains("@")) {
        segments[i] = segments[i].toLowerCase();
      }
    }
    return segments;
  }
}
