package fprbot.events;

import java.awt.Color;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.server.member.ServerMemberJoinEvent;
import org.javacord.api.listener.server.member.ServerMemberJoinListener;

import fprbot.FPR;
import fprbot.FPR_Server_Objects;

public class UserJoin extends FPR implements ServerMemberJoinListener {
	public UserJoin(FPR_Server_Objects FPR) {
		super(FPR);
	}

	@Override
	public void onServerMemberJoin(ServerMemberJoinEvent event) {
		User user = event.getUser();

		EmbedBuilder embed = new EmbedBuilder().setColor(Color.green)
				.setTitle("Welcome to funny.pig.run Gaming!")
				.setDescription("Hello there, " + user.getMentionTag()
						+ "! We welcome you to the family :)")
				.addField("Rules", "Please, take your time and read "
						+ FPR.get_tc("rules").getMentionTag()
						+ " so that we don't end up in some misunderstandings.")
				.addField("Roles",
						"All users in this server will be given a role based on their nationality."
								+ " For now, you will be given the role "
								+ FPR.get_r("new_immigrant").getMentionTag()
								+ "."
								+ "You can assign yourself in an exsisting role using the `$role` command."
								+ "If no role fits you, then feel free to use `$role null` command"
								+ " in order to notify funny.pig.run.")
				.addInlineField("The shameless self promotion",
						"funny.pig.run does some stuff online. "
								+ "You can find out more by checking "
								+ FPR.get_tc("self_promotion").getMentionTag()
								+ ".")
				.addInlineField("funny.pig.run Gaming", "This server now has "
						+ event.getServer().getMemberCount() + " members!")
				.setTimestampToNow()
				.setAuthor("Oink Oink",
						"https://github.com/shinjitumala/FunnyPigRun_Bot",
						"https://avatars3.githubusercontent.com/u/30512876?s=460&v=4")
				.setThumbnail(
						"https://static-cdn.jtvnw.net/jtv_user_pictures/panel-154435522-image-b09a58ec-2d43-4efa-b7b0-5acda3e01a24");

		FPR.get_tc("townhall").sendMessage(embed);
		user.addRole(FPR.get_r("new_immigrant")).exceptionally(throwable -> {
			// Print possible errors to the log
			throwable.printStackTrace();
			return null;
		});
		;
		FPR.LOGGER().info("done");
	}

}
