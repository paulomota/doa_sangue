package br.com.doasangue.service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.bind.JAXBException;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import br.com.doasangue.exception.ValidationException;
import br.com.doasangue.model.User;

public class PushNotificationService {

	private static final String PROJECT_KEY = "AIzaSyDNwjKb4z1qP_idT2PNPBwBUQQSw7fZMqQ";
	
	public static void main(String args[]) throws IOException, JSONException, JAXBException, ValidationException{
		System.out.println("Inicio\n");

		PushNotificationService ps = new PushNotificationService();
		ps.init();
		
		User sender = new User();
		sender.setId(1l);
		sender.setName("Remetente");
		sender.setEmail("remetente@gmail.com");
		
		User receiver = new User();
		receiver.setDeviceToken("bk3RNwTe3H0:CI2k_HHwgIpoDKCIZvvDMExUdFQ3P1");
		
		sendPushNotification(sender, receiver, "oi mundo");
		
		System.out.println("\nFim");
	}
	
	private void init() throws FileNotFoundException{
		FirebaseOptions options = new FirebaseOptions.Builder()
		  .setServiceAccount(new FileInputStream("src/main/resources/Doasangue-bfee8f402192.json"))
		  .setDatabaseUrl("https://doasangue-19a5f.firebaseio.com/")
		  .build();
		
		FirebaseApp.initializeApp(options);
	}
	
	public static String sendPushNotification(User sender, User receiver, String message) throws IOException, JSONException, JAXBException, ValidationException {
		if(receiver.getDeviceToken() == null){
			throw new ValidationException("O usuário receber não possui um device token cadastrado");
		}
		
		JSONObject senderData = new JSONObject();
		senderData.put("id", sender.getId());
		senderData.put("name", sender.getName());
		senderData.put("email", sender.getEmail());
		senderData.put("role", sender.getRole());
		senderData.put("gender", sender.getGender());
		senderData.put("weight", sender.getWeight());
		senderData.put("city", sender.getCity());
		senderData.put("urgency", sender.getUrgency());
		senderData.put("urgency", sender.getUrgency());
		senderData.put("bloodType", sender.getBloodType());
		senderData.put("picturePath", sender.getPicturePath());
		senderData.put("lat", sender.getLat());
		senderData.put("lng", sender.getLng());
		
		if(sender.getBirthdate() != null){
			senderData.put("birthdate", sender.getBirthdate().longValue());
		}
		
		JSONObject messageData = new JSONObject();
		messageData.put("message", message);
		messageData.put("sender", senderData);
		
		JSONObject requestData = new JSONObject();
		requestData.put("to", receiver.getDeviceToken());
		requestData.put("data", messageData);
		
		System.out.println("\n\nrequestData: " + requestData + "\n");
		
		URL url = new URL("https://fcm.googleapis.com/fcm/send");
		HttpURLConnection conn= (HttpURLConnection) url.openConnection(); 
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Authorization", "key="+PROJECT_KEY);
		conn.setRequestProperty("Content-Type", "application/json"); 
		conn.setRequestProperty("charset", "utf-8");
		
		OutputStream os = conn.getOutputStream();
		os.write(requestData.toString().getBytes("UTF-8"));
		os.flush();
		
		if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
			System.out.println("\nErrorStream: " + conn.getErrorStream());
			System.out.println("\nResponse Message" + conn.getResponseMessage());
			
			throw new RuntimeException("ERRO " + conn.getResponseCode() );
		}
		
		String response = getStringFromInputStream(conn.getInputStream());
		
		System.out.println("\nsucesso: ");
		System.out.println(response);
		
		return response;
	}
	
	private static String getStringFromInputStream(InputStream inputStream) throws IOException, JAXBException {
		BufferedReader br = new BufferedReader(new InputStreamReader((inputStream)));

		String output = "";
		String aux = "";
		
		while ((aux = br.readLine()) != null) {
			output += aux;
			System.out.println(aux);
		}
		
		return output;
	}
}
