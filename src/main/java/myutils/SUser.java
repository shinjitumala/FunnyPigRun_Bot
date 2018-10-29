package myutils;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.user.User;

import level.LevelUserData;
import main.FPR;

public class SUser implements Serializable {
  private static ArrayList<SUser> members;

  /**
   *
   */
  private static final long serialVersionUID = -5917988694028362811L;

  private final long          id;
  private final LevelUserData level;

  public SUser(User user, LevelUserData level) {
    id = user.getId();
    this.level = level;
  }

  public User restore() throws NoSuchElementException {
    User ret;
    try {
      ret = FPR.api().getUserById(id).get();
    } catch (InterruptedException | ExecutionException e) {
      throw new NoSuchElementException();
    }
    return ret;
  }

  public long id() {
    return id;
  }

  /**
   * Adds a new member to the database.
   *
   * @param user
   */
  public static void add(User user) {
    members.add(new SUser(user, new LevelUserData()));
  }

  /**
   * Removes a user from the database.
   *
   * @param user
   * @return
   */
  public static boolean remove(User user) {
    for (SUser u : members) {
      if (u.id == user.getId()) {
        members.remove(u);
        return true;
      }
    }
    return false;
  }

  /**
   * Save the current members data.
   *
   * @return
   */
  public static boolean save() {
    return MyUtils.writeObject(members, ServerFiles.MEMBERS.toString());
  }

  /**
   * Restores members data from file.
   */
  @SuppressWarnings("unchecked")
  public static boolean load() {
    try {
      Object obj = MyUtils.readObject(ServerFiles.MEMBERS.toString());
      members = new ArrayList<>((ArrayList<SUser>) obj);
      return true;
    } catch (ClassNotFoundException | IOException e) {
      return false;
    }
  }
}
