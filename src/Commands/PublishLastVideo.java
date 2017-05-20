package Commands;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.TimerTask;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class PublishLastVideo extends TimerTask{
	private static final String PROPERTIES_FILENAME = "youtube.properties";
	private String apiKey;
	private String lastVideoId;
	private List<String> channelsId = new ArrayList<String>();
	JsonParser parser = new JsonParser();
	protected PropertyChangeSupport propertyChangeSupport;

	public PublishLastVideo(){
		
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
        this.channelsId.add("UC-iZs55_7agxY5s4I-slhmw"); //soulnSane channel id
 
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
					this.setVideoId(tmp);
				}
			} catch (IOException e) {
				System.out.println("URL could not be read");
			} 
		}		
	}
	
	/**
	 * S�rement sale. A modifier
	 * @param entrySet : ensemble de key=>val dans le json retourn� par la requ�te HTTP
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

}