package level;

import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

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

  }
}
