
package com.jbedu.oracle.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jbedu.oracle.dao.BoardDao;
import com.jbedu.oracle.dao.MemberDao;
import com.jbedu.oracle.dto.BoardDto;
import com.jbedu.oracle.dto.MemberDto;

@Controller
public class BoardController {
	
	@Autowired //의존성 자동 주입
	private SqlSession sqlSession;
	
//	@RequestMapping(value = "/write_form")
//	public String write_form(Model model, HttpSession session) {
//		
//		String sessionId = (String) session.getAttribute("sessionId");
//		//로그인 상태라면 로그인한 아이디값이 반환, 로그인하지 않은 상태라면 null
//		System.out.println(sessionId);
//		
//		if(sessionId == null) { //로그인하지 않은 상태
//			model.addAttribute("msg", "로그인한 회원만 글쓰기가 가능합니다!");
//			model.addAttribute("url", "login");
//			
//			return "alert";
//		}
//		
//		MemberDao dao = sqlSession.getMapper(MemberDao.class);
//		MemberDto memberDto = dao.memberInfoDao(sessionId);
//		
//		model.addAttribute("loginId", sessionId);
//		model.addAttribute("memberName", memberDto.getMname()); //로그인한 유저의 이름을 전송
//		
//		return "writeForm";
//	}
	
	@RequestMapping(value = "/writeform")
	public String writeform(Model model, HttpSession session) {
		
		String sessionId = (String) session.getAttribute("sessionId");
		//로그인 상태라면 로그인한 아이디값이 반환, 로그인하지 않은 상태라면 null
		System.out.println(sessionId);
		
		if(sessionId == null) { //로그인하지 않은 상태
			model.addAttribute("msg", "로그인한 회원만 글쓰기가 가능합니다!");
			model.addAttribute("url", "login");
			
			return "alert";
		}
		
		MemberDao dao = sqlSession.getMapper(MemberDao.class);
		MemberDto memberDto = dao.memberInfoDao(sessionId);
		
		model.addAttribute("loginId", sessionId);
		model.addAttribute("memberName", memberDto.getMname()); //로그인한 유저의 이름을 전송
		
		return "writeForm";
	}
	
	@RequestMapping(value = "/write") //게시판 글쓰기
	public String write(HttpServletRequest request, Model model) {
		
		String bid = request.getParameter("bid");
		String bname = request.getParameter("bname");
		String btitle = request.getParameter("btitle");
		String bcontent = request.getParameter("bcontent");
		
		BoardDao dao = sqlSession.getMapper(BoardDao.class);
		
		dao.writeDao(bid, bname, btitle, bcontent);
		
		return "redirect:list";
	}
	
	@RequestMapping(value = "list")
	public String list(HttpServletRequest request, Model model) {
	
		BoardDao dao = sqlSession.getMapper(BoardDao.class);
		ArrayList<BoardDto> bDtos = dao.listDao();
		
		model.addAttribute("boardList", bDtos);
		
		return "boardList";
	}
	
	@RequestMapping(value = "/content_view")
	public String content_view(HttpServletRequest request, Model model) {
		
		String bnum = request.getParameter("bnum"); // 유저가 클릭한 게시글의 번호
		
		BoardDao dao = sqlSession.getMapper(BoardDao.class);
		
		dao.uphitDao(bnum); // 조회수 1 증가
		
		BoardDto bDto = dao.contentDao(bnum); // 글 1개 반환
		
//		System.out.println(bDto);  // 글이 삭제된 경우 테스트 위에 bnum을 "100" 이렇게 세팅해서. 그럼 null 이 찍힘
		if(bDto == null) {
			model.addAttribute("msg", "존재하지 않는 글입니다.");
			model.addAttribute("url", "list");
			
			return "alert";
		}
		
		model.addAttribute("boardDto", bDto);
		
		return "contentView";
	}
	
	@RequestMapping(value = "/modifyOk")
	public String modifyOk(HttpServletRequest request, Model model) {
		
		String bnum = request.getParameter("bnum");
		String btitle = request.getParameter("btitle");
		String bcontent = request.getParameter("bcontent");
		
		BoardDao dao = sqlSession.getMapper(BoardDao.class);
		
		dao.modifyDao(bnum, btitle, bcontent);
		
		return "redirect:list";
	}
	
	@RequestMapping(value = "/content_modify")
	public String content_modify(HttpServletRequest request, Model model) {
		
		String bnum = request.getParameter("bnum"); // 수정하고자 하는 글 번호
		
		BoardDao dao = sqlSession.getMapper(BoardDao.class);
		
		BoardDto bDto = dao.contentDao(bnum); // 글 1개 반환
		
		model.addAttribute("boardDto", bDto);
		
		return "contentModify";
		
	}
	
	@RequestMapping(value = "/content_delete")
	public String content_delete(HttpServletRequest request, Model model) {
		
		String bnum = request.getParameter("bnum"); // 수정하고자 하는 글 번호
		
		BoardDao dao = sqlSession.getMapper(BoardDao.class);
		
		dao.deleteDao(bnum);
		
		return "redirect:list";
		
	}
}
