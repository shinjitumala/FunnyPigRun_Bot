package events;

import java.awt.Color;
import java.util.Collection;
import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Ban;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.server.member.ServerMemberLeaveEvent;
import org.javacord.api.listener.server.member.ServerMemberLeaveListener;

import main.FPR;
import myutils.UTemplates;
import myutils.enums.ETextChannels;

public class UserLeave implements ServerMemberLeaveListener {

  @Override
  public void onServerMemberLeave(ServerMemberLeaveEvent event) {
    User user = event.getUser();

    try {
      Collection<Ban> bans = FPR.server().getBans().get();
      for (Ban b : bans) {
        if (b.getUser().equals(user)) {
          return;
        }
      }
    } catch (InterruptedException | ExecutionException e) {
      FPR.logger.error("UserLeave: Error getting the list of bans.");
    }

    EmbedBuilder embed = UTemplates
        .embedTemplate("Goodbye!",
            "We will miss you, " + user.getMentionTag() + "! \n"
                + "You are welcomed back anytime :wave:",
            Color.GRAY)
          .addField(FPR.server().getName(),
              "This server is now left with " + FPR.server().getMemberCount() + " members!");

    FPR.textChannels.get(ETextChannels.TOWNHALL.toString()).sendMessage(embed);
  }

}
