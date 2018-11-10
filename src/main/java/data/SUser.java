package data;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.user.User;

import level.LevelUserData;
import main.FPR;
import myutils.UFiles;
import myutils.enums.EFiles;

public class SUser implements Serializable {
  private static final long       serialVersionUID = -5917988694028362811L;
  private static ArrayList<SUser> members          = new ArrayList<>();

  private final long         id;
  public final LevelUserData level;

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
   * Find the user in the user data.
   *
   * @param user
   * @return
   * @throws NoSuchElementException
   */
  public static SUser find(User user) throws NoSuchElementException {
    for (SUser u : members) {
      if (u.id() == user.getId()) {
        return u;
      }
    }
    throw new NoSuchElementException();
  }

  /**
   * Save the current members data.
   *
   * @return
   */
  public static boolean save() {
    return UFiles.writeObject(members, EFiles.MEMBERS.toString());
  }

  /**
   * Restores members data from file.
   */
  @SuppressWarnings("unchecked")
  public static boolean load() {
    try {
      Object obj = UFiles.readObject(EFiles.MEMBERS.toString());
      members = new ArrayList<>((ArrayList<SUser>) obj);
    } catch (ClassNotFoundException | IOException e) {
      return false;
    }
    Collection<User> temp = FPR.server().getMembers();
    for (User u : temp) {
      try {
        SUser.find(u);
      } catch (NoSuchElementException e) {
        SUser.add(u);
      }
    }
    return true;
  }

  /**
   * Gets the ArrayList of user data.
   *
   * @return
   */
  public static ArrayList<SUser> members() {
    return members;
  }
}
