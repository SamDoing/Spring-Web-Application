package com.sam.webapp.interceptor;

import java.time.Duration;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class Interceptador extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Acesso acesso = new Acesso();
		acesso.path = request.getRequestURI();
		acesso.data = LocalDateTime.now();

		request.setAttribute("acesso", acesso);

		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		Acesso acesso = (Acesso) request.getAttribute("acesso");
		acesso.duracao = Duration.between(acesso.data, LocalDateTime.now());
	}

	class Acesso {
		private String path;
		private LocalDateTime data;
		private Duration duracao;
	}
}
