package fprbot;

import java.util.NoSuchElementException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.util.logging.FallbackLoggerConfiguration;

import fprbot.commands.core.MessageHandler;
import fprbot.commands.core.ReactionAddHandler;
import fprbot.commands.core.ReactionRemoveHandler;
import fprbot.events.CustomEmojiCreate;
import fprbot.events.UserBan;
import fprbot.events.UserJoin;
import fprbot.events.UserLeave;
import fprbot.events.UserUnban;

public class FPR_Bot {
	private static String TOKEN;
	private static final String DEFAULT_PREFIX = "$";

	private static final Logger LOGGER = LogManager.getLogger(FPR_Bot.class);

	// ----------
	// Objects in funny.pig.run's server
	private static FPR_Server_Objects FPR;
	// Server
	private static Server fpr_server;
	// ----------

	public static void main(String args[]) {
		if (args.length < 1) {
			System.out.println("Usage: java fpr_bot <bot token>");
			return;
		}

		TOKEN = args[0];

		FallbackLoggerConfiguration.setDebug(true);

		org.javacord.api.DiscordApi api = new DiscordApiBuilder()
				.setToken(TOKEN).login().join();

		LOGGER.info("You can invite me by using the following url: "
				+ api.createBotInvite());

		// ----------
		// Initialize objects in funny.pig.run's server
		try {
			// Server
			fpr_server = api.getServerById("414315826557091840").get();

			// FPR_Server_Objects
			FPR = new FPR_Server_Objects(fpr_server, LOGGER);
		} catch (NoSuchElementException ex) {
			LOGGER.fatal(
					"The server \"funny.pig.run Gaming\" is missing! Cannot continue!");
			return;
		}

		// Initialize individual objects and add them to FPR_Server_Objects
		try {
			// Channels
			FPR.add_tc("townhall",
					fpr_server.getTextChannelById("414316475797602305").get());
			FPR.add_tc("self_promotion",
					fpr_server.getTextChannelById("414316416318177291").get());
			FPR.add_tc("rules",
					fpr_server.getTextChannelById("414318128516956163").get());
			FPR.add_tc("news",
					fpr_server.getTextChannelById("478821976186683402").get());

			FPR.add_tc("oink general",
					fpr_server.getTextChannelById("414319326238343169").get());
			FPR.add_tc("oink commands",
					fpr_server.getTextChannelById("452361154925297684").get());
			FPR.add_tc("oink agora",
					fpr_server.getTextChannelById("492303377544249344").get());

			// Roles
			FPR.add_r("oink oink",
					fpr_server.getRoleById("414316850231377921").get());
			FPR.add_r("new_immigrant",
					fpr_server.getRoleById("481333688043307008").get());

		} catch (NoSuchElementException ex) {
			LOGGER.fatal(
					"Some elements in \"funny.pig.run Gaming\" is missing! Cannot continue!");
			return;
		}
		// ----------

		// Add listeners
		api.addMessageCreateListener(new MessageHandler(FPR, DEFAULT_PREFIX));
		api.addServerMemberJoinListener(new UserJoin(FPR));
		api.addServerMemberLeaveListener(new UserLeave(FPR));
		api.addKnownCustomEmojiCreateListener(new CustomEmojiCreate(FPR));
		api.addServerMemberBanListener(new UserBan(FPR));
		api.addServerMemberUnbanListener(new UserUnban(FPR));
		api.addReactionAddListener(new ReactionAddHandler(FPR));
		api.addReactionRemoveListener(new ReactionRemoveHandler(FPR));

	}
}
