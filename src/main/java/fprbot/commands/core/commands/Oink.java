package fprbot.commands.core.commands;

import java.awt.Color;
import java.util.Collection;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.entity.user.UserStatus;
import org.javacord.api.event.message.MessageCreateEvent;

import fprbot.FPR;
import fprbot.FPR_Server_Objects;

public class Oink extends FPR {

	public Oink(FPR_Server_Objects FPR) {
		super(FPR);
	}

	public boolean default_oink(MessageCreateEvent event) {
		EmbedBuilder embed = new EmbedBuilder().setTitle("**Command: Oink**")
				.setDescription(
						"This command is used to manage the *Oink Oink* role.\n"
								+ "The *Oink Oink* role represents members who are considered \"close friends\".\n"
								+ "This command can only be used by members with *Oink Oink* role and can only be used in private command channels.\n"
								+ "Usage: `$oink <arguments>`\n"
								+ "Below is the list of arguments that you can use.")
				.addField("status",
						"Shows the stats of the *Oink Oink* role.\n"
								+ "Usage: `$oink stats`")
				.addField("vote",
						"Start or end a vote or simply vote. Votes can only be started if there is no votes currently going on. Votes can only be ended if there is on underway. And you can only vote if there is a vote happening right now.\n"
								+ "\t`$oink vote add <user mention>`: Start a vote to add a user to *Oink Oink*\n"
								+ "\t`$oink vote end`: Ends the current voting manually. Votings expire automatically in 30 minutes.\n"
								+ "\t`$oink vote aye`: Vote yes to the current vote.\n"
								+ "\t`$oink vote nay`: Vote no to the current vote.")
				.setColor(Color.white).setTimestampToNow()
				.setAuthor("Oink Oink",
						"https://github.com/shinjitumala/FunnyPigRun_Bot",
						"https://avatars3.githubusercontent.com/u/30512876?s=460&v=4")
				.setThumbnail(
						"https://static-cdn.jtvnw.net/jtv_user_pictures/panel-154435522-image-b09a58ec-2d43-4efa-b7b0-5acda3e01a24");
		event.getChannel().sendMessage(embed);

		return true;
	}

	public boolean oink_stats(MessageCreateEvent event) {
		Collection<User> users = FPR.get_r("oink oink").getUsers();
		int user_count = users.size();
		int active_count = (int) users.stream()
				.filter(u -> u.getStatus().equals(UserStatus.ONLINE)).count();

		String vote_status;
		if (user_count / 10 <= active_count) {
			vote_status = "Possible";
		} else {
			vote_status = "impossible";
		}

		EmbedBuilder embed = new EmbedBuilder().setTitle("__**Oink Oink**__")
				.setDescription("Current active users: " + active_count + "/"
						+ user_count)
				.addField("**Voting**",
						"In oder to pass a vote, more than 10% of the members and more than 50% of active members must vote yes.")
				.addField("**Votes needed**",
						"10% of all members: " + user_count / 10 + "\n"
								+ "50% of currently active members: "
								+ active_count / 2)
				.addField("**status**",
						"Passing a vote right now: " + vote_status)
				.setColor(Color.white).setTimestampToNow()
				.setAuthor("Oink Oink",
						"https://github.com/shinjitumala/FunnyPigRun_Bot",
						"https://avatars3.githubusercontent.com/u/30512876?s=460&v=4")
				.setThumbnail(
						"https://static-cdn.jtvnw.net/jtv_user_pictures/panel-154435522-image-b09a58ec-2d43-4efa-b7b0-5acda3e01a24");
		event.getChannel().sendMessage(embed);
		return true;
	}
}
