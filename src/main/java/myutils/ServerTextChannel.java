package myutils;

public enum ServerTextChannel {
  TOWNHALL("townhall"),
  SELF_PROMOTION("self promotion"),
  RULES("rules"),
  NEWS("news"),
  OINK_GENERAL("oink general"),
  OINK_COMMANDS("oink_commands"),
  OINK_AGORA("oink agora");

  private final String key;

  ServerTextChannel(String key) {
    this.key = key;
  }

  @Override
  public String toString() {
    return key;
  }
}
