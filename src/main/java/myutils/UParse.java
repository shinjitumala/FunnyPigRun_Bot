package myutils;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Matcher;

import org.javacord.api.util.DiscordRegexPattern;

import commands.ExCommandException;

public class UParse {
  /**
   * Parses user mention and get the user id.
   *
   * @param scanner scanner
   * @return User ID
   * @throws ExCommandException
   */
  public static String parseUser(Scanner scanner) throws ExCommandException {
    String id;
    try {
      id = scanner.next();
      Matcher matcher = DiscordRegexPattern.USER_MENTION.matcher(id);
      matcher.matches();
      id = matcher.group("id");
    } catch (NoSuchElementException e) {
      throw new ExCommandException("Insufficient arguments.");
    } catch (IllegalStateException e) {
      throw new ExCommandException("Invalid argumetns.");
    }
    return id;
  }

  /**
   * Parses role mention and get the role id.
   *
   * @param scanner scanner
   * @return Role ID
   * @throws ExCommandException
   */
  public static String parseRole(Scanner scanner) throws ExCommandException {
    String id;
    try {
      id = scanner.next();
      Matcher matcher = DiscordRegexPattern.ROLE_MENTION.matcher(id);
      matcher.matches();
      id = matcher.group("id");
    } catch (NoSuchElementException e) {
      throw new ExCommandException("Insufficient arguments.");
    } catch (IllegalStateException e) {
      throw new ExCommandException("Invalid argumetns.");
    }
    return id;
  }

  /**
   * Parses a string.
   *
   * @param scanner scanner
   * @return string
   * @throws ExCommandException
   */
  public static String parseString(Scanner scanner) throws ExCommandException {
    String string;
    try {
      string = scanner.next();
    } catch (NoSuchElementException e) {
      throw new ExCommandException("Insufficient arguments.");
    }
    return string;
  }

  /**
   * Parses an integer
   *
   * @param scanner scanner
   * @return integer
   * @throws ExCommandException
   */
  public static int parseInt(Scanner scanner) throws ExCommandException {
    int integer;
    String string;
    try {
      string = scanner.next();
      integer = Integer.parseInt(string);
    } catch (NoSuchElementException e) {
      throw new ExCommandException("Insufficient arguments.");
    } catch (NumberFormatException e) {
      throw new ExCommandException("Invalid arguments.");
    }
    return integer;
  }
}
