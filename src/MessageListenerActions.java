import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class MessageListenerActions extends ListenerAdapter{

	private boolean relou = true;
	private JDA jda;
	
	public MessageListenerActions(JDA jda){
		if(this.jda == null){
			this.jda = jda;
		}
	}
	
	public void addListener(){
		jda.addEventListener(new MessageListenerActions(jda));
	}
	
	public JDA getJda(){
		return this.jda;
	}
	
	@Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
		System.out.println("passed");
    	
    	String botname = event.getGuild().getMember(jda.getSelfUser()).getEffectiveName();
    	
    	 if(event.getMessage().getContent().equals(String.format("@%s /off", botname))){
         	if(event.getMember().getPermissions().contains(Permission.ADMINISTRATOR)){
 	        	relou = false;
 	        	event.getChannel().sendMessage("Mode désactivé.").complete();
         	}
         	else{
         		event.getChannel().sendMessage("Vous n'avez pas la permission de faire ça").complete();
         	}
         }
        if(event.getMessage().getContent().equals(String.format("@%s /on", botname))){
        	if(event.getMember().getPermissions().contains(Permission.ADMINISTRATOR)){
	        	relou = true;
	        	event.getChannel().sendMessage("Mode activé.").complete();
        	}
        	else{
        		event.getChannel().sendMessage("Vous n'avez pas la permission de faire ça").complete();
        	}
        }

        if(event.getMessage().getContent().equals("@"+event.getGuild().getMember(jda.getSelfUser()).getEffectiveName()+" help")){
        	
        	event.getChannel().sendMessage("Nom : "+event.getGuild().getMember(jda.getSelfUser()).getEffectiveName()).complete();
        	event.getChannel().sendMessage("Fonction : Tester les nouvelles fonctions avant déploiement.").complete();
    		event.getChannel().sendMessage("Serveur actif : "+event.getGuild().getName()).complete();
    		
        	if(relou){
        		event.getChannel().sendMessage("Le Mode relou est actuellement activé.").complete();
        	}
        	else{
        		event.getChannel().sendMessage("Le Mode est actuellement désactivé.").complete();
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
