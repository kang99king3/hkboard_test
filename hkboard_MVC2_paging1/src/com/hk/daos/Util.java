package com.hk.daos;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class Util {

	// static : 클래스명.메서드,  클래스명.맴버필드
	// non-static : 객체생성후 객체명.메섣, 객체명.맴버필드
	// 메서드의 형태: static/ non-static,   return/void, 아규먼트O/아규먼트X 
	
	public static void jsForWard(String msg,String url
			,HttpServletResponse response) throws IOException{
		String s="<script type='text/javascript'>"
				 +"alert('"+msg+"');"
				 +"location.href='"+url+"';"
			     +"</script>";
		
		PrintWriter pw=response.getWriter();
		pw.print(s);
	}
}




