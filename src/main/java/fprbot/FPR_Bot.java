package fprbot;

import java.util.NoSuchElementException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.javacord.api.util.logging.FallbackLoggerConfiguration;

import fprbot.commands.core.MessageHandler;
import fprbot.events.UserJoin;

public class FPR_Bot {
	private static final String TOKEN = "NDgwNDQxNDE2NTA2NTQwMDMy.DlsXCA.JDYtMxgVTSTGyhxrjtckh5aM7dg";
	private static final String DEFAULT_PREFIX = "$$";

	private static Logger logger = LogManager.getLogger(FPR_Bot.class);

	// ----------
	// Objects in funny.pig.run's server
	// Server
	private static Server fpr_server;
	// Channels
	private static ServerTextChannel welcome_channel;
	private static ServerTextChannel self_promotion_channel;
	private static ServerTextChannel rules_channel;
	// Roles
	private static Role new_immigrant;
	// ----------

	public static void main(String args[]) {
		FallbackLoggerConfiguration.setDebug(true);

		org.javacord.api.DiscordApi api = new DiscordApiBuilder()
				.setToken(TOKEN).login().join();

		logger.info("You can invite me by using the following url: "
				+ api.createBotInvite());

		// ----------
		// Initialize objects in funny.pig.run's server
		try {
			// Server
			fpr_server = api.getServerById("414315826557091840").get();

			// Channels
			welcome_channel = fpr_server
					.getTextChannelById("414316475797602305").get();
			self_promotion_channel = fpr_server
					.getTextChannelById("414316416318177291").get();
			rules_channel = fpr_server.getTextChannelById("414318128516956163")
					.get();

			// Roles
			new_immigrant = fpr_server.getRoleById("481333688043307008").get();
		} catch (NoSuchElementException ex) {
			logger.fatal(
					"Some of the server elements in \"funny.pig.run Gaming\" is missing! Cannot continue!");
			return;
		}
		// ----------

		// Add listeners
		api.addMessageCreateListener(new MessageHandler(DEFAULT_PREFIX));
		api.addServerMemberJoinListener(new UserJoin(welcome_channel,
				new_immigrant, self_promotion_channel, rules_channel));
	}
}
