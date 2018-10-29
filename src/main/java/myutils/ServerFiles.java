package myutils;

public enum ServerFiles {
  NATIONALITY("nationality.fpr"),
  MEMBERS("members.fpr");

  private final String path;

  ServerFiles(String path) {
    this.path = path;
  }

  @Override
  public String toString() {
    return path;
  }
}
