package org.client;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;


@Data
public class AccessToken implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty(value = "access_token", required = true)
	String accessToken;
	
	@JsonProperty(value = "token_type", required = true)
	String tokenType;
	
	@JsonProperty(value = "expires_in", required = true)
	long expiresIn;
	
	@JsonProperty(value = "scope", required = true)
	String scope;
	
}
