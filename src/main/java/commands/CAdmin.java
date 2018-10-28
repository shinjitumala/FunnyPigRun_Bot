package commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.event.message.MessageCreateEvent;

import main.FPR;
import myutils.MyUtils;
import myutils.SRole;
import myutils.ServerFiles;
import myutils.ServerRole;

public class CAdmin extends ICommand {
  public CAdmin() {
    super(FPR.getRole(ServerRole.PIGGIES.toString()));
  }

  @Override
  protected boolean exec(String[] command, MessageCreateEvent event) throws ECommandExecutionException {
    if (command.length >= 1) {
      switch (command[0]) {
        case "role":
          if (command.length >= 2) {
            switch (command[1]) {
              case "add":
                if (command.length == 4) {
                  return roleAdd(event, command[2], command[3]);
                }
              break;
            }
          }
        break;
      }
    }
    return false;
  }

  private boolean roleAdd(MessageCreateEvent event, String id, String tag) throws ECommandExecutionException {

    Role role;
    try {
      long l = Long.parseLong(id);
      role = FPR.server().getRoleById(l).get();
    } catch (NoSuchElementException | NumberFormatException e) {
      FPR.log().error("CAdmin: addRole() > Could not find a role given ID!");
      throw new ECommandExecutionException("Could not find a role with that ID.", "role");
    }

    FPR.nationality().put(tag, role);

    ArrayList<SRole> list = new ArrayList<>();
    for (String key : FPR.nationality().keySet()) {
      list.add(new SRole(FPR.nationality().get(key), key));
    }
    try {
      MyUtils.writeObject(list, ServerFiles.NATIONALITY.toString());
    } catch (IOException e) {
      FPR.log().error("CAdmin: roleAdd() > Error occured while writing to " + ServerFiles.NATIONALITY.toString() + ".");
      throw new ECommandExecutionException("Error writing to file", "role");
    }

    EmbedBuilder embed = MyUtils
        .completeTemplate("The role " + role.getMentionTag() + " was added with the tag \"" + tag + "\".\n"
            + "You can now change your nationality to this role by using the `" + MainCommand.prefix() + "iam " + tag
            + "` command.");
    event.getChannel().sendMessage(embed);

    return true;
  }

}
