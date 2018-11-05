package level;

import java.awt.Color;
import java.util.NoSuchElementException;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import data.SUser;
import main.FPR;
import myutils.UTemplates;

public class LevelCore {
  public LevelCore() {

  }

  /**
   * Called when someone chats in the server
   *
   * @param event MessageCreateEvent
   */
  public void chat(MessageCreateEvent event) {
    User user = event.getMessage().getUserAuthor().get();

    try {
      SUser suser = SUser.find(user);
      int currentLevel = suser.level.level();
      suser.level.addXP(2);
      if (suser.level.updateLevel() > currentLevel) {
        EmbedBuilder embed = UTemplates
            .embedTemplate("Level up!", "Congratulations, " + user.getMentionTag() + "!\n" + "You have reached **level "
                + suser.level.level() + "**.", Color.CYAN);
        event.getChannel().sendMessage(embed);
      }
      if (!SUser.save()) {
        FPR.log().error("LevelCore: chat() > Error while saving user data.");
      }
    } catch (NoSuchElementException e) {
      FPR.log().error("LevelCore: chat() > User was not found in the database.");
    }
  }
}
