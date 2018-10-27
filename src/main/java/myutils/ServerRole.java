package myutils;

public enum ServerRole {
  PIGGIES("piggies"),
  OINK("oink oink"),
  DRIFTER("drifter");

  private final String key;

  ServerRole(String key) {
    this.key = key;
  }

  @Override
  public String toString() {
    return key;
  }
}
