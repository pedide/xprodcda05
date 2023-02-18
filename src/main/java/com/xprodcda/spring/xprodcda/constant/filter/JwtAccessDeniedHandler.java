package com.xprodcda.spring.xprodcda.constant.filter;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.io.IOException;
import java.io.OutputStream;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xprodcda.spring.xprodcda.constant.SecurityConstant;
import com.xprodcda.spring.xprodcda.domain.HttpResponse;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		//Je créé mon objet response de mon dinstance HttResponse
				HttpResponse httpResponse = new HttpResponse(
						HttpStatus.UNAUTHORIZED.value(),
						HttpStatus.UNAUTHORIZED,
						HttpStatus.UNAUTHORIZED.getReasonPhrase().toUpperCase(),
						SecurityConstant.ACCESS_DENIED_MESSAGE
						);
				response.setContentType(APPLICATION_JSON_VALUE);
				response.setStatus(HttpStatus.UNAUTHORIZED.value());

				OutputStream  outputStream = response.getOutputStream();
				ObjectMapper mapper = new ObjectMapper();
				mapper.writeValue(outputStream, httpResponse);
				outputStream.flush();
		
	}

}
