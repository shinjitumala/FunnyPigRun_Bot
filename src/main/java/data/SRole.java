package data;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import org.javacord.api.entity.permission.Role;

import main.FPR;
import myutils.UFiles;
import myutils.enums.EFiles;

public class SRole implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 2360516558823515729L;

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
  public static boolean load() {
    try {
      Object obj = UFiles.readObject(EFiles.NATIONALITY.toString());
      for (SRole r : (ArrayList<SRole>) obj) {
        FPR.nationality().put(r.tag(), r.restore());
      }
    } catch (IOException | ClassNotFoundException e) {
      return false;
    }
    return true;
  }

  public static boolean save() {
    ArrayList<SRole> list = new ArrayList<>();
    for (String key : FPR.nationality().keySet()) {
      list.add(new SRole(FPR.nationality().get(key), key));
    }
    return UFiles.writeObject(list, EFiles.NATIONALITY.toString());
  }
}
