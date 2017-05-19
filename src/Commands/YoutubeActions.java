package Commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Lists;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Channel;

import Authorization.Auth;


public class YoutubeActions {

	private static YouTube youtube;
	
	List<String> scopes = Lists.newArrayList();

	public YoutubeActions() throws IOException {
		scopes.add("https://www.googleapis.com/auth/youtube");
		//Credential credential = Auth.authorize(scopes, "youtubeactions");
		
        // This object is used to make YouTube Data API requests.
        youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
            public void initialize(HttpRequest request) throws IOException {
            }
        }).setApplicationName("").build();
       
        	
	}
	
	public List<String> retrieveVideoUrl(String textQuery, int maxResults, boolean filter, int timeout) 
			throws Exception {
		
		return new ArrayList<String>();
	
	}
	

}
