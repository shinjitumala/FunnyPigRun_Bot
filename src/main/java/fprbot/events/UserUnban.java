package fprbot.events;

import java.awt.Color;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.server.member.ServerMemberUnbanEvent;
import org.javacord.api.listener.server.member.ServerMemberUnbanListener;

import fprbot.FPR;
import fprbot.FPR_Server_Objects;

public class UserUnban extends FPR implements ServerMemberUnbanListener {
	public UserUnban(FPR_Server_Objects FPR) {
		super(FPR);
	}

	public void onServerMemberUnban(ServerMemberUnbanEvent event) {
		User user = event.getUser();

		EmbedBuilder embed = new EmbedBuilder().setColor(Color.yellow)
				.setTitle("Hmm...")
				.setDescription(user.getMentionTag()
						+ " has been unbanned from the server!")
				.setTimestampToNow()
				.setAuthor("Oink Oink",
						"https://github.com/shinjitumala/FunnyPigRun_Bot",
						"https://avatars3.githubusercontent.com/u/30512876?s=460&v=4")
				.setThumbnail(
						"https://static-cdn.jtvnw.net/jtv_user_pictures/panel-154435522-image-b09a58ec-2d43-4efa-b7b0-5acda3e01a24");

		FPR.get_tc("townhall").sendMessage(embed);
	}

}
