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
  public static DiscordApi api;

  // Important users
  private static User FPRBot;
  private static User me;

  // use this for logging
  public static final Logger logger = LogManager.getLogger(Main.class);

  // funny.pig.run Gaming variables
  public static Server                                   server;
  public static final HashMap<String, ServerTextChannel> textChannels = new HashMap<>();
  public static final HashMap<String, Role>              roles        = new HashMap<>();
  public static final LevelCore                          levelcore    = new LevelCore();

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
   * Gets the discord api.
   *
   * @return
   */
  public static DiscordApi api() {
    return api;
  }
}
