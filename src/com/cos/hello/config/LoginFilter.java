package com.cos.hello.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.cos.hello.dto.LoginDto;
import com.cos.hello.model.Users;

public class LoginFilter implements Filter {
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		String gubun = req.getParameter("gubun");
		
		if(gubun.equals("loginProc")){ //null과 공백 둘 다 체크
			if(req.getParameter("username") == null ||
				req.getParameter("password") == null ||
				req.getParameter("username").equals("") ||
				req.getParameter("password").equals("") 
					) {
				chain.doFilter(request, response);
				return;
			}
			
			
			//XSS 공격 막기
			
			
			
			
			String username= req.getParameter("username");
			String password= req.getParameter("password");
			
			LoginDto dto = new LoginDto();
			dto.setUsername(username);
			dto.setUsername(password);

			
			request.setAttribute("dto", dto);
			
		}
		chain.doFilter(request, response); //이거 맞나?
		
		
		
	}
}
