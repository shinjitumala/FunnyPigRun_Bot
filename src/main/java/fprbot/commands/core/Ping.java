package fprbot.commands.core;

import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class Ping implements MessageCreateListener {

	public void onMessageCreate(MessageCreateEvent event) {
		String message = event.getMessage().getContent();
		if (message.equalsIgnoreCase("!ping")) {
			MessageAuthor author = event.getMessage().getAuthor();
			EmbedBuilder embed = new EmbedBuilder().addField("Pong!", "Hello there, <@" + author.getId() + ">!", true);

			event.getChannel().sendMessage(embed);
		}
	}

}
