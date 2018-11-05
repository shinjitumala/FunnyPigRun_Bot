package events;

import java.awt.Color;

import org.javacord.api.entity.message.embed.EmbedBuilder;
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

    EmbedBuilder embed = UTemplates
        .embedTemplate("Uh oh!", user.getMentionTag() + " has been banned from the server!", Color.RED)
        .addField(FPR.server().getName(),
            "This server is now left with " + FPR.server().getMemberCount() + " members!");

    FPR.getTextChannel(ETextChannels.TOWNHALL.toString()).sendMessage(embed);
  }

}
