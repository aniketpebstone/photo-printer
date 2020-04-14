package org.client;

import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class Oauth2ClientService {

	@Value("${photoPrinter.clientId}")
	String clientId;
	
	@Value("${photoPrinter.clientSecret}")
	String clientSecret;
	
	@Value("${photoPrinter.userAuthorizationUri}")
	String userAuthorizationUri;
	
	@Value("${photoPrinter.userInfoUri}")
	String userInfoUri;
	
	@Value("${photoPrinter.accessTokenUri}")
	String accessTokenUri;
	
	
	@Value("${photoPrinter.grantType}")
	String grantType;
	
	@Value("${photoPrinter.redirectUri}")
	String redirectUri;
	
	@Value("${photoPrinter.photoResourceUri}")
	String photoResourceUri;
	
	@Value("${photoPrinter.photoWithMetaResourceUri}")
	String photoWithMetaResourceUri;
	
	
	@Autowired
	RestTemplate restTemplate;
	
	public String getLocation() {
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(userAuthorizationUri)
		        .queryParam(OAuthConstants.RESPONSE_TYPE, OAuthConstants.CODE)
		        .queryParam(OAuthConstants.REDIRECT_URI, redirectUri)
		        .queryParam(OAuthConstants.CLIENT_ID, clientId);
		return builder.toUriString();
	}
	
	public AccessToken getAccessToken(String code) {
		
		LinkedMultiValueMap<String, String> payload = new LinkedMultiValueMap<String, String>();
		payload.add(OAuthConstants.GRANT_TYPE,OAuthConstants.GRANT_TYPE_AUTHORIZATION_CODE);
		payload.add(OAuthConstants.CODE, code);
		payload.add(OAuthConstants.REDIRECT_URI, redirectUri);
	
		String credentials=clientId+":"+clientSecret;
		String encodedCredentials = new String(Base64.encodeBase64(credentials.getBytes()));

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.add(OAuthConstants.AUTHORIZATION, OAuthConstants.BASIC+" " + encodedCredentials);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(accessTokenUri);
		HttpEntity<?> entity= new HttpEntity<>(payload,headers);
		URI uri = builder.build().encode().toUri();
		HttpEntity<AccessToken> response = restTemplate.exchange(uri, HttpMethod.POST, entity, AccessToken.class);
		
		return response.getBody();
		
	}

	public UserInfo fetchUserDetails(String accessToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.add(OAuthConstants.AUTHORIZATION, OAuthConstants.BEARER+" " + accessToken);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(userInfoUri);
		HttpEntity<?> entity= new HttpEntity<>(headers);
		URI uri = builder.build().encode().toUri();
		HttpEntity<UserInfo> response = restTemplate.exchange(uri, HttpMethod.POST, entity, UserInfo.class);
		return response.getBody();
	}
	
	public String getPhotos(String accessToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.add(OAuthConstants.AUTHORIZATION, OAuthConstants.BEARER+" " + accessToken);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(photoResourceUri);
		HttpEntity<?> entity= new HttpEntity<>(headers);
		URI uri = builder.build().encode().toUri();
		HttpEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
		return response.getBody();
	}

	public String getPhotosWithMeta(String accessToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.add(OAuthConstants.AUTHORIZATION, OAuthConstants.BEARER+" " + accessToken);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(photoWithMetaResourceUri);
		HttpEntity<?> entity= new HttpEntity<>(headers);
		URI uri = builder.build().encode().toUri();
		HttpEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
		return response.getBody();
	}
	

}
