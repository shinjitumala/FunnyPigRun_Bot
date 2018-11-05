package events;

import java.awt.Color;

import org.javacord.api.entity.message.embed.EmbedBuilder;
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

    EmbedBuilder embed = UTemplates
        .embedTemplate("Goodbye!",
            "We will miss you, " + user.getMentionTag() + "! \n" + "You are welcomed back anytime :wave:", Color.GRAY)
        .addField(FPR.server().getName(),
            "This server is now left with " + FPR.server().getMemberCount() + " members!");

    FPR.getTextChannel(ETextChannels.TOWNHALL.toString()).sendMessage(embed);
  }

}
