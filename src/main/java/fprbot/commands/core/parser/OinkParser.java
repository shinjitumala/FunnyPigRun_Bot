package fprbot.commands.core.parser;

import java.util.Optional;

import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import fprbot.FPR;
import fprbot.FPR_Server_Objects;
import fprbot.commands.core.commands.Oink;

public class OinkParser extends FPR implements IParser {
	private final Oink oink;

	public OinkParser(FPR_Server_Objects FPR) {
		super(FPR);
		oink = new Oink(FPR);
	}

	@Override
	public boolean parse(String[] tokens, int token_number,
			MessageCreateEvent event) {
		// -----------
		// Do a permission check here
		// Only users with "Oink Oink" can use this command.
		Optional<User> t_user = event.getMessage().getAuthor().asUser();
		if (!t_user.isPresent()) {
			FPR.LOGGER().error("OinkParser: Error while checking permissions.");
			return false;
		}
		User user = t_user.get();
		if (!MyUtil.user_has_role(FPR.get_s(), user, FPR.get_r("oink oink"))) {
			FPR.LOGGER().error(
					"OinkParser: Permission was inadequate for the command.");
			return false;
		}

		// Command can only be used in private channels.
		if (!(event.getChannel().equals(FPR.get_tc("oink commands"))
				| event.getChannel().equals(FPR.get_tc("oink agora")))) {
			FPR.LOGGER().error(
					"OinkParser: Inappropriate text channel to use the command.");
			return false;
		}

		// -----------
		if (tokens.length == 1) {
			return oink.default_oink(event);
		}
		boolean ret;
		switch (tokens[token_number].toLowerCase()) {
		case "stats":
			ret = oink.oink_stats(event);
			break;
		case "vote":
			ret = parse_vote(tokens, 1, event);
			break;
		default:
			FPR.LOGGER().error("OinkParser: Unknown ping command.");
			return false;
		}
		return ret;
	}

	private boolean parse_vote(String[] tokens, int token_number,
			MessageCreateEvent event) {
		return true;
	}
}
