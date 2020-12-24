package com.cos.hello.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
//javax로 시작하는 패키지는 톰캣이 들고 있는 라이브러리
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cos.hello.config.DBConn;
import com.cos.hello.dao.UsersDao;
import com.cos.hello.model.Users;
import com.cos.hello.service.UsersService;


//디스패쳐의 역할 = 분기 = 필요한 View를 응답해주는 것
public class UserController extends HttpServlet {

	// req와 res는 톰캣이 만들어줍니다.(클라이언트의 요청이 있을때 마다)
	// req는 BufferedReader 할 수 있는 ByteStream
	// res는 BufferedWriter 할 수 있는 ByteStream

	// http://localhost:8000/hello/user?gubun=login
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		System.out.println("doget 실행");

		doProcess(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req, resp);
	}

	protected void doProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		System.out.println("doprocess 실행");

		String gubun = req.getParameter("gubun"); // /hello/front
		System.out.println(gubun);
		route(gubun, req, resp); // 지금은 req,resp를 지역변수로 다루지만 나중에는 heap영역으로

	}

	// throw ioexception은 이 함수 전체를 try-catch문으로 묶는다는 의미
	private void route(String gubun, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		UsersService usersService = new UsersService();
		
		
		// 이런 것들이 라우터, gubun이라는 프로토콜로 분기점
		if (gubun.equals("login")) {
			resp.sendRedirect("auth/login.jsp");
		} else if (gubun.equals("join")) {
			resp.sendRedirect("auth/join.jsp");
		} else if (gubun.equals("selectOne")) { // 유저정보 보기
			// 인증이 필요한 페이지
			usersService.유저정보보기(req,resp);
		} else if (gubun.equals("updateOne")) {
			usersService.유저정보수정페이지(req, resp);

		} else if (gubun.equals("joinProc")) {
			usersService.회원가입(req, resp);

		} else if (gubun.equals("loginProc")) {						
			usersService.로그인(req, resp);
		
		}else if (gubun.equals("updateProc")) {						
			usersService.회원수정(req, resp);
		
		}else if (gubun.equals("deleteProc")) {						
			usersService.회원삭제(req, resp);
		
		}
		
		

	}

}