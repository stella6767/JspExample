package com.cos.hello.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class CharFilter implements Filter { //web.xml에서 읽을 수 있게

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("charFilter");
		
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		//만약 웹의 다른 처리에서 utf-8로 응답하고 싶지 않다면, 오버라이딩해서 해결! 기본설정은 utf-8
		
		chain.doFilter(request, response);//그 다음 필터를 찾으러감 없으면 index파일을 찾아들어감
		
	}
	
	
}
