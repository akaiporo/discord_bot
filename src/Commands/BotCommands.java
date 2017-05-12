package Commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class BotCommands implements Command{

	private final String HELP = "USAGE";
	
	public BotCommands() {
		
	}

	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void action(String[] args, MessageReceivedEvent event) {
		event.getTextChannel().sendMessage("Hello !");
		
	}

	@Override
	public String help() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean executed(boolean success, MessageReceivedEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

}
