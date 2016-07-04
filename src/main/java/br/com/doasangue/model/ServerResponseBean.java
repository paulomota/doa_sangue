package br.com.doasangue.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import br.com.doasangue.bean.ErroBean;
import br.com.doasangue.util.JSONUtil;

public class ServerResponseBean {

	private boolean hasErros;
	
	private Integer code;
	
	private Object data;
	
	private List<ErroBean> erros;

	public ServerResponseBean(){
		this.erros = new ArrayList<ErroBean>();
	}
	
	public ServerResponseBean(boolean hasErros, Integer code) {
		this.hasErros = hasErros;
		this.code = code;
		this.erros = new ArrayList<ErroBean>();
	}

	public void addError(ErroBean erro) {
		erros.add(erro);
	}
	
	public Response getResponse() throws IOException {
		JSONObject responseJson = new JSONObject();

		try {
			responseJson.put("hasError", this.hasErros);
			
			if (this.hasErros) {
				responseJson.put("erros",  JSONUtil.constructJSONArray(this.erros));
				
			} else {
				
				if (this.data == null) {
					responseJson.put("data", new JSONObject());
					
				} else if (this.data instanceof Collection) {
					responseJson.put("data", JSONUtil.constructJSONArray(this.data));
					
				} else if (this.data instanceof String) {
					responseJson.put("data", "{" + this.data + "}");
					
				} else if (this.data instanceof Enum) {
					responseJson.put("data", this.data);
					
				} else if (this.data instanceof Enum[]) {
					responseJson.put("data", JSONUtil.constructJSONArray(this.data));
					
				} else {
					
					responseJson.put("data", JSONUtil.constructJSONObject(this.data));
				}
			}
			
		} catch (JSONException je) {
			je.printStackTrace();
			
		} catch (JsonGenerationException jge) {
			jge.printStackTrace();
			
		} catch (JsonMappingException jme) {
			jme.printStackTrace();
		}
		
		return Response
                .status(this.getCode())
                .entity(responseJson.toString())
                .header("Accept-Charset", "UTF-8").build();
	}
	
	public boolean isHasErros() {
		return hasErros;
	}

	public void setHasErros(boolean hasErros) {
		this.hasErros = hasErros;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public List<ErroBean> getErros() {
		return erros;
	}

	public void setErros(List<ErroBean> erros) {
		this.erros = erros;
	}

}
