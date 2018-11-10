package main;

import java.util.NoSuchElementException;
import java.util.concurrent.ExecutionException;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

import commands.MainCommand;
import commands.admin.RoleAdd;
import commands.utilities.Iam;
import commands.utilities.Ping;
import commands.utilities.RoleList;
import data.SRole;
import data.SUser;
import events.UserBan;
import events.UserJoin;
import events.UserLeave;
import events.UserUnban;
import myutils.enums.EFiles;
import myutils.enums.ERoles;
import myutils.enums.ETextChannels;

public class Main {
  /**
   * Bot starts from here
   *
   * @param args Bot Token
   */
  public static void main(String[] args) {
    if (args.length != 1) {
      System.err.println("Usage: FPRBot <Bot Token>");
      System.exit(1);
    }

    FPR.log().info("Starting bot...");

    // Log in
    String token = args[0];
    DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();

    try {
      // Initialize FPR.java
      FPR
          .initialize(api.getServerById("414315826557091840").get(), api.getYourself(),
              api.getOwner().get(), api); // funny.pig.run
      // Gaming

      // Channels
      FPR
          .addTextChannel(ETextChannels.TOWNHALL.toString(),
              FPR.server().getTextChannelById("414316475797602305").get());
      FPR
          .addTextChannel(ETextChannels.SELF_PROMOTION.toString(),
              FPR.server().getTextChannelById("414316416318177291").get());
      FPR
          .addTextChannel(ETextChannels.RULES.toString(),
              FPR.server().getTextChannelById("414318128516956163").get());
      FPR
          .addTextChannel(ETextChannels.NEWS.toString(),
              FPR.server().getTextChannelById("478821976186683402").get());

      FPR
          .addTextChannel(ETextChannels.OINK_GENERAL.toString(),
              FPR.server().getTextChannelById("414319326238343169").get());
      FPR
          .addTextChannel(ETextChannels.OINK_COMMANDS.toString(),
              FPR.server().getTextChannelById("452361154925297684").get());
      FPR
          .addTextChannel(ETextChannels.OINK_AGORA.toString(),
              FPR.server().getTextChannelById("492303377544249344").get());

      // Roles
      FPR.addRole(ERoles.PIGGIES.toString(), FPR.server().getRoleById("414316813183090689").get());
      FPR.addRole(ERoles.OINK.toString(), FPR.server().getRoleById("414316850231377921").get());
      FPR.addRole(ERoles.DRIFTER.toString(), FPR.server().getRoleById("481333688043307008").get());

      // Initialize roles
      if (!SRole.load()) {
        FPR.log().error("Main: Error while reading \"" + EFiles.NATIONALITY.toString() + "\".");
      }

      // Initialize members
      if (!SUser.load()) {
        FPR.log().error("Main: Error while initializing user data.");
      }

    } catch (NoSuchElementException | InterruptedException | ExecutionException ex) {
      FPR.log().fatal("Main: Failed to initialize variables for funny.pig.run Gaming. Exiting...");
      System.exit(1);
    }

    // Initialize command modules
    MainCommand commands = new MainCommand();
    commands.addModule(new RoleAdd());
    commands.addModule(new Ping());
    commands.addModule(new RoleList());
    commands.addModule(new Iam());

    // Add event listeners
    api.addMessageCreateListener(commands);
    api.addServerMemberJoinListener(new UserJoin());
    api.addServerMemberLeaveListener(new UserLeave());
    api.addServerMemberBanListener(new UserBan());
    api.addServerMemberUnbanListener(new UserUnban());

    FPR.log().info("Done initializing bot!");
  }
}
