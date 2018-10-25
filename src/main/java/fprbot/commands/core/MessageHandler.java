package fprbot.commands.core;

import java.util.NoSuchElementException;

import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import fprbot.FPR;
import fprbot.FPR_Server_Objects;
import fprbot.commands.core.parser.MainParser;

public class MessageHandler extends FPR implements MessageCreateListener {
	private String PREFIX;

	public MessageHandler(FPR_Server_Objects FPR, String prefix) {
		super(FPR);
		PREFIX = prefix;
	}

	@Override
	public void onMessageCreate(MessageCreateEvent event) {
		FPR.LOGGER().info("MessageHandler: Message detected. Message: "
				+ event.getMessage().getContent());

		MessageAuthor author = event.getMessage().getAuthor();
		String message = event.getMessage().getContent();

		// ----------
		// Bot response condition
		// Will only pick up commands sent in "funny.pig.run Gaming" server
		try {
			if (!event.getServer().get().equals(FPR.get_s())) {
				FPR.LOGGER().warn(
						"MessageHandler: Detecting messages from another server. This bot should not be there!");
				return;
			}
		} catch (NoSuchElementException ex) {
			FPR.LOGGER().warn(
					"MessageHandler: Could not determine the server where the message was sent."
							+ " The message was ignored.");
			return;
		}

		// Will not respond to a webhook.
		if (author.isUser()) {
			if (author.asUser().get().isBot()) {
				FPR.LOGGER().info(
						"MessageHandler: Message was from a bot. The message was ignored.");
				return;
			}
		} else {
			FPR.LOGGER().info(
					"MessageHandler: Message was from a webhook. The message was ignored.");
			return;
		}

		// Will only respond if prefix is used.
		if (!message.startsWith(PREFIX)) {
			FPR.LOGGER().info(
					"MessageHandler: The message was not a command. Message ignored.");
			return;
		}
		// ----------

		// Cuts out the prefix
		message = message.substring(PREFIX.length());

		// ----------
		// Message Parsing
		String[] tokens = message.split("\\s");
		MainParser main = new MainParser(FPR);
		Boolean cmd = main.parse(tokens, 0, event);
		// ----------
		if (cmd) {
			FPR.LOGGER()
					.info("MessageHandler: Command was successfully received!");
		} else {
			FPR.LOGGER().error("MessageHandler: Command error!");
		}

	}

}
