package myutils;

import java.awt.Color;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.Role;

import commands.MainCommand;
import main.FPR;

public class UTemplates {
  public static EmbedBuilder embedTemplate(String title, String Description, Color color) {
    EmbedBuilder ret = new EmbedBuilder()
        .setTimestampToNow()
          .setAuthor(FPR.bot().getDisplayName(FPR.server()),
              "https://github.com/shinjitumala/FunnyPigRun_Bot", FPR.bot().getAvatar())
          .setFooter("Created by " + FPR.me().getName(), FPR.me().getAvatar())
          .setTitle(title)
          .setDescription(Description)
          .setColor(color);

    return ret;
  }

  public static EmbedBuilder helpTemplate(String command, String Description, Role permission) {
    EmbedBuilder ret = UTemplates
        .embedTemplate(MainCommand.PREFIX + command, Description, Color.GRAY)
          .addField("Permissions", "Must have role " + permission.getMentionTag() + " or above.\n"
              + "User the command `" + MainCommand.PREFIX + "rolelist` to see role hierarchy.");

    return ret;
  }

  public static EmbedBuilder errorTemplate(String reason, String command) {
    EmbedBuilder ret = UTemplates
        .embedTemplate("Oops!", reason, Color.RED)
          .addField("Help", "For more information about the command, use `" + MainCommand.PREFIX
              + "help " + command + "`.\n"
              + "If you think this is a bug, please feel free to create a new issue on github by "
              + "[clicking here](https://github.com/shinjitumala/FunnyPigRun_Bot/issues)."
              + " Or you could simply tag " + FPR.me().getMentionTag() + " to get his attention.");

    return ret;
  }

  public static EmbedBuilder errorTemplate(String reason) {
    EmbedBuilder ret = UTemplates.embedTemplate("Oops!", reason, Color.RED);

    return ret;
  }

  public static EmbedBuilder completeTemplate(String message) {
    EmbedBuilder ret = UTemplates.embedTemplate("Done!", message, Color.BLUE);
    return ret;
  }
}
