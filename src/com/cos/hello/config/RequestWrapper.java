package com.cos.hello.config;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class RequestWrapper extends HttpServletRequestWrapper {
	
	HttpServletRequest request;
	String name;
	
	public RequestWrapper(HttpServletRequest request) {
		super(request);
		// TODO Auto-generated constructor stub
		//this.request = request;
		System.out.println("wrapper 들어옴?");
		
		if(request != null) {
			this.request = request;
			name = request.getParameter("username");
			
			System.out.println(name);
			
			name = name.replaceAll("<", "&lt;")
					.replaceAll(">","&gt;" );
			
			System.out.println("바뀐 " + name);
		}
	}

	
	
	@Override
	public String getParameter(String name) {
		// TODO Auto-generated method stub
		System.out.println(name);
		return name;
	}
//	


}
