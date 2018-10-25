package fprbot.commands.core.parser;

import java.util.List;

import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

public class MyUtil {
	public static boolean user_has_role(Server server, User user, Role role) {
		List<Role> roles = user.getRoles(server);

		return roles.stream().filter(r -> r.equals(role)).findFirst()
				.isPresent();
	}
}
