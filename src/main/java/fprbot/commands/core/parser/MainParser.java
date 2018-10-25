package fprbot.commands.core.parser;

import org.javacord.api.event.message.MessageCreateEvent;

import fprbot.FPR;
import fprbot.FPR_Server_Objects;

public class MainParser extends FPR implements IParser {

	public MainParser(FPR_Server_Objects FPR) {
		super(FPR);
	}

	@Override
	public boolean parse(String[] tokens, int token_number,
			MessageCreateEvent event) {
		IParser parser;
		Boolean ret;
		switch (tokens[token_number].toLowerCase()) {
		case "ping":
			parser = new PingParser(FPR);
			ret = parser.parse(tokens, 1, event);
			break;
		case "oink":
			parser = new OinkParser(FPR);
			ret = parser.parse(tokens, 1, event);
			break;
		default:
			FPR.LOGGER().error("MainParser: Unknown command.");
			return false;
		}
		return ret;
	}
}
