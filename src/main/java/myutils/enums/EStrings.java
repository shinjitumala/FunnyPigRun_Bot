package myutils.enums;

public enum EStrings {
  PREFIX("<FPR>");

  private final String s;

  EStrings(String s) {
    this.s = s;
  }

  @Override
  public String toString() {
    return s;
  }
}
