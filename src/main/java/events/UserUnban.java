package events;

import java.awt.Color;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.server.member.ServerMemberUnbanEvent;
import org.javacord.api.listener.server.member.ServerMemberUnbanListener;

import main.FPR;
import myutils.MyUtils;
import myutils.ServerTextChannel;

public class UserUnban implements ServerMemberUnbanListener {

  @Override
  public void onServerMemberUnban(ServerMemberUnbanEvent event) {
    User user = event.getUser();

    EmbedBuilder embed = MyUtils
        .embedTemplate("Hm...", user.getMentionTag() + " has been unbanned from the server!", Color.YELLOW);

    FPR.getTextChannel(ServerTextChannel.TOWNHALL.toString()).sendMessage(embed);
  }

}
