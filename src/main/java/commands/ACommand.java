package commands;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import myutils.enums.ERoles;

public @Retention(RetentionPolicy.RUNTIME) @interface ACommand {
  // The command.
  String command();

  // The permission for the command.
  ERoles permission();

  // Explanation for the command.
  String help();
}
