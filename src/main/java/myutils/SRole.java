package myutils;

import java.io.Serializable;
import java.util.NoSuchElementException;

import org.javacord.api.entity.permission.Role;

import main.FPR;

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

}
