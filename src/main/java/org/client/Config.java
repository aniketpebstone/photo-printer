package org.client;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
public class Config {
	
	@Bean
	public RestTemplate getRestTemplate(RestTemplateBuilder restTemplateBuilder)
	{
		RestTemplate restTemplate = new RestTemplate();
		 return restTemplate;
	}
	
	@Bean
	public CommonsRequestLoggingFilter requestLoggingFilter()
	{
		CommonsRequestLoggingFilter crlf = new CommonsRequestLoggingFilter();
		crlf.setIncludeClientInfo(true);
		crlf.setIncludeQueryString(true);
		crlf.setIncludePayload(true);
		crlf.setIncludeHeaders(true);
		return crlf;
	}
	
}
