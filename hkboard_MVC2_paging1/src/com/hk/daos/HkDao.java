package com.hk.daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.hk.dtos.HkDto;

public class HkDao {
	// Dao 클래스 : data에 접근하는 객체 (jdbc의 집합)
	
	public HkDao() {
		//jdbc 1단계: 드라이버 로딩
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("1단계:드라이버로딩 성공!!");
		} catch (ClassNotFoundException e) {
			System.out.println("1단계:드라이버로딩 실패!!");
			e.printStackTrace();
		}
	}
	
	//글목록조회: 여러글에 대한 정보 필요 -> select문 -> dto(row하나담는그릇)
   // rowRange: 한번에 보여줄 행의 개수(범위), pagteNum:현재 요청된 페이지 번호
	public List<HkDto>  getAllBoard(int rowRange, String pageNum) {
		List<HkDto> list=new ArrayList<HkDto>();
		Connection conn=null;// DB연결
		PreparedStatement st=null;// 쿼리 준비
		ResultSet rs=null;// 결과 받기
		
		//DB 연결 정보
		String url="jdbc:oracle:thin:@192.168.3.10:1521:xe";
		String user="hk";
		String password="hk";
		
		//쿼리 작성
		String sql=" SELECT SEQ, ID, NAME, TITLE, CONTENT, REGDATE "
				  +" FROM "
				  +"	(SELECT ROW_NUMBER() OVER(ORDER BY REGDATE DESC) AS RN, "
				  +"	SEQ, ID, NAME, TITLE, CONTENT, REGDATE "
				  +"	FROM HKBOARD) "
				  +" WHERE CEIL(RN/?)=? ";
					
		try {
			conn=DriverManager.getConnection(url, user, password);
			System.out.println("2단계:DB연결 성공!!");
			
			st=conn.prepareStatement(sql);//계정에 연결된 객체에 대한 statement객체생성
			st.setInt(1, rowRange);
			st.setString(2, pageNum);
			System.out.println("3단계:쿼리준비 성공!!");
		
			rs=st.executeQuery();
			System.out.println("4단계:쿼리실행 성공!!");
			
			//rs객체에 결과를 담았고 담은걸 Dto 객체에 담아야 자바에서 쓸수 있다.
			while(rs.next()) {
				HkDto dto=new HkDto();
				dto.setSeq(rs.getInt(1));//DB는 인덱스가 1부터 시작함
				dto.setId(rs.getString(2));
				dto.setName(rs.getString(3));
				dto.setTitle(rs.getString(4));
				dto.setContent(rs.getString(5));
				dto.setRegdate(rs.getDate(6));
				list.add(dto);
				System.out.println(dto);
			}
			System.out.println("5단계:쿼리결과받기성공!!!");
		} catch (SQLException e) {
			System.out.println("JDBC실패!!!("+getClass()+":getAllBoard())");
			e.printStackTrace();
		}finally {
			//닫을 때는 오류에 상관없이 무조건 실행되어야 하기때문에 finally에 작성
			//IO : input/output(입력/출력) 입력-데이터가 컴퓨터로 들어오는것 (키보드입력)
			//                           출력-데이터가 컴퓨터 밖으로 나가는것(syso로 콘솔 출력)
			// 입력  ---> 자바 ---> 출력
			try {
				if(rs!=null) {
					rs.close();
				}
				if(st!=null) {
					st.close();				
				}
				if(conn!=null) {
					conn.close();				
				}
				System.out.println("6단계:DB닫기 성공!!!");
			} catch (SQLException e) {
				System.out.println("6단계:DB닫기 실패!!!");
				e.printStackTrace();
			}
		}
		return list;
	}
	//글 총 페이지수 구하기
	public int boardCount(int rowRange) {
		Connection conn=null;
		PreparedStatement psmt=null;
		ResultSet rs=null;
		int count=0;
		String url="jdbc:oracle:thin:@192.168.3.10:1521:xe";
		String user="hk";
		String password="hk";
		String sql=" SELECT CEIL(COUNT(*)/?) AS COUNT " + 
				   " FROM HKBOARD ";
		
		try {
			conn=DriverManager.getConnection(url, user, password);
			psmt=conn.prepareStatement(sql);
			psmt.setInt(1, rowRange);
			rs=psmt.executeQuery();
			while(rs.next()) {
				count=rs.getInt(1);
				System.out.println("페이지수:"+count);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null) {
					rs.close();
				}
				if(psmt!=null) {
					psmt.close();				
				}
				if(conn!=null) {
					conn.close();				
				}
				System.out.println("6단계:DB닫기 성공!!!");
			} catch (SQLException e) {
				System.out.println("6단계:DB닫기 실패!!!");
				e.printStackTrace();
			}
		}
		return count;
	}
	//글상세보기: select문 조건:seq ---> 결과 row 하나  --> Dto
	public HkDto getBoard(int seq) {
		HkDto dto=new HkDto();
		
		Connection conn=null;//연결
		PreparedStatement psmt=null;//쿼리 준비
		ResultSet rs=null;//결과받기
		
		String url="jdbc:oracle:thin:@192.168.3.10:1521:xe";
		String user="hk";
		String password="hk";
		
		String sql=" SELECT SEQ, ID, NAME, TITLE, CONTENT, REGDATE "
				 + " FROM HKBOARD WHERE SEQ=?";
		
		//드라이버 로딩---> DriverManager객체에 getConnection--> HK계정을 연결한 객체구함
		try {
			conn = DriverManager.getConnection(url, user, password);
			System.out.println("2단계:DB연결 성공!!");
			
			psmt=conn.prepareStatement(sql);
			psmt.setInt(1, seq);
			System.out.println("3단계:쿼리준비 성공!!");
			
			rs=psmt.executeQuery();
			System.out.println("4단계:쿼리실행 성공!!");
			
			while(rs.next()) { //[seq,id,name,title,content,regdate]
				dto.setSeq(rs.getInt(1)); 
				dto.setId(rs.getString(2)); 
				dto.setName(rs.getString(3)); 
				dto.setTitle(rs.getString(4)); 
				dto.setContent(rs.getString(5)); 
				dto.setRegdate(rs.getDate(6)); 
				System.out.println(dto);
			}
			System.out.println("5단계:쿼리결과받기성공!!");
		} catch (SQLException e) {
			System.out.println("JDBC실행실패!!("+getClass()+":getBoard())");
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null) {
					rs.close();
				}
				if(psmt!=null) {
					psmt.close();				
				}
				if(conn!=null) {
					conn.close();				
				}
				System.out.println("6단계:DB닫기 성공!!!");
			} catch (SQLException e) {
				System.out.println("6단계:DB닫기 실패!!!");
				e.printStackTrace();
			}
		}
		
		return dto;
	}
	
	//글쓰기: insert문 실행: 전달받는 파라미터 (id,name,title,contnet)
	//     메서드의 반환타입은??? y/n   ---> y일경우 어디로 이동, n일경우 어디로 이동하고---> boolean(true/false)
	//     select문 결과존재:executeQuery(),  executeUpdate(): int형 update된 row의 개수반환  0, >0
	
	public boolean insertBoard(HkDto dto) {
		int count=0;
		Connection conn=null;
		PreparedStatement psmt=null;
//		ResultSet rs=null;// insert문이기때문에 결과가 없어서 생략
		
		String url="jdbc:oracle:thin:@192.168.3.10:1521:xe";
		String user="hk";
		String password="hk";
		
		String sql=" INSERT INTO HKBOARD (SEQ,ID,NAME,TITLE,CONTENT,REGDATE) "
				+ " VALUES(HKBOARD_SEQ.NEXTVAL,?,?,?,?,sysdate) ";
		
		try {
			conn=DriverManager.getConnection(url, user, password);
			System.out.println("2단계:DB연결성공");
			
			psmt=conn.prepareStatement(sql);
			psmt.setString(1, dto.getId());
			psmt.setString(2, dto.getName());
			psmt.setString(3, dto.getTitle());
			psmt.setString(4, dto.getContent());
			System.out.println("3단계:쿼리준비성공!!");
			
			count=psmt.executeUpdate();
			System.out.println("4단계:쿼리실행성공(업데이트개수:"+count+")");
		} catch (SQLException e) {
			System.out.println("JDBC실행실패!!("+getClass()+":getBoard())");
			e.printStackTrace();
		}finally {
			try {
				if(psmt!=null) {
					psmt.close();				
				}
				if(conn!=null) {
					conn.close();				
				}
				System.out.println("6단계:DB닫기 성공!!!");
			} catch (SQLException e) {
				System.out.println("6단계:DB닫기 실패!!!");
				e.printStackTrace();
			}
		}
		
		//삼항연산자: (조건?참일경우 실행코드:거짓일경우 실행코드) 작성
		return count>0?true:false;
	}
	//글수정하기: executeUpdate()-> 반환값이 수정된 row의 개수 : int형반환 --> boolean타입
	// 쿼리작성시 파라미터 내용 결정: update문 , 수정할 내용:제목,내용 에 대해서 누구껄 수정할 건데??
	// update hkboard set title=?, content=? where seq=?
	public boolean updateBoard(HkDto dto) {
		int count=0;
		Connection conn=null;
		PreparedStatement psmt=null;
		
		String url="jdbc:oracle:thin:@192.168.3.10:1521:xe";
		String user="hk";
		String password="hk";
		
		String sql= " UPDATE HKBOARD SET TITLE=? , CONTENT=?, REGDATE=SYSDATE "
				+ " WHERE SEQ=? ";
		
		try {
			conn=DriverManager.getConnection(url, user, password);
			psmt=conn.prepareStatement(sql);
			psmt.setString(1, dto.getTitle());
			psmt.setString(2, dto.getContent());
			psmt.setInt(3, dto.getSeq());
			count=psmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("jdbc실패:"+getClass()+":updateBoard()");
			e.printStackTrace();
		}finally {
			try {
				if(psmt!=null) {
					psmt.close();
				}
				if(conn!=null) {
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return count>0?true:false;
	}
	//글삭제하기: delete문 - delete from hkboard where seq=?
	public boolean deleteBoard(int seq) {
		int count=0;
		Connection conn=null;
		PreparedStatement psmt=null;
		
		String url="jdbc:oracle:thin:@192.168.3.10:1521:xe";
		String user="hk";
		String password="hk";
		
		String sql=" DELETE FROM HKBOARD WHERE SEQ=? ";
		
		try {
			conn=DriverManager.getConnection(url, user, password);
			psmt=conn.prepareStatement(sql);
			psmt.setInt(1, seq);
			count=psmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("jdbc실패:"+getClass()+":deleteboard()");
			e.printStackTrace();
		}finally {
			try {
				if(psmt!=null) {
					psmt.close();
				}
				if(conn!=null) {
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return count>0?true:false;
	}
}







