package commands;

import java.awt.Color;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import main.FPR;
import myutils.MyUtils;
import myutils.ServerRole;

public class CUtilities extends ICommand {
  public CUtilities() {
    super(FPR.getRole(ServerRole.DRIFTER.toString()));
  }

  @Override
  protected boolean exec(String[] command, MessageCreateEvent event) throws ECommandExecutionException {
    FPR.log().info(command[0]);
    if (command.length == 1) {
      switch (command[0]) {
        case "ping":
          ping(event);
        break;
      }
    }
    return false;
  }

  private void ping(MessageCreateEvent event) throws ECommandExecutionException {
    EmbedBuilder embed = MyUtils.embedTemplate("Pong!",
        "Don't worry " + event.getMessage().getUserAuthor().get().getMentionTag() + "!\n I'm alive and well :)",
        Color.BLUE);

    event.getChannel().sendMessage(embed);
  }

}
