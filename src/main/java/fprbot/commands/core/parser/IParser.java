package fprbot.commands.core.parser;

import org.javacord.api.event.message.MessageCreateEvent;

public interface IParser {
	abstract public boolean parse(String[] tokens, int token_number,
			MessageCreateEvent event);
}
