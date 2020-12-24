package com.cos.hello.service;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cos.hello.dao.UsersDao;
import com.cos.hello.model.Users;

public class UsersService {

	public void 회원가입(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		String username = req.getParameter("username"); // request가 담고 있는 데이터 중 name을 키값으로 해서 파싱
		String password = req.getParameter("password");
		String email = req.getParameter("email");

		Users user = Users.builder().username(username).password(password).email(email).build();

		//UsersDao usersDao = new UsersDao(); //싱글톤, getinstance로 바꾸자
		UsersDao usersDao = UsersDao.getInstance();

		int result = usersDao.insert(user);

		if (result == 1) {
			// 3번 insert가 정상적으로 되었다면 index.jsp를 응답!!
			resp.sendRedirect("auth/login.jsp");
		} else {
			resp.sendRedirect("auth/join.jsp");
		}

		System.out.println("===================JoinProc Start================");
		System.out.println(username);
		System.out.println(password);
		System.out.println(email);
		System.out.println("===================JoinProc End================");

	}

	public void 로그인(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		// SELECT id,username, email from users where username =? and password=?
		// DAO의 함수명: login() return을 Users 오브젝트를 리턴
		// 정상: 세션에 Users 오브젝트 담고 index.jsp, 비정상:login.jsp
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		System.out.println("===================loginProc Start================");
		System.out.println(username);
		System.out.println(password);
		System.out.println("===================loginProc End================");

		// 1번 전달되는 값 받기
		Users user = Users.builder().username(username).password(password).build();

		// 2번 데이터베이스 값이 있는 select해서 확인
		//UsersDao usersDao = new UsersDao(); //싱글톤, getinstance로 바꾸자
		UsersDao usersDao = UsersDao.getInstance();
		
		Users userEntity = usersDao.login(user); // userEntity는 DB에서 들고 온 유저정보 DB는 entity 붙이자

		if (userEntity != null) {
			HttpSession session = req.getSession();
			session.setAttribute("sessionUser", userEntity);
			resp.sendRedirect("index.jsp");

		} else {
			resp.sendRedirect("auth/login.jsp");
		}

	}

	public void 유저정보보기(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

		HttpSession session = req.getSession();
		
		Users user = (Users)session.getAttribute("sessionUser");
		//UsersDao usersDao = new UsersDao();
		UsersDao usersDao = UsersDao.getInstance();
		
		System.out.println(session.getAttribute("sessionUser"));
		
		if(user!= null) {
			Users userEntity = usersDao.selectById(user.getId());
			req.setAttribute("user", userEntity); //키값
			RequestDispatcher dis = 
					req.getRequestDispatcher("user/selectOne.jsp");
			dis.forward(req, resp);
			
			
		}else {
			resp.sendRedirect("auth/login.jsp");
		}
		
	}
	
	
	public void 유저정보수정페이지(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

		HttpSession session = req.getSession();
		
		Users user = (Users)session.getAttribute("sessionUser");
		 //UsersDao usersDao = new UsersDao();
		UsersDao usersDao = UsersDao.getInstance();
		
		if(user!= null) {
			Users userEntity = usersDao.selectById(user.getId());
			req.setAttribute("user", userEntity); //키값
			RequestDispatcher dis = 
					req.getRequestDispatcher("user/updateOne.jsp");
			dis.forward(req, resp);
			
			
		}else {
			resp.sendRedirect("auth/login.jsp");
		}
		
	}
	
}
