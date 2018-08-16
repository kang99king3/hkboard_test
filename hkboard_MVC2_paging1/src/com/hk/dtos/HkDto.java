package com.hk.dtos;

import java.util.Date;

public class HkDto {
	private int seq;
	private String id;
	private String name;
	private String title;
	private String content;
	private Date regdate;
	// HkDto dto=new HkDto();    dto.seq  (X) ---> dto.getSeq(): 은닉화
	
	public HkDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	//생성자 오버로딩
	public HkDto(int seq, String id, String name, String title, String content, Date regdate) {
		super();
		this.seq = seq;
		this.id = id;
		this.name = name;
		this.title = title;
		this.content = content;
		this.regdate = regdate;
	}
	
	public HkDto(int seq, String title, String content) {
		this.seq = seq;
		this.title = title;
		this.content = content;
	}
	
	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
    
	//오버라이딩: 자식 클래스에서 재정의하면 부모의 메서드를 호출해도 자식에서 호출된다.
	// syso(dto.toString()) ---> hashcode 출력  ---> 오버라이딩 --> 자식클래스에서 재정의
	//Object ---> 중요 4대 메서드: getClass(), hashCode(), equals(), toString()
	@Override
	public String toString() {
		return "HkDto [seq=" + seq + ", id=" + id + ", name=" + name + ", title=" + title + ", content=" + content
				+ ", regdate=" + regdate + "]";
	}
	
	
	
}
