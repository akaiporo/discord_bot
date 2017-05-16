package Commands;
import net.dv8tion.jda.client.events.call.CallCreateEvent;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.DisconnectEvent;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.ReconnectedEvent;
import net.dv8tion.jda.core.events.guild.GuildJoinEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberNickChangeEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.user.UserGameUpdateEvent;
import net.dv8tion.jda.core.events.user.UserOnlineStatusUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.managers.AudioManager;

public class MessageListenerActions extends ListenerAdapter{

	private boolean relou = false;
	private JDA jda;
	private String botname;
	
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
    	
    	this.botname = event.getGuild().getMember(jda.getSelfUser()).getEffectiveName();
    	String botName = jda.getSelfUser().getName();

    	if(event.getMessage().getContent().equals(String.format("@%s /off", botname)) && !event.getAuthor().getName().equals(botName)){
         	this.swearOff(event);
        }
        if(event.getMessage().getContent().equals(String.format("@%s /on", botname)) && !event.getAuthor().getName().equals(botName)){
        	this.swearOn(event);
        }
        String condition = String.format("@%s connect to", botname);
        if(event.getMessage().getContent().contains(condition) && !event.getAuthor().equals(botName)){
        	this.voiceConnection(event, condition);
        }

        if(event.getMessage().getContent().equals("@"+event.getGuild().getMember(jda.getSelfUser()).getEffectiveName()+" help") 
        		&& !event.getAuthor().getName().equals(botName)){
        	
        	this.help(event);
        }

        String message = event.getMessage().getContent().toLowerCase();
            
        if(message.equals("poutiou pingu !") && !event.getAuthor().getName().equals(botName)){
            
        	event.getChannel().sendMessage("NOOT NOOT").complete();
        }
        if(relou){
		    this.insulterSora(event, message);
         }
    }
	
	private void voiceConnection(MessageReceivedEvent event, String condition){
		if(event.getMember().getPermissions().contains(Permission.ADMINISTRATOR)){
    		int length = condition.length(); 
        	String channelName = event.getMessage().getContent().substring(length+1);
        	for(VoiceChannel vc : event.getGuild().getVoiceChannelsByName(channelName, true)){
        		if(vc.getName().toLowerCase().equals(channelName.toLowerCase())){
        			AudioManager manager = vc.getGuild().getAudioManager();
        			manager.openAudioConnection(vc);
        		}
        	}

    	}
    	else{
    		event.getChannel().sendMessage("Vous n'avez pas la permission de faire ça").complete();
    	}
	}
	private void help(MessageReceivedEvent event){
		event.getGuild().getTextChannelById("313747898258948106").sendMessage("Nom : "+event.getGuild().getMember(jda.getSelfUser()).getEffectiveName()).complete();
		event.getGuild().getTextChannelById("313747898258948106").sendMessage("Serveur actif : "+event.getGuild().getName()).complete();
		event.getGuild().getTextChannelById("313747898258948106").sendMessage("Fonctions actives : ").complete();
		event.getGuild().getTextChannelById("313747898258948106").sendMessage("	@"+this.botname+" connect to [nom du channel vocal]").complete();
		event.getGuild().getTextChannelById("313747898258948106").sendMessage("	@"+this.botname+" /on").complete();
		event.getGuild().getTextChannelById("313747898258948106").sendMessage("	@"+this.botname+" /off").complete();
		event.getGuild().getTextChannelById("313747898258948106").sendMessage("	@"+this.botname+" help").complete();
		
    	if(relou){
    		event.getGuild().getTextChannelById("313747898258948106").sendMessage("Le mode \"insultes\" est actuellement activé.").complete();
    	}
    	else{
    		event.getGuild().getTextChannelById("313747898258948106").sendMessage("Le mode \"insultes\" actuellement désactivé.").complete();
    	}
	}
	private void swearOn(MessageReceivedEvent event){
		if(!event.getAuthor().getName().equals(botname)){
			if(event.getMember().getPermissions().contains(Permission.ADMINISTRATOR) || event.getAuthor().getId().equals("207659944483225604")){
	        	relou = true;
	        	event.getChannel().sendMessage("Mode activé.").complete();
	    	}
	    	else{
	    		event.getChannel().sendMessage("Vous n'avez pas la permission de faire ça").complete();
	    	}
		}	
	}
	private void swearOff(MessageReceivedEvent event){
		if(!event.getAuthor().getName().equals(botname)){
			if(event.getMember().getPermissions().contains(Permission.ADMINISTRATOR) || event.getAuthor().getId().equals("207659944483225604")){
		        	relou = false;
		        	event.getChannel().sendMessage("Mode désactivé.").complete();
	     	}
	     	else{
	     		event.getChannel().sendMessage("Vous n'avez pas la permission de faire ça").complete();
	     	}
		}
	}
	private void insulterSora(MessageReceivedEvent event, String message){
		String botName = jda.getSelfUser().getName();
		if((   message.contains("sora") ||
		       message.contains("@Raze Sora") ||
		       message.contains("raze sora") || 
		       message.contains("Raze") ||
		       message.contains("dora")) && !event.getAuthor().getName().equals(botName)){
			
		           event.getChannel().sendMessage("PD SORA").complete();
		}
	}
	
	@Override
	public void onGuildMemberJoin(GuildMemberJoinEvent event) {
		event.getGuild().getPublicChannel().sendMessage(String.format("Bienvenue à %s qui vient de nous rejoindre !", event.getMember().getEffectiveName())).complete();
	}
	
	@Override
	public void onGuildMemberLeave(GuildMemberLeaveEvent event) {
		event.getGuild().getPublicChannel().sendMessage(String.format("%s a quitter le serveur. Aurevoir !", event.getMember().getEffectiveName())).complete();
	}
	
	@Override
	public void onGuildMemberNickChange(GuildMemberNickChangeEvent event) {
		if(event.getNewNick() != null && event.getPrevNick() != null){
			event.getGuild().getTextChannelById("313747898258948106").sendMessage(String.format("%s s'appelle désormais %s !", event.getPrevNick(), event.getNewNick())).complete();
		}
		
		else if(event.getNewNick() == null){
			event.getGuild().getTextChannelById("313747898258948106").sendMessage(String.format("%s s'appelle désormais %s !", event.getPrevNick(), event.getMember().getEffectiveName())).complete();
		}
		
		/*else{
			event.getGuild().getPublicChannel().sendMessage(String.format("%s s'appelle désormais %s !", event.getMember().getEffectiveName(), event.getNewNick())).complete();
		}*/
		
	}
	
	/**
	 * Ça marche ! Mais bon. On flic pas, hein ? 
	 */
	/*@Override
	public void onUserOnlineStatusUpdate(UserOnlineStatusUpdateEvent event) {
		if(event.getPreviousOnlineStatus() == OnlineStatus.OFFLINE){
			event.getGuild().getPublicChannel().sendMessage(String.format("%s s'est connecté !",event.getUser().getName())).complete();
		}
		else if(event.getPreviousOnlineStatus() == OnlineStatus.ONLINE){
			event.getGuild().getPublicChannel().sendMessage(String.format("%s s'est déconnecté !",event.getUser().getName())).complete();
		}
	}*/
}
