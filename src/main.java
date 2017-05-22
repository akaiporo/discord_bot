
import java.io.FileNotFoundException;
import java.util.Timer;
import javax.security.auth.login.LoginException;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import Commands.MessageListenerActions;
import Commands.PublishLastVideo;
import GraphicElements.statusFrame;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class main extends ListenerAdapter{
	public static JDA jda;
	public static boolean relou = true;
	
	public static void main(String[] args)
            throws LoginException, RateLimitedException, InterruptedException, JsonIOException, JsonSyntaxException, FileNotFoundException
    {
		jda = new JDABuilder(AccountType.BOT).setToken("MzEyOTE0MDczNDE3NDE2NzA0.C_h_dw.WP_wu9RoHwVHzWUeeit8DVhJw4U").buildBlocking();
		//in dev 	    MzEyOTE0MDczNDE3NDE2NzA0.C_h_dw.WP_wu9RoHwVHzWUeeit8DVhJw4U
		//sausage prod  MzExOTU3NTA2MDYwNDUxODQ0.C_j2UA.Kw_S88wmdzUD32AeNMIIjsAmiMk 
		MessageListenerActions messageListener = new MessageListenerActions(jda);
		PublishLastVideo lastPublished = new PublishLastVideo();
		lastPublished.addPropertyChangeListener(messageListener);
		messageListener.addListener();
		
		Timer timer = new Timer();
		timer.schedule(lastPublished, 0, 900000);
		 
		statusFrame frame = new statusFrame(jda);

    }
}
