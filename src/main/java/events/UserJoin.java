package events;

import java.awt.Color;
import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.server.member.ServerMemberJoinEvent;
import org.javacord.api.listener.server.member.ServerMemberJoinListener;

import commands.MainCommand;
import data.SUser;
import main.FPR;
import myutils.UTemplates;
import myutils.enums.ERoles;
import myutils.enums.ETextChannels;

public class UserJoin implements ServerMemberJoinListener {

  @Override
  public void onServerMemberJoin(ServerMemberJoinEvent event) {
    User user = event.getUser();

    SUser.add(user);

    EmbedBuilder embed = UTemplates
        .embedTemplate("Welcome to " + FPR.server().getName(),
            "Hello, there " + user.getMentionTag() + "!\n"
                + "We welcome you to the family :kissing_smiling_eyes:",
            Color.GREEN)
          .addField("Rules",
              "Please take the time and read "
                  + FPR.textChannels.get(ETextChannels.RULES.toString()).getMentionTag()
                  + " so that we don't end up in any misunderstandings.")
          .addField("Roles",
              "All users in this server is given a role based on their nationality."
                  + " We can see how gamers from all over the world are coming together here.\n\n"
                  + "For now, you will be given the role "
                  + FPR.roles.get(ERoles.DRIFTER.toString()).getMentionTag() + ".\n\n"
                  + "You can assign yourself in an exisiting role using the `" + MainCommand.PREFIX
                  + "iam <role tag>` command. You can notify " + FPR.me().getMentionTag()
                  + " to create a new role for you by using the `" + MainCommand.PREFIX
                  + "iam NULL` command.")
          .addField("The shameless self promotion", FPR.me().getDisplayName(FPR.server())
              + " does some stuff online.\n" + "You can find out more by checking "
              + FPR.textChannels.get(ETextChannels.SELF_PROMOTION.toString()).getMentionTag() + ".")
          .addField(FPR.server().getName(),
              "This server now has " + FPR.server().getMemberCount() + " members!");

    FPR.textChannels.get(ETextChannels.TOWNHALL.toString()).sendMessage(embed);

    try {
      user.addRole(FPR.roles.get(ERoles.DRIFTER.toString())).get();
    } catch (InterruptedException | ExecutionException e) {
      FPR.logger.error("UserJoin: Error adding role to User " + user.getDiscriminatedName());
    }
  }

}
