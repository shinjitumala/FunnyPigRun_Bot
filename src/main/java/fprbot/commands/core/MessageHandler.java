package fprbot.commands.core;

import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class MessageHandler implements
		MessageCreateListener {
	private String PREFIX;

	public MessageHandler(
			String prefix) {
		PREFIX = prefix;
	}

	public void onMessageCreate(
			MessageCreateEvent event) {
		System.out.println(
				"Message received! Message: "
						+ event.getMessage()
								.getContent());

		MessageAuthor author = event
				.getMessage()
				.getAuthor();
		String message = event
				.getMessage()
				.getContent();

		// ----------
		// bot response condition
		// will not respond to a bot.
		if (!author.isUser()) {
			System.out.println(
					"A bot is talking!");
			return;
		}

		// will only respond if prefix is used.
		if (!message
				.startsWith(PREFIX)) {
			System.out.println(
					"The message was not a command!");
			return;
		}
		// ----------

		// cuts out the prefix
		message = message.substring(
				PREFIX.length());
		System.out.println(
				"Command received: "
						+ message);
	}

}
