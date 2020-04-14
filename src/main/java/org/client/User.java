package org.client;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class User implements Serializable{

	private static final long serialVersionUID = 1L;

	@JsonProperty(value = "username", required = true)
	String userName;
	
	@JsonProperty(value = "authorities", required = true)
	List<Map<String,String>> authorities;
	
}
