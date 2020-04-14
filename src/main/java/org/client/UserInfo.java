package org.client;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;


@Data
public class UserInfo implements Serializable{
	private static final long serialVersionUID = 1L;

	@JsonProperty(value = "user", required = true)
	User user;
	
}
