package com.cos.hello.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class Script {
	
	public static void back(HttpServletResponse resp, String msg) throws IOException { 		
		
		PrintWriter out = resp.getWriter();
		out.println("<script>");
		out.println("alert('"+msg+"')");
		out.println("history.back();");
		out.println("</script>");
		out.flush();
		
	}
	
	
	public static void href(HttpServletResponse resp, String url, String msg) throws IOException { 		
		//resp.setHeader("content-Type", "text/html; charset=utf-8");//응답시에 mime 타입
		//resp.setCharacterEncoding("utf-8"); //필터에 걸자. req. 내가 요청받는 모든 데이터는 utf-8로
		//resp.setContentType("text/html; charset=utf-8"); //위의 setHeader랑 같음
		PrintWriter out = resp.getWriter();
		out.println("<script>");
		out.println("alert('"+msg+"')");
		out.println("location.href = '"+url+"';");
		out.println("</script>");
		out.flush();
		
	}
	
	

}
