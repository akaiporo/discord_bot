package Commands;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.TimerTask;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;


public class PublishLastVideo extends TimerTask{
	private static final String PROPERTIES_FILENAME = "youtube.properties";
	private static final String CHANNELSID_FILENAME = "channelsId.json";
	private String apiKey;
	private String lastVideoId;
	private List<String> channelsId = new ArrayList<String>();
	JsonParser parser = new JsonParser();
	protected PropertyChangeSupport propertyChangeSupport;

	public PublishLastVideo() throws JsonIOException, JsonSyntaxException, FileNotFoundException{
		
		propertyChangeSupport = new PropertyChangeSupport(this);
		
		Properties properties = new Properties();
        try {
            FileInputStream in = new FileInputStream(PROPERTIES_FILENAME); 
            properties.load(in);

        } catch (IOException e) {
            System.err.println("There was an error reading " + PROPERTIES_FILENAME + ": " + e.getCause()
                    + " : " + e.getMessage());
        }
        this.apiKey = properties.getProperty("youtube.apikey");
        this.getChannelsId(parser.parse(new JsonReader(new FileReader(CHANNELSID_FILENAME))).getAsJsonObject());
 
	}
	
	public void setVideoId(String videoId) {
        String oldVideoId = this.lastVideoId;
        this.lastVideoId = videoId;
        propertyChangeSupport.firePropertyChange("VideoId",oldVideoId, videoId);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

	@Override
	public void run() {
		String hour = Integer.toString(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
		hour+=":"+Integer.toString(Calendar.getInstance().get(Calendar.MINUTE));
		hour+=":"+Integer.toString(Calendar.getInstance().get(Calendar.SECOND));
		URL url;
		InputStream lastVideo;
		for(String id : this.channelsId){
			try {
				url = new URL("https://www.googleapis.com/youtube/v3/search?key="+this.apiKey+"&channelId="+id+"&part=snippet,id&order=date&maxResults=1");
				lastVideo = url.openStream();
				JsonElement result = parser.parse(new InputStreamReader(lastVideo, "UTF-8"));
				JsonObject json = result.getAsJsonObject();
				String tmp  = this.iterateOverJson(json.entrySet());
				if(!tmp.equals(this.lastVideoId)){
					System.out.println(lastVideoId+", new : "+tmp);
					this.setVideoId(tmp);
				}
			} catch (IOException e) {
				System.out.println(String.format("[%s] [Error] The URL failed to return a result, or your request to the Google Youtupe API get rejected", hour));
			} 
		}		
	}
	
	/**
	 * Sûrement sale. A modifier
	 * @param entrySet : ensemble de key=>val dans le json retourné par la requête HTTP
	 * @return new videoId
	 */
	private String iterateOverJson(Set<Entry<String, JsonElement>> entrySet){
		String videoId = "";
		
		for(Map.Entry<String, JsonElement> entry : entrySet){
			if(entry.getKey().equals("items")){
				JsonArray json = entry.getValue().getAsJsonArray(); 
				for(JsonElement je : json){
					JsonObject id = je.getAsJsonObject().get("id").getAsJsonObject();
					videoId = id.get("videoId").getAsString();
				}
			}
		}
		
		return videoId;
	}
	/**
	 * Récupère les id des chaines youtubes dans le fichier JSON
	 * @param ids : Json Object contenant les datas du fichier json
	 */
	private void getChannelsId(JsonObject ids){
		Set<Entry<String, JsonElement>> entrySet = ids.entrySet();
		for(Map.Entry<String, JsonElement> entry : entrySet){
			JsonArray json = entry.getValue().getAsJsonArray(); 
			for(JsonElement je : json){
				this.channelsId.add(je.getAsString());
			}
		}
	}

}
