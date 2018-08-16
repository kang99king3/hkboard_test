package com.hk.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hk.daos.HkDao;
import com.hk.daos.Paging;
import com.hk.daos.Util;
import com.hk.dtos.HkDto;

@WebServlet("/HkController.do")
public class HkController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
//		request.getSession().getAttribute("dto");//session객체
//		request.getServletContext().getAttribute("dto");//application객체
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		
		//1. 해당 페이지에서 요청된 내용을 알기 위한 command파라미터 받기
		String command=request.getParameter("command");
		
		//2. 요청에 대한 처리를 위한 Dao객체 생성
		HkDao dao=new HkDao();
		
		//3.요청에 대한 분기 작성: if~else~작성
		if(command.equals("boardlist")){
			
			String pNum=request.getParameter("pnum");
			int pageRange=10;//한페이지에 보여줄 글 목록 수
			int pageNumber=5;//한번에 보여줄 페이지 수:  1 | 2 | 3 | 4 | 5
			int pcount=dao.boardCount(pageRange);// 총 페이지 수
			
			if(pNum==null) {
				pNum=(String)request.getSession().getAttribute("pnum");
			}else {
				request.getSession().setAttribute("pnum", pNum);
			}
			
			
			//      아규먼트내용(총페이지수, 현재페이지번호, 한번에 보여줄 페이지 수)
			Map<String, Integer> map=Paging.pagingValue(pcount, pNum, pageNumber);
			request.setAttribute("prePageNum", map.get("prePageNum"));
			request.setAttribute("nextPageNum", map.get("nextPageNum"));
			request.setAttribute("startPage", map.get("startPage"));
			request.setAttribute("endPage", map.get("endPage"));
			
			List<HkDto> list=dao.getAllBoard(pageRange,pNum);//글목록을 담고있는 list객체 구함
			request.setAttribute("list", list);//request scope객체에 list객체를 담는다.

			dispatch("boardlist.jsp", request, response);
			
		}else if(command.equals("boarddetail")){
			
			int seq=Integer.parseInt(request.getParameter("seq"));
			HkDto dto = dao.getBoard(seq);
			request.setAttribute("dto", dto);//request scope에 dto객체를 담는다
//			pageContext.forward("boarddetail.jsp");
			dispatch("boarddetail.jsp", request, response);
		}else if(command.equals("boardwrite")){
	 		response.sendRedirect("boardwrite.jsp");
//			pageContext.forward("http://daum.net");
//	 		response.sendRedirect("http://daum.net");
		}else if(command.equals("boardinsert")){
			//요청파라미터 받기
			String id=request.getParameter("id");
			String name=request.getParameter("name");
			String title=request.getParameter("title");
			String content=request.getParameter("content");
			
			//요청내용을 처리할 dao 메서드 실행
			boolean isS=dao.insertBoard(new HkDto(0,id,name,title,content,null));
			
			if(isS){
				Util.jsForWard("새로운 글을 등록합니다.!!", "HkController.do?command=boardlist",response);
			}else{
				Util.jsForWard("글 등록에 실패하였습니다.!!ㅜㅜ",
						"HkController.do?command=boardwrite",response);
			}
		
		}else if(command.equals("updateform")){
			//파리미터 받고
			int seq=Integer.parseInt(request.getParameter("seq"));
			//Dao 실행하고
			HkDto dto=dao.getBoard(seq);
			//결과 scope에 담고
			request.setAttribute("dto", dto);
			//해당 페이지로 이동
//			pageContext.forward("boardupdate.jsp");
			dispatch("boardupdate.jsp", request, response);
		}else if(command.equals("boardupdate")){
			//title, content, seq 전송되고, 이 값들을 받는다.
			String title=request.getParameter("title");
			String content=request.getParameter("content");
			int seq=Integer.parseInt(request.getParameter("seq"));
			
			boolean isS=dao.updateBoard(new HkDto(seq,title,content));
			
			//java에서 페이지 이동할때 사용되는 메서드 2개 (sendRedirect(), forward())
			if(isS){
				response.sendRedirect("HkController.do?command=boarddetail&seq="+seq);
			}else{
				response.sendRedirect("HkController.do?command=updateform&seq="+seq);
			}
		}else if(command.equals("delboard")){
			int seq=Integer.parseInt(request.getParameter("seq"));

			boolean isS=dao.deleteBoard(seq);
			if(isS){
				Util.jsForWard("글을 삭제합니다",
						  "HkController.do?command=boardlist",response);
			}else{
				response.sendRedirect("HkController.do?command=boarddetail&seq="+seq);
			}
		}
	}
	
	//jsp에서 사용하던 forward(url) 구현
	public void dispatch(String url,HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatch=request.getRequestDispatcher(url);
		dispatch.forward(request, response);
	}
}












