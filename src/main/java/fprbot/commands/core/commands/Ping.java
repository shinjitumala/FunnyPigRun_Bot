package fprbot.commands.core.commands;

import java.awt.Color;
import java.util.Optional;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import fprbot.FPR;
import fprbot.FPR_Server_Objects;

public class Ping extends FPR {

	public Ping(FPR_Server_Objects FPR) {
		super(FPR);
	}

	public boolean default_ping(MessageCreateEvent event) {
		Optional<User> t_user = event.getMessage().getAuthor().asUser();

		if (!t_user.isPresent()) {
			FPR.LOGGER().error("Ping: Error while getting mention tag.");
			return false;
		}
		User user = t_user.get();

		EmbedBuilder embed = new EmbedBuilder().setColor(Color.blue)
				.setTitle("Pong!")
				.setDescription("Hello there, " + user.getMentionTag() + "!"
						+ "\nI am well and alive, don't worry :D")
				.setTimestampToNow()
				.setAuthor("Oink Oink",
						"https://github.com/shinjitumala/FunnyPigRun_Bot",
						"https://avatars3.githubusercontent.com/u/30512876?s=460&v=4")
				.setThumbnail(
						"https://static-cdn.jtvnw.net/jtv_user_pictures/panel-154435522-image-b09a58ec-2d43-4efa-b7b0-5acda3e01a24");

		event.getChannel().sendMessage(embed);

		return true;
	}

	public boolean ping_pong(MessageCreateEvent event) {
		event.getChannel().sendMessage("Pong Ping!");

		return true;
	}

}
