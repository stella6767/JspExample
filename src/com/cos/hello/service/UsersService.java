package com.cos.hello.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cos.hello.dao.UsersDao;
import com.cos.hello.dto.JoinDto;
import com.cos.hello.dto.LoginDto;
import com.cos.hello.model.Users;
import com.cos.hello.util.Script;

public class UsersService {

	public void 회원가입(HttpServletRequest req, HttpServletResponse resp) throws IOException {

//		String username = req.getParameter("username"); // request가 담고 있는 데이터 중 name을 키값으로 해서 파싱
//		String password = req.getParameter("password");
//		String email = req.getParameter("email");
//
//		Users user = Users.builder().username(username).password(password).email(email).build();

		JoinDto joinDto = (JoinDto)req.getAttribute("dto");
		
		// UsersDao usersDao = new UsersDao(); //싱글톤, getinstance로 바꾸자
		UsersDao usersDao = UsersDao.getInstance();

		int result = usersDao.insert(joinDto);

		if (result == 1) {
			// 3번 insert가 정상적으로 되었다면 index.jsp를 응답!!
			resp.sendRedirect("auth/login.jsp");
		} else {
			resp.sendRedirect("auth/join.jsp");
		}

		System.out.println("===================JoinProc Start================");
//		System.out.println(username);
//		System.out.println(password);
//		System.out.println(email);
		System.out.println("===================JoinProc End================");

	}

	public void 로그인(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		LoginDto loginDto = (LoginDto)req.getAttribute("dto");
		

		// 2번 데이터베이스 값이 있는 select해서 확인
		// UsersDao usersDao = new UsersDao(); //싱글톤, getinstance로 바꾸자
		UsersDao usersDao = UsersDao.getInstance();

		Users userEntity = usersDao.login(loginDto); // userEntity는 DB에서 들고 온 유저정보 DB는 entity 붙이자

		if (userEntity != null) {
			HttpSession session = req.getSession();
			session.setAttribute("sessionUser", userEntity);
			//한글처리를 위해 resp 객체를 건드린다.
			//MIME 타입
			//http header에 Content-Type
			Script.href(resp, "index.jsp", "로그인 성공");
			//resp.sendRedirect("index.jsp");

		} else {
			Script.back(resp, "로그인 실패");
			//resp.sendRedirect("auth/login.jsp");
		}

	}

	public void 유저정보보기(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

		HttpSession session = req.getSession();

		Users user = (Users) session.getAttribute("sessionUser");
		// UsersDao usersDao = new UsersDao();
		UsersDao usersDao = UsersDao.getInstance();

		System.out.println(session.getAttribute("sessionUser"));

		if (user != null) {
			Users userEntity = usersDao.selectById(user.getId());
			req.setAttribute("user", userEntity); // 키값
			RequestDispatcher dis = req.getRequestDispatcher("user/selectOne.jsp");
			dis.forward(req, resp);

		} else {
			resp.sendRedirect("auth/login.jsp");
		}

	}

	public void 유저정보수정페이지(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

		HttpSession session = req.getSession();

		Users user = (Users) session.getAttribute("sessionUser");
		// UsersDao usersDao = new UsersDao();
		UsersDao usersDao = UsersDao.getInstance();

		if (user != null) {
			Users userEntity = usersDao.selectById(user.getId());
			req.setAttribute("user", userEntity); // 키값
			RequestDispatcher dis = req.getRequestDispatcher("user/updateOne.jsp");
			dis.forward(req, resp);

		} else {
			resp.sendRedirect("auth/login.jsp");
		}

	}

	public void 회원수정(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		System.out.println("유저정보수정");

		String username = req.getParameter("username");

		int id = Integer.parseInt(req.getParameter("id"));
		String password = req.getParameter("password");
		String email = req.getParameter("email");

		Users user = Users.builder().id(id).password(password).email(email).build();

		UsersDao usersDao = UsersDao.getInstance();

		int result = usersDao.update(user);

		if (result == 1) {
			// 성공하면
			resp.sendRedirect("index.jsp");
		} else {
			// 이전 페이지로 이동(아직 구현안함)
			resp.sendRedirect("user?gubun=updateOne"); // 모든 요청과 응답경로는 컨트롤러를 통해서
		}
	}

	public void 회원삭제(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		int id = Integer.parseInt(req.getParameter("id"));

		UsersDao usersDao = UsersDao.getInstance();

		int result = usersDao.deleteById(id);

		if (result == 1) {
			// 성공하면
			HttpSession session = req.getSession(); 
			session.invalidate(); //내 제이세션 id 영역만 통째로 날라감
			resp.sendRedirect("index.jsp");
		} else {
			// 이전 페이지로 이동(아직 구현안함)
			resp.sendRedirect("user?gubun=selectOne");
		}
	}

}
