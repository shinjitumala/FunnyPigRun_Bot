package data;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

import org.javacord.api.entity.permission.Role;

import commands.ExCommandException;
import main.FPR;
import myutils.UFiles;
import myutils.enums.EFiles;

public class SRole implements Serializable {
  private static final long serialVersionUID = 2360516558823515729L;

  public static final HashMap<String, Role> nationality = new HashMap<>();

  private final long   id;
  private final String tag;

  public SRole(Role role, String tag) {
    id = role.getId();
    this.tag = tag;
  }

  public Role restore() throws NoSuchElementException {
    Role ret = FPR.server().getRoleById(id).get();

    return ret;
  }

  public String tag() {
    return tag;
  }

  @SuppressWarnings("unchecked")
  public static void load() {
    try {
      Object obj = UFiles.readObject(EFiles.NATIONALITY.toString());
      for (SRole r : (ArrayList<SRole>) obj) {
        nationality.put(r.tag(), r.restore());
      }
    } catch (IOException | ClassNotFoundException e) {
      FPR.logger.error("Error while reading \"" + EFiles.NATIONALITY.toString() + "\".");
    }
  }

  public static void save() throws ExCommandException {
    ArrayList<SRole> list = new ArrayList<>();
    for (String key : nationality.keySet()) {
      list.add(new SRole(nationality.get(key), key));
    }
    if (!UFiles.writeObject(list, EFiles.NATIONALITY.toString())) {
      FPR.logger.error("Error occured while writing to " + EFiles.NATIONALITY.toString() + ".");
      throw new ExCommandException("Error writing to file.");
    }
  }
}
