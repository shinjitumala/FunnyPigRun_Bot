package myutils.enums;

public enum EFiles {
  NATIONALITY("nationality.fpr"),
  MEMBERS("members.fpr"),
  TAGS("tags.fpr");

  private final String path;

  EFiles(String path) {
    this.path = path;
  }

  @Override
  public String toString() {
    return path;
  }
}
