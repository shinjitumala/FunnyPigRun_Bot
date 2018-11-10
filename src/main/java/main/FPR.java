package main;

import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import level.LevelCore;

/**
 * Class that holds all shared variables for "funny.pig.run Gaming" server.
 */
public class FPR {
  // Discord API
  private static DiscordApi api;

  // Important users
  private static User FPRBot;
  private static User me;

  // use this for logging
  private static final Logger logger = LogManager.getLogger(Main.class);

  // funny.pig.run Gaming variables
  private static Server                                   server;
  private static final HashMap<String, ServerTextChannel> textChannels = new HashMap<>();
  private static final HashMap<String, Role>              roles        = new HashMap<>();
  private static HashMap<String, Role>                    nationality  = new HashMap<>();
  private static LevelCore                                levelcore    = new LevelCore();

  /**
   * initialize the server variable
   *
   * @param init_server funny.pig.run Gaming server object
   * @param thisBot     the User object of this bot
   */
  public static void initialize(Server init_server, User thisBot, User FPR, DiscordApi discord) {
    server = init_server;
    FPRBot = thisBot;
    me = FPR;
    api = discord;
  }

  /**
   * Get the Server object for funny.pig.run Gaming
   *
   * @return server object
   */
  public static Server server() {
    return server;
  }

  /**
   * Get the User object for funny.pig.run
   *
   * @return User funny.pig.run
   */
  public static User bot() {
    return FPRBot;
  }

  /**
   * Get the User object for FPRBot
   *
   * @return User FPRBot
   */
  public static User me() {
    return me;
  }

  /**
   * Add a new text channel
   *
   * @param key key
   * @param tc  text channel object
   */
  public static void addTextChannel(String key, ServerTextChannel tc) {
    textChannels.put(key, tc);
  }

  /**
   * Get a text channel with key
   *
   * @param key key
   * @return text channel
   */
  public static ServerTextChannel getTextChannel(String key) {
    ServerTextChannel ret = textChannels.get(key);
    if (ret == null) {
      FPR.log().fatal("FPR: Cannot find ServerTextChannel with key \"" + key + "\".");
      System.exit(1);
    }
    return ret;
  }

  /**
   * Add a new role
   *
   * @param key  key
   * @param role role object
   */
  public static void addRole(String key, Role role) {
    roles.put(key, role);
  }

  /**
   * Get a role with key
   *
   * @param key key
   * @return role
   */
  public static Role getRole(String key) {
    Role ret = roles.get(key);
    if (ret == null) {
      FPR.log().fatal("FPR: Cannot find Role with key \"" + key + "\".");
      System.exit(1);
    }
    return ret;
  }

  /**
   * Use this to leave a log message.
   *
   * @return Logger object
   */
  public static Logger log() {
    return logger;
  }

  /**
   * Initializes nationality role HashMap.
   *
   * @param data
   */
  public static void initNationality(HashMap<String, Role> data) {
    nationality = data;
  }

  /**
   * Adds nationality role to the database.
   *
   * @param role role
   */
  public static void addNationality(Role role, String tag) {
    nationality.put(tag, role);
  }

  /**
   * Gets the HashMap for all nationality roles.
   *
   * @return
   */
  public static HashMap<String, Role> nationality() {
    return nationality;
  }

  /**
   * Gets the DiscordApi object for this bot
   *
   * @return
   */
  public static DiscordApi api() {
    return api;
  }

  /**
   * Gets the leveling system core.
   *
   * @return
   */
  public static LevelCore level() {
    return levelcore;
  }
}
