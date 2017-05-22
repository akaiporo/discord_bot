package Commands;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberNickChangeEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.managers.AudioManager;

public class MessageListenerActions extends ListenerAdapter implements PropertyChangeListener{

	private boolean relou = false;
	private JDA jda;
	private String botname;
	private static final String CHANNELSID_FILENAME = "channelsId.json";
	
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
     
        if(event.getMessage().getContent().contains(String.format("@%s cherche : ", botname)) && !event.getAuthor().getName().equals(botName)){
        	String search = message.substring(12+botname.length());
        	System.out.println(search);
        	try {
				YoutubeActions ytActions = new YoutubeActions();
				event.getChannel().sendMessage(ytActions.search(search)).complete();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
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

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		if(event.getPropertyName().equals("VideoId")){
			jda.getGuildById("311953226851155968").getPublicChannel().sendMessage("Une nouvelle vidéo est sortie !: https://youtube.com/watch?v="+event.getNewValue()).complete();
		}
		
	}
	
	/*@Override
	public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {
		if(event.getAuthor().getMutualGuilds().contains(this.jda.getGuildById("311953226851155968"))){
			if(jda.getGuildById("311953226851155968").getMember(event.getAuthor()).getPermissions().contains(Permission.ADMINISTRATOR)){
				if(event.getMessage().getContent().contains("/add ")){
					String id = event.getMessage().getContent().substring(5);
					try {
						this.setChannelsId(new JsonParser().parse(new JsonReader(new FileReader(CHANNELSID_FILENAME))).getAsJsonObject(), id);
					} catch (JsonIOException e) {
						
						e.printStackTrace();
					} catch (JsonSyntaxException e) {
					
						e.printStackTrace();
					} catch (FileNotFoundException e) {
						
						e.printStackTrace();
					}
					
				}
			}
			else{
				event.getAuthor().getPrivateChannel().sendMessage("Seul l'administrateur de votre serveur peut me parler.").complete();
			}
		}
	}
	
	private void setChannelsId(JsonObject ids, String id){
		Set<Entry<String, JsonElement>> entrySet = ids.entrySet();
		for(Map.Entry<String, JsonElement> entry : entrySet){
			JsonArray json = entry.getValue().getAsJsonArray(); 
			entry.setValue();
		}
	}*/
}
