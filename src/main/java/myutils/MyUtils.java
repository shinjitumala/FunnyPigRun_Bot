package myutils;

import java.awt.Color;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.permission.Role;

import commands.ExCommandException;
import data.SRole;
import main.FPR;

public class MyUtils {

  public static Color getRoleColor(Role role) {
    Optional<Color> color = role.getColor();
    if (color.isPresent()) {
      return color.get();
    }
    return Color.blue;
  }

  public static Role findNationality(String tag) throws ExCommandException {
    Role role = SRole.nationality.get(tag);
    if (role == null) {
      FPR.logger.error("Failed to find role with tag: '" + tag + "'.");
      throw new ExCommandException("Could not find role with tag: `" + tag + "`.");
    }
    return role;
  }

  public static <T> T get(Optional<T> obj) throws ExCommandException {
    if (obj.isPresent()) {
      return obj.get();
    }
    String name = obj.getClass().getName();
    FPR.logger.error("Failed to get Optional object: '" + name + "'.");
    throw new ExCommandException("Failed to get " + name + ".");
  }

  public static <T> T wait(CompletableFuture<T> obj) throws ExCommandException {
    T ret;
    try {
      ret = obj.get();
    } catch (NoSuchElementException | InterruptedException | ExecutionException e) {
      String name = obj.getClass().getName();
      FPR.logger.error("Failed to get CompleteableFuture object: '" + name + "'.");
      throw new ExCommandException("Failed to get " + name + ".");
    }
    return ret;
  }
}
