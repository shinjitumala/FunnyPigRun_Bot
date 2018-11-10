package myutils;

import java.awt.Color;
import java.util.Optional;

import org.javacord.api.entity.permission.Role;

import main.FPR;

public class MyUtils {

  public static Color getRoleColor(Role role) {
    Optional<Color> color = role.getColor();
    if (color.isPresent()) {
      return color.get();
    }
    return Color.blue;
  }

  public static Role findNationality(String tag) {
    return FPR.nationality().get(tag);
  }
}
