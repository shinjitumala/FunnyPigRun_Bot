package fprbot.events;

import java.awt.Color;

import org.javacord.api.entity.emoji.Emoji;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.server.emoji.KnownCustomEmojiCreateEvent;
import org.javacord.api.listener.server.emoji.KnownCustomEmojiCreateListener;

import fprbot.FPR;
import fprbot.FPR_Server_Objects;

public class CustomEmojiCreate extends FPR
		implements KnownCustomEmojiCreateListener {
	public CustomEmojiCreate(FPR_Server_Objects FPR) {
		super(FPR);
	}

	public void onKnownCustomEmojiCreate(KnownCustomEmojiCreateEvent event) {
		Emoji emoji = event.getEmoji();

		EmbedBuilder embed = new EmbedBuilder().setColor(Color.green)
				.setTitle("A new emoji was added!")
				.setDescription(emoji.getMentionTag()
						+ "\n Hover over it to see the text behind it!")
				.setTimestampToNow()
				.setAuthor("Oink Oink",
						"https://github.com/shinjitumala/FunnyPigRun_Bot",
						"https://avatars3.githubusercontent.com/u/30512876?s=460&v=4")
				.setThumbnail(
						"https://static-cdn.jtvnw.net/jtv_user_pictures/panel-154435522-image-b09a58ec-2d43-4efa-b7b0-5acda3e01a24");

		FPR.get_tc("news").sendMessage(embed);
	}

}
