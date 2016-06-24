package br.com.doa_sangue.util;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class JSONUtil {
	
	public static JSONObject constructJSONObject(Object object) throws JSONException, IOException{
		if(object instanceof JSONObject){
			return (JSONObject) object;
		}
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonResponse = mapper.writeValueAsString(object);
		
		return new JSONObject(jsonResponse);
	}
	
	public static JSONArray constructJSONArray(Object object) throws IOException, JSONException {
		ObjectMapper mapper = new ObjectMapper();
		String jsonResponse = mapper.writeValueAsString(object);
		
		return new JSONArray(jsonResponse);
	}

}
