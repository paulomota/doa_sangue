package br.com.doasangue.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

public class ChatService {
	
	private static ChatService instance;
	
	public ChatService(){
		if(instance == null){
			
		}
		
	}
	
	public ChatService getInstance() throws FileNotFoundException{
		if(instance == null){
			instance = new ChatService();
			
			FirebaseOptions options = new FirebaseOptions.Builder()
			  .setServiceAccount(new FileInputStream("path/to/serviceAccountCredentials.json"))
			  .setDatabaseUrl("https://databaseName.firebaseio.com/")
			  .build();
			FirebaseApp.initializeApp(options);
		}
		
		return instance;
	}

}
