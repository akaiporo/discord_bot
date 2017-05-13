import java.util.HashMap;

import javax.security.auth.login.LoginException;

import Commands.BotCommands;
import Commands.Command;
import net.dv8tion.jda.core.*;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.impl.MessageImpl;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Main_Bot extends ListenerAdapter{

	public static JDA jda;
	public static boolean relou = true;
	
	public static void main(String[] args)
            throws LoginException, RateLimitedException, InterruptedException
    {
		jda = new JDABuilder(AccountType.BOT).setToken("MzEyOTE0MDczNDE3NDE2NzA0.C_h_dw.WP_wu9RoHwVHzWUeeit8DVhJw4U").buildBlocking();
		jda.getPresence().getStatus();
		
		MessageListenerActions messageListener = new MessageListenerActions(jda);
		messageListener.addListener();
    }

	

}
