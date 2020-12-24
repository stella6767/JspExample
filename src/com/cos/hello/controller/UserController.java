package com.cos.hello.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
//javax로 시작하는 패키지는 톰캣이 들고 있는 라이브러리
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cos.hello.model.Users;

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
		// 이런 것들이 라우터, gubun이라는 프로토콜로 분기점
		if (gubun.equals("login")) {
			resp.sendRedirect("auth/login.jsp");
		} else if (gubun.equals("join")) {
			resp.sendRedirect("auth/join.jsp");

		} else if (gubun.equals("selectOne")) { // 유저정보 보기
			// 인증이 필요한 페이지
			String result;
			HttpSession session = req.getSession();
			if (session.getAttribute("sessionUser") != null) {
				Users user = (Users) session.getAttribute("sessionUser"); //getAttribute에 key 값 넣기
				//인증 끝
				result="인증되었습니다";
				System.out.println(user);
			}else {
				result = "인증되지 않았습니다.";
			}
			req.setAttribute("result", result);
			RequestDispatcher dis = req.getRequestDispatcher("user/selectOne.jsp");
			dis.forward(req, resp);
		
			//resp.sendRedirect("user/selectOne.jsp");

		} else if (gubun.equals("updateOne")) {
			resp.sendRedirect("user/updateOne.jsp");

		} else if (gubun.equals("joinProc")) {
			// 데이터 원형 username=ssar&paswword=1234&email=ssar@nate.com

			// 1번 form의 input 태그에 있는 3가지 값 username, password, email 받기

			// getParameter 함수는 get 방식의 데이터와 post 방식의 데이터를 다 받을 수 잇음
			// 단 post방식에서는 데이터 타입이 x-www-form-urlencoded 방식만 받을 수 있음.
			// key=value 방식
			String username = req.getParameter("username"); // request가 담고 있는 데이터 중 name을 키값으로 해서 파싱
			String password = req.getParameter("password");
			String email = req.getParameter("email");

			System.out.println("===================JoinProc Start================");
			System.out.println(username);
			System.out.println(password);
			System.out.println(email);
			System.out.println("===================JoinProc End================");

			// 2번 DB에 연결해서 3가지 값을 insert 하기
			// 생략

			// 3번 insert가 정상적으로 되었다면 index.jsp를 응답!!
			resp.sendRedirect("index.jsp");

		} else if (gubun.equals("loginProc")) {
			String username = req.getParameter("username");
			String password = req.getParameter("password");
			System.out.println("===================loginProc Start================");
			System.out.println(username);
			System.out.println(password);
			System.out.println("===================loginProc End================");

			// 2번 데이터베이스 값이 있는 select해서 확인
			// 생략
			Users user = Users.builder().id(1).username(username).build();

			// 3번
			HttpSession session = req.getSession();
			// session에는 사용자 패스워드 절대 넣지 않기
			session.setAttribute("sessionUser", user);
			// 모든 응답에는 JSessionId가 쿠키로 추가된다.

			// 4번 index.jsp 페이지로 이동
			resp.sendRedirect("index.jsp");

		}

	}

}