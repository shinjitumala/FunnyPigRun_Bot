package myutils;

import java.util.List;
import java.util.Optional;

import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import commands.ExCommandException;

public class URoles {
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
  public static void hasRole(Optional<User> user, Server server, Role role) throws ExCommandException {
    if (user.isPresent()) {
      if (URoles.hasRole(user.get(), server, role)) {
        return;
      }
    }
    throw new ExCommandException("Permission denied.");
  }

  /**
   * Checks whether the user has a higher role that the specified role in the
   * server
   *
   * @param user   user
   * @param server server
   * @param role   role
   * @throws ExCommandException if the user has insufficient permission
   */
  public static boolean hasPermission(User user, Server server, Role role) {
    int neededPermission = role.getPosition();
    int maximumPermission = -1;
    int i;
    for (Role r : user.getRoles(server)) {
      i = r.getPosition();
      if (i > maximumPermission) {
        maximumPermission = i;
      }
    }

    if (maximumPermission >= neededPermission) {
      return true;
    }
    return false;
  }
}
