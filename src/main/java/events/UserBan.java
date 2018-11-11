package events;

import java.awt.Color;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Ban;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.server.member.ServerMemberBanEvent;
import org.javacord.api.listener.server.member.ServerMemberBanListener;

import main.FPR;
import myutils.UTemplates;
import myutils.enums.ETextChannels;

public class UserBan implements ServerMemberBanListener {

  @Override
  public void onServerMemberBan(ServerMemberBanEvent event) {
    User user = event.getUser();
    String reason = "None.";

    try {
      Collection<Ban> bans = FPR.server().getBans().get();
      for (Ban b : bans) {
        if (b.getUser().equals(user)) {
          reason = b.getReason().get();
        }
      }
    } catch (InterruptedException | ExecutionException | NoSuchElementException e) {
      FPR.logger.error("UserLeave: Error getting reason for the ban.");
    }

    EmbedBuilder embed = UTemplates
        .embedTemplate("Uh oh!",
            user.getMentionTag() + " has been banned from the server!\n" + "Reason:\n```" + reason
                + "```",
            Color.RED)
          .addField(FPR.server().getName(),
              "This server is now left with " + FPR.server().getMemberCount() + " members!");

    FPR.textChannels.get(ETextChannels.TOWNHALL.toString()).sendMessage(embed);
  }

}
