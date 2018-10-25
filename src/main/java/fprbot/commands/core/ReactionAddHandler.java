package fprbot.commands.core;

import org.javacord.api.event.message.reaction.ReactionAddEvent;
import org.javacord.api.listener.message.reaction.ReactionAddListener;

import fprbot.FPR;
import fprbot.FPR_Server_Objects;

public class ReactionAddHandler extends FPR implements ReactionAddListener {

	public ReactionAddHandler(FPR_Server_Objects FPR) {
		super(FPR);
	}

	public void onReactionAdd(ReactionAddEvent event) {
		// TODO Auto-generated method stub

	}
}
