package myutils;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Optional;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

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
    EmbedBuilder ret = new EmbedBuilder()
        .setTimestampToNow()
        .setAuthor(FPR.bot().getDisplayName(FPR.server()), "https://github.com/shinjitumala/FunnyPigRun_Bot",
            FPR.bot().getAvatar())
        .setFooter("Created by " + FPR.me().getName(), FPR.me().getAvatar())
        .setTitle(title)
        .setDescription(Description)
        .setColor(color);

    return ret;
  }

  public static EmbedBuilder helpTemplate(String command, String Description, Role permission) {
    EmbedBuilder ret = MyUtils
        .embedTemplate(MainCommand.prefix() + command, Description, Color.GRAY)
        .addField("Permissions", "Must have role " + permission.getMentionTag() + " or above.\n" + "User the command `"
            + MainCommand.prefix() + "rolelist` to see role hierarchy.");

    return ret;
  }

  public static EmbedBuilder errorTemplate(String reason, String command) {
    EmbedBuilder ret = MyUtils
        .embedTemplate("Oops!", reason, Color.RED)
        .addField("Help", "You can get more information about this command by typing `" + MainCommand.prefix() + command
            + " --help`.");

    return ret;
  }

  public static EmbedBuilder errorTemplate(String reason) {
    EmbedBuilder ret = MyUtils.embedTemplate("Oops!", reason, Color.RED);

    return ret;
  }

  public static EmbedBuilder completeTemplate(String message) {
    EmbedBuilder ret = MyUtils.embedTemplate("Done!", message, Color.BLUE);
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

  public static Color getRoleColor(Role role) {
    Optional<Color> color = role.getColor();
    if (color.isPresent()) {
      return color.get();
    }
    return Color.blue;
  }

  public static Role findNationality(String tag) {
    return FPR.nationality().get(tag);
  }

  /**
   * Write an object to a file.
   *
   * @param o    Object
   * @param path file path
   * @return returns false if failed
   */
  public static boolean writeObject(Object o, String path) {
    try (ObjectOutputStream oos = new ObjectOutputStream(
        new DeflaterOutputStream(new FileOutputStream(ServerFiles.NATIONALITY.toString())))) {
      oos.writeObject(o);
      return true;
    } catch (IOException e) {
      return false;
    }
  }

  /**
   * Reads object from a file
   *
   * @param path file path
   * @return the read object
   * @throws ClassNotFoundException
   * @throws IOException
   */
  public static Object readObject(String path) throws ClassNotFoundException, IOException {
    try (ObjectInputStream ois = new ObjectInputStream(new InflaterInputStream(new FileInputStream(path)))) {
      Object ret = ois.readObject();
      return ret;
    } catch (ClassNotFoundException e) {
      throw new ClassNotFoundException();
    } catch (IOException e) {
      throw new IOException(e);
    }
  }
}
