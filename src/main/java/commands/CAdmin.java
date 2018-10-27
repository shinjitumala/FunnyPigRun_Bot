package commands;

import org.javacord.api.event.message.MessageCreateEvent;

import main.FPR;
import myutils.ServerRole;

public class CAdmin extends ICommand {
  public CAdmin() {
    super(FPR.getRole(ServerRole.PIGGIES.toString()));
  }

  @Override
  protected boolean exec(String[] command, MessageCreateEvent event) throws ECommandExecutionException {
    return false;
  }

}
