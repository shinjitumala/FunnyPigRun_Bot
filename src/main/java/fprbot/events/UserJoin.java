package fprbot.events;

import java.awt.Color;

import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.server.member.ServerMemberJoinEvent;
import org.javacord.api.listener.server.member.ServerMemberJoinListener;

public class UserJoin implements ServerMemberJoinListener {
	private final ServerTextChannel welcome_channel;
	private final Role new_immigrant;
	private final ServerTextChannel self_promotion_channel;
	private final ServerTextChannel rules_channel;

	public UserJoin(ServerTextChannel welcome_channel, Role new_immigrant,
			ServerTextChannel self_promotion_channel,
			ServerTextChannel rules_channel) {
		this.welcome_channel = welcome_channel;
		this.new_immigrant = new_immigrant;
		this.self_promotion_channel = self_promotion_channel;
		this.rules_channel = rules_channel;
	}

	public void onServerMemberJoin(ServerMemberJoinEvent event) {
		User user = event.getUser();

		EmbedBuilder embed = new EmbedBuilder().setColor(Color.green)
				.setTitle("Welcome to funny.pig.run Gaming!")
				.setDescription("Hello there, " + user.getMentionTag()
						+ "! We welcome you to the family :)")
				.addField("Rules", "Please, take your time and read "
						+ rules_channel.getMentionTag()
						+ " so that we don't end up in some misunderstandings.")
				.addField("Roles",
						"All users in this server will be given a role based on their nationality."
								+ " For now, you will be given the role "
								+ new_immigrant.getMentionTag() + "."
								+ "You can assign yourself in an exsisting role using the `$role` command."
								+ "If no role fits you, then feel free to use `$role null` command"
								+ " in order to notify funny.pig.run.")
				.addInlineField("The shameless self promotion",
						"funny.pig.run does some stuff online. "
								+ "You can find out more by checking "
								+ self_promotion_channel.getMentionTag() + ".")
				.addInlineField("funny.pig.run Gaming", "This server now has "
						+ event.getServer().getMemberCount() + " members!")
				.setTimestampToNow()
				.setAuthor("Oink Oink",
						"https://github.com/shinjitumala/FunnyPigRun_Bot",
						"https://avatars3.githubusercontent.com/u/30512876?s=460&v=4")
				.setThumbnail(
						"https://static-cdn.jtvnw.net/jtv_user_pictures/panel-154435522-image-b09a58ec-2d43-4efa-b7b0-5acda3e01a24");

		welcome_channel.sendMessage(embed);
	}

}
