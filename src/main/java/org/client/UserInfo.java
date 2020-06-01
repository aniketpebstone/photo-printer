package org.client;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;


@Data
public class UserInfo implements Serializable{
	private static final long serialVersionUID = 1L;

	boolean active;
	
	long exp;
	
	@JsonProperty(value = "user_name", required = true)
	String userName;
	
	@JsonProperty(value = "authorities", required = true)
	List<String> authorities;
	
	@JsonProperty(value = "client_id", required = true)
	String clientId;
	
	@JsonProperty(value = "scope", required = true)
	List<String> scope;
	
}
