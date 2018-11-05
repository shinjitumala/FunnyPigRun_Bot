package level;

import java.io.Serializable;

public class LevelUserData implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = -1242027455469914168L;

  public int updateLevel() {
    int remaining = xp;
    int level = 0;
    while (remaining >= 0) {
      remaining -= 5 + (2 * level);
      level++;
    }
    this.level = level;
    return level;
  }

  private int xp;
  private int level;

  public LevelUserData() {
    this.xp = 0;
  }

  public int xp() {
    return xp;
  }

  public void addXP(int xp) {
    this.xp += xp;
  }

  public int level() {
    return level;
  }

}
