package main;

import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;

/**
 * Class that holds all shared variables for "funny.pig.run Gaming" server. 
 */
public class FPR {
	// use this for logging
	private static final Logger logger = LogManager.getLogger(Main.class);
	
	// funny.pig.run Gaming variables
	private static Server server;
	private static final HashMap<String, ServerTextChannel> textChannels = new HashMap<String, ServerTextChannel>();
	private static final HashMap<String, Role> roles = new HashMap<String, Role>();
	
	
	/**
	 * initialize the server variable
	 * @param init_server funny.pig.run Gaming server object
	 */
	public static void initialize(Server init_server) {
		server = init_server;
	}
	
	/**
	 * Get the Server object for funny.pig.run Gaming
	 * @return server object
	 */
	public static Server server() {
		return server;
	}
	
	/**
	 * Add a new text channel
	 * @param key key
	 * @param tc text channel object
	 */
	public static void addTextChannel(String key, ServerTextChannel tc) {
		textChannels.put(key,  tc);
	}
	
	
	/**
	 * Get a text channel with key
	 * @param key key
	 * @return text channel
	 */
	public static ServerTextChannel getTextChannel(String key) {
		ServerTextChannel ret = textChannels.get(key);
		if(ret == null) {
			FPR.log().fatal("FPR: Cannot find ServerTextChannel with key \"" + key + "\".");
			System.exit(1);
		}
		return ret;
	}
	
	/**
	 * Add a new role
	 * @param key key
	 * @param role role object
	 */
	public static void addRole(String key, Role role) {
		roles.put(key, role);
	}
	
	/**
	 * Get a role with key
	 * @param key key
	 * @return role
	 */
	public static Role getRole(String key) {
		Role ret = roles.get(key);
		if(ret == null) {
			FPR.log().fatal("FPR: Cannot find Role with key \"" + key + "\".");
			System.exit(1);
		}
		return ret;
	}
	
	/**
	 * Use this to leave a log message.
	 * @return Logger object
	 */
	public static Logger log() {
		return logger;
	}
}
