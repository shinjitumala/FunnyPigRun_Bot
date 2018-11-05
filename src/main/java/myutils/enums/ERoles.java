package myutils.enums;

public enum ERoles {
  PIGGIES("piggies"),
  OINK("oink oink"),
  DRIFTER("drifter");

  private final String key;

  ERoles(String key) {
    this.key = key;
  }

  @Override
  public String toString() {
    return key;
  }
}
