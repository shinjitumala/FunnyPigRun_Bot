package myutils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import commands.ACommand;

public class UClass {
  /**
   * Gets all command methods in a command class.
   *
   * @param type the command class
   * @return list of command methods
   */
  public static List<Method> getCommands(final Class<?> type) {
    final List<Method> commands = new ArrayList<>();
    Class<?> aClass = type;

    Class<? extends Annotation> annotation = ACommand.class;
    while (aClass != Object.class) {
      final List<Method> allMethods = new ArrayList<>(Arrays.asList(aClass.getDeclaredMethods()));
      for (final Method m : allMethods) {
        if (m.isAnnotationPresent(annotation)) {
          commands.add(m);
        }
      }
      aClass = aClass.getSuperclass();
    }
    return commands;
  }
}
