package commands.utilities;

import java.awt.Color;
import java.util.Scanner;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import commands.ACommand;
import commands.ExCommandException;
import commands.ICommand;
import myutils.UTemplates;
import myutils.enums.ERoles;

@ACommand(
    command = "ping",
    permission = ERoles.DRIFTER,
    help = "Pings the bot in order to check if it is alive.")
public class Ping implements ICommand {
  @Override
  public boolean run(MessageCreateEvent event, Scanner scanner) throws ExCommandException {
    EmbedBuilder embed = UTemplates
        .embedTemplate("Pong!",
            "Don't worry " + event.getMessage().getUserAuthor().get().getMentionTag()
                + "!\n I'm alive and well :)",
            Color.BLUE);

    event.getChannel().sendMessage(embed);
    return true;
  }
}
