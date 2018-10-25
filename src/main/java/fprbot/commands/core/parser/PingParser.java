package fprbot.commands.core.parser;

import org.javacord.api.event.message.MessageCreateEvent;

import fprbot.FPR;
import fprbot.FPR_Server_Objects;
import fprbot.commands.core.commands.Ping;

public class PingParser extends FPR implements IParser {
	private final Ping ping;

	public PingParser(FPR_Server_Objects FPR) {
		super(FPR);
		ping = new Ping(FPR);
	}

	@Override
	public boolean parse(String[] tokens, int token_number,
			MessageCreateEvent event) {
		// -----------
		// Do a permission check here
		// NOTE: Everyone can use the ping command.
		// -----------
		boolean ret;
		if (tokens.length == token_number) {
			ret = ping.default_ping(event);
		} else {
			switch (tokens[token_number].toLowerCase()) {
			case "pong":
				ret = ping.ping_pong(event);
				break;
			default:
				FPR.LOGGER().error("PingParser: Unknown ping command.");
				return false;
			}
		}

		return ret;
	}

}
