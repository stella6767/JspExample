package com.cos.hello.config;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

//public class AttackFilter implements Filter{ //XSS 필터
//	
//	public FilterConfig filterConfig;
//
//	 public void init(FilterConfig filterConfig) throws ServletException { 
//	        this.filterConfig = filterConfig;    
//	 }    
//
//	 public void destroy() {
//	         this.filterConfig = null;     
//	 }    
//
//
//	
//	//2번째 순서(마지막 순서)
//	@Override
//	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//			throws IOException, ServletException {
//		//joinProc 일때
//		String username=request.getParameter("username");
//
//		System.out.println("attackFilter");
//		
//		//post 요청만 받아서 처리!!
//		HttpServletRequest req = (HttpServletRequest)request; //getmethod 함수를 사용하기 위해서 다운캐스팅
//		HttpServletResponse resp = (HttpServletResponse)response;
//		
//		String method = req.getMethod();
//		System.out.println("method: "+method);
//		
//		if(method.equals("POST")) {
////			username = username.replaceAll("<", "&lt;")
////				.replaceAll(">","&gt;" );
////			
////			System.out.println("바뀐 username: " + username);
//			//username에 <> 꺽쇠 들어오는것을 방어
//			//만약에 꺽쇠가 들어오면 전부 &lt; &gt;
//			//다시 필터타게 할 예정
//			
//			RequestWrapper requestWrapper = new RequestWrapper((HttpServletRequest)request);
//			
//			chain.doFilter(requestWrapper ,response);
//			
//		}
//		
//
//		
//		
////		BufferedReader br =request.getReader();
////		String input;
////		while((input= br.readLine())!=null) {
////			System.out.println(input);
////		}
//		
//		
//		chain.doFilter(req,response);
//	
//	}
//}	

public class AttackFilter implements Filter {
	
	class FilterRequest extends HttpServletRequestWrapper {

		public FilterRequest(HttpServletRequest request) {
			super(request);
		}

		public String changeWord(String text) {
			String result = text.replaceAll("<", "<").replaceAll(">", ">");
			return result;
		}

		public String getParameter(String parameter) {
			String value = super.getParameter(parameter);
			System.out.println("이게 어떻게 가능하지?");
			return changeWord(value);
		}
	}

	// 필터는 요청이 들어오기 전만 처리 가능하다.
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("공격방어 필터 실행");
		// post요청만 받아서 처리!
		// joinProc일 때
		String gubun = request.getParameter("gubun");
		HttpServletRequest req = (HttpServletRequest) request;
		String method = req.getMethod();
		if (method.equals("POST")) {
			// username에 < >들어오는 것을 방어
			// 만약 꺽쇠가 들어오면 전부 < >치환
			// 다시 필터 타게 할 예정
			
			chain.doFilter(new FilterRequest((HttpServletRequest) request), response);
		} else {
			chain.doFilter(request, response);
		}

	}

}
