package data;

import java.io.Serializable;
import java.util.HashMap;

public class STag implements Serializable {
  private static final long serialVersionUID = 1436332277157920083L;

  public static final HashMap<String, String> tags = new HashMap<>();

  private final String tag;
  private final String message;

  public STag(String tag, String message) {
    this.tag = tag;
    this.message = message;
  }
}
