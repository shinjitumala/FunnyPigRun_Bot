package fprbot;

import java.util.HashMap;

import org.apache.logging.log4j.Logger;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;

public class FPR_Server_Objects {
	private final Logger LOGGER;
	private final Server s_fpr;
	private final HashMap<String, ServerTextChannel> tc_map;
	private final HashMap<String, Role> r_map;

	public FPR_Server_Objects(Server s_fpr, Logger LOGGER) {
		this.LOGGER = LOGGER;
		this.s_fpr = s_fpr;
		tc_map = new HashMap<String, ServerTextChannel>();
		r_map = new HashMap<String, Role>();
	}

	public Logger LOGGER() {
		return LOGGER;
	}

	public Server get_s() {
		return s_fpr;
	}

	public void add_tc(String key, ServerTextChannel tc) {
		tc_map.put(key, tc);
	}

	public ServerTextChannel get_tc(String key) {
		ServerTextChannel tc = tc_map.get(key);
		if (tc == null) {
			LOGGER.fatal("get_tc(): No such element found error. Key: " + key);
			System.exit(1);
		}
		return tc;
	}

	public void add_r(String key, Role r) {
		r_map.put(key, r);
	}

	public Role get_r(String key) {
		Role r = r_map.get(key);
		if (r == null) {
			LOGGER.fatal("get_r(): No such element found error. Key: " + key);
			System.exit(1);
		}
		return r;
	}

}
