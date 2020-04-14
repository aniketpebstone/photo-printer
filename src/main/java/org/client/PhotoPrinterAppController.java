package org.client;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class PhotoPrinterAppController {
	
	@Autowired
	Oauth2ClientService service;
	
	@GetMapping("/")
	public String welcome() {
		return "welcome";
	}
	
	@GetMapping("/authorize")
	public ResponseEntity<?> authorizeOauth2Request(){
		String location=service.getLocation();
		log.info("Redirecting to {}"+location);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(URI.create(location));
		return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
	}
	
	@GetMapping("/redirect")
	public ResponseEntity<?> generateToken(
			 @RequestParam(value = "code", required = true) String code,HttpServletRequest request) throws ServletException, IOException{
		log.info("Authorization code from server:{}",code);
		AccessToken accessToken=service.getAccessToken(code);
		request.getSession().setAttribute("access_token", accessToken);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(URI.create("/user"));
		return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
	}
	
	@RequestMapping("/user")
	public String getUserDetails(Map<String, Object> model,HttpServletRequest request) {
		AccessToken accessToken=(AccessToken)request.getSession().getAttribute("access_token");
		UserInfo userInfo=service.fetchUserDetails(accessToken.getAccessToken());
		request.getSession().setAttribute("user_info",userInfo);
		log.info("Access Token:"+accessToken.getAccessToken());
		model.put("userInfo", userInfo);
		return "main";
	}
	
	@RequestMapping("/photos")
	public ResponseEntity<?> getPhotos(HttpServletRequest request) {
		AccessToken accessToken=(AccessToken)request.getSession().getAttribute("access_token");
		String photos=service.getPhotos(accessToken.accessToken);
		return ResponseEntity.ok(photos);
	}
	
	@RequestMapping("/photos-with-meta")
	public ResponseEntity<?> getPhotosWithMeta(HttpServletRequest request) {
		AccessToken accessToken=(AccessToken)request.getSession().getAttribute("access_token");
		String photos=service.getPhotosWithMeta(accessToken.accessToken);
		return ResponseEntity.ok(photos);
	}
	
	
}
