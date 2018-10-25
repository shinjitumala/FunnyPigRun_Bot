package main;
import java.util.NoSuchElementException;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

import commands.MainCommand;

public class Main {
	/**
	 * Bot starts from here
	 * @param args Bot Token
	 */
	public static void main(String[] args) {
		if(args.length != 1) {
			System.err.println("Usage: FPRBot <Bot Token>");
			return;
		}
		
		String token = args[0];
		
		DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();
		
		api.addMessageCreateListener(new MainCommand());
		
		try {
			FPR.initialize(api.getServerById("414315826557091840").get());
			
		}catch(NoSuchElementException exF) {
			FPR.log().fatal("Failed to initialize variables for funny.pig.run Gaming. Exiting...");
			return;
		}
	}
}
