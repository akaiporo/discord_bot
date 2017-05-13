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

public class main extends ListenerAdapter{

	public static JDA jda;
	public static boolean relou = true;
	
	public static void main(String[] args)
            throws LoginException, RateLimitedException, InterruptedException
    {
		jda = new JDABuilder(AccountType.BOT).setToken("MzExOTU3NTA2MDYwNDUxODQ0.C_Xc-Q.MhFUI8yBgOzEg3Cnw27yDL3qXoM").buildBlocking();
		jda.addEventListener(new main());
    }

	@Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
    	
    	String botname = jda.getSelfUser().getName();
    	
    	String condition = "@BOT PINGU mode relou off";
    	
    	 if(event.getMessage().getContent().equals("@BOT PINGU mode relou off")){
         	if(event.getMember().getPermissions().contains(Permission.ADMINISTRATOR)){
 	        	relou = false;
 	        	event.getChannel().sendMessage("Mode désactivé.").complete();
         	}
         	else{
         		event.getChannel().sendMessage("Vous n'avez pas la permission de faire ça").complete();
         	}
         }
        if(event.getMessage().getContent().equals("@BOT PINGU mode relou on")){
        	if(event.getMember().getPermissions().contains(Permission.ADMINISTRATOR)){
	        	relou = true;
	        	event.getChannel().sendMessage("Mode activé.").complete();
        	}
        	else{
        		event.getChannel().sendMessage("Vous n'avez pas la permission de faire ça").complete();
        	}
        }
        if(event.getMessage().getContent().equals("@BOT PINGU help")){
        	if(relou){
        		event.getChannel().sendMessage("Nom : "+event.getGuild().getMember(jda.getSelfUser()).getEffectiveName()).complete();
        		event.getChannel().sendMessage("Le Mode relou est actuellement activé.").complete();
        		event.getChannel().sendMessage("Fonction : insulter Raze Sora.").complete();
        		event.getChannel().sendMessage("Serveur actif : "+event.getGuild().getName()).complete();
        	}
        	else{
        		event.getChannel().sendMessage("Nom : "+event.getGuild().getMember(jda.getSelfUser()).getEffectiveName()).complete();
        		event.getChannel().sendMessage("Le Mode est actuellement désactivé.").complete();
        		event.getChannel().sendMessage("Fonction : insulter Raze Sora.").complete();
        		event.getChannel().sendMessage("Serveur actif : "+event.getGuild().getName()).complete();
        	}
        }
        else
        {
        	/*MessageImpl mess = new MessageImpl("NOOT NOOT", event.getChannel(), false);
        	mess.setTTS(true);*/
        	
            System.out.printf("[%s][%s] %s: %s\n", event.getGuild().getName(),
                        event.getTextChannel().getName(), event.getMember().getEffectiveName(),
                        event.getMessage().getContent());
            String message = event.getMessage().getContent().toLowerCase();
            if(message.equals("poutiou pingu !") && !event.getAuthor().equals(botname)){
            
            	event.getChannel().sendMessage("NOOT NOOT").complete();
            }
            if(relou){
            	if(event.getGuild().getName().equals("Serveur de jeux 64")){
		            if((event.getAuthor().getName().equals("démaciaa") || 
		            		message.contains("pigom") ||
		            		message.contains("démaciaa") ||
		            		message.contains("@démaciaa")) && !event.getAuthor().getName().equals(botname)){
		            	event.getChannel().sendMessage("Pigom est un sacré enculé").complete();
		            }
            	}
            	else{
		            if((event.getAuthor().getName().equals("Raze Sora") || 
		            		message.contains("sora") ||
		            		message.contains("@Raze Sora") ||
		            		message.contains("raze sora") || 
		            		message.contains("Raze") ||
		            		message.contains("dora")) && !event.getAuthor().getName().equals(botname)){
		            	event.getChannel().sendMessage("PD SORA").complete();
		            }
            	}
            }
            	
     
        }
    }

}
