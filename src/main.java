import java.util.HashMap;

import javax.security.auth.login.LoginException;

import Commands.MessageListenerActions;
import GraphicElements.statusFrame;
import net.dv8tion.jda.core.*;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.impl.MessageImpl;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class main extends ListenerAdapter{
	public static JDA jda;
	public static boolean relou = true;
	
	public static void main(String[] args)
            throws LoginException, RateLimitedException, InterruptedException
    {
		jda = new JDABuilder(AccountType.BOT).setToken("MzExOTU3NTA2MDYwNDUxODQ0.C_j2UA.Kw_S88wmdzUD32AeNMIIjsAmiMk").buildBlocking();
		 
		MessageListenerActions messageListener = new MessageListenerActions(jda);
		messageListener.addListener();
		 
		statusFrame frame = new statusFrame(jda);

    }


}
