package fprbot.commands.core;

import org.javacord.api.event.message.reaction.ReactionRemoveEvent;
import org.javacord.api.listener.message.reaction.ReactionRemoveListener;

import fprbot.FPR;
import fprbot.FPR_Server_Objects;

public class ReactionRemoveHandler extends FPR
		implements ReactionRemoveListener {

	public ReactionRemoveHandler(FPR_Server_Objects FPR) {
		super(FPR);
	}

	public void onReactionRemove(ReactionRemoveEvent event) {
		// TODO Auto-generated method stub

	}
}
