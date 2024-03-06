package com.GG.springboot.app.auth.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.SessionFlashMapManager;

@Component
public class LoginSuccesHandler extends SimpleUrlAuthenticationSuccessHandler{

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// TODO Auto-generated method stub
		SessionFlashMapManager flashMapManager= new SessionFlashMapManager();
		
		FlashMap flashMap= new FlashMap();
		
		flashMap.put("success", "Hello " + authentication.getName()+ "  you have logged in successfully");
		
		flashMapManager.saveOutputFlashMap(flashMap, request, response);
		
		if(authentication !=null) {
			logger.info("The User : " + authentication.getName() + "you have logged in successfully");
		}
		
		
		super.onAuthenticationSuccess(request, response, authentication);
	}
	
	

}
