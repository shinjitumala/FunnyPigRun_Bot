package main;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutionException;
import java.util.zip.InflaterInputStream;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

import commands.CAdmin;
import commands.CUtilities;
import commands.MainCommand;
import events.UserBan;
import events.UserJoin;
import events.UserLeave;
import events.UserUnban;
import myutils.SRole;
import myutils.ServerFiles;
import myutils.ServerRole;
import myutils.ServerTextChannel;

public class Main {
  /**
   * Bot starts from here
   *
   * @param args Bot Token
   */
  @SuppressWarnings("unchecked")
  public static void main(String[] args) {
    if (args.length != 1) {
      System.err.println("Usage: FPRBot <Bot Token>");
      return;
    }

    FPR.log().info("Starting bot...");

    // Log in
    String token = args[0];
    DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();

    try {
      // Initialize FPR.java
      FPR.initialize(api.getServerById("414315826557091840").get(), api.getYourself(), api.getOwner().get()); // funny.pig.run
      // Gaming

      // Channels
      FPR
          .addTextChannel(ServerTextChannel.TOWNHALL.toString(),
              FPR.server().getTextChannelById("414316475797602305").get());
      FPR
          .addTextChannel(ServerTextChannel.SELF_PROMOTION.toString(),
              FPR.server().getTextChannelById("414316416318177291").get());
      FPR
          .addTextChannel(ServerTextChannel.RULES.toString(),
              FPR.server().getTextChannelById("414318128516956163").get());
      FPR
          .addTextChannel(ServerTextChannel.NEWS.toString(),
              FPR.server().getTextChannelById("478821976186683402").get());

      FPR
          .addTextChannel(ServerTextChannel.OINK_GENERAL.toString(),
              FPR.server().getTextChannelById("414319326238343169").get());
      FPR
          .addTextChannel(ServerTextChannel.OINK_COMMANDS.toString(),
              FPR.server().getTextChannelById("452361154925297684").get());
      FPR
          .addTextChannel(ServerTextChannel.OINK_AGORA.toString(),
              FPR.server().getTextChannelById("492303377544249344").get());

      // Roles
      FPR.addRole(ServerRole.PIGGIES.toString(), FPR.server().getRoleById("414316813183090689").get());
      FPR.addRole(ServerRole.OINK.toString(), FPR.server().getRoleById("414316850231377921").get());
      FPR.addRole(ServerRole.DRIFTER.toString(), FPR.server().getRoleById("481333688043307008").get());

      try (ObjectInputStream ois = new ObjectInputStream(
          new InflaterInputStream(new FileInputStream(ServerFiles.NATIONALITY.toString())))) {
        Object obj = ois.readObject();
        if (obj instanceof ArrayList) {
          for (SRole r : (ArrayList<SRole>) obj) {
            FPR.nationality().put(r.tag(), r.restore());
          }
        } else {
          FPR
              .log()
              .warn("Main: Found object " + obj.getClass().toString() + " in file \""
                  + ServerFiles.NATIONALITY.toString() + "\". Object was saved to the wrong file?");
        }
      } catch (IOException | ClassNotFoundException e) {
        FPR.log().error("Main: Error while reading \"" + ServerFiles.NATIONALITY.toString() + "\".");
      }

    } catch (NoSuchElementException | InterruptedException | ExecutionException ex) {
      FPR.log().fatal("Main: Failed to initialize variables for funny.pig.run Gaming. Exiting...");
      return;
    }

    // Initialize command modules
    MainCommand commands = new MainCommand(">");
    commands.addModule(new CAdmin());
    commands.addModule(new CUtilities());

    // Add event listeners
    api.addMessageCreateListener(commands);
    api.addServerMemberJoinListener(new UserJoin());
    api.addServerMemberLeaveListener(new UserLeave());
    api.addServerMemberBanListener(new UserBan());
    api.addServerMemberUnbanListener(new UserUnban());

    FPR.log().info("Done initializing bot!");
  }
}
