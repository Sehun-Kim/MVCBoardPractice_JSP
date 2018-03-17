package com.javaex.ex.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.javalec.ex.dto.BDto;


public class BDao {
	DataSource dataSource; // context에서 커넥션풀을 가져오기 위한 DataSource 객체 선언

	public BDao(){ // BDao 객체 생성자
		try {
			Context context = new InitialContext(); // context 생성 
			dataSource = (DataSource)context.lookup("java:comp/env/jdbc/Oracle11g"); // dataSource가 오라클 DB를 참조한다고 초기화
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 이름, 제목, 내용을 DB에 넣는 메소드
	public void write(String bName, String bTitle, String bContent){
		System.out.println("BDao.write");

		Connection connection = null; // connection 선언
		PreparedStatement preparedStatement = null;

		try {
			connection = dataSource.getConnection(); // connection풀에서 커넥션을 하나 가져옴
			String query = "insert into mvc_board (bId, bName, bTitle, bContent, bHit, bGroup, bStep, bIndent) values (mvc_board_seq.nextval, ?, ?, ?, 0, mvc_board_seq.currval, 0, 0 )";
			// data 삽입을 위한 쿼리문 생성 bId는 시퀀스를 사용해서 삽입시 하나씩 증가하도록, bGroup은 id값과 동일한 값을 적용한다. bName, bTitle, bContent를 제외한 모든 값은 0으로 초기화해준다.
			preparedStatement = connection.prepareStatement(query); // connection에 쿼리문 삽입
			preparedStatement.setString(1, bName); // bName 삽입
			preparedStatement.setString(2, bTitle); // bTitle 삽입 
			preparedStatement.setString(3, bContent); // bContent 삽입
			preparedStatement.executeQuery(); // 쿼리문 실행
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try{
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	// DB에서 모든 row를 찾아 BDto 타입의 ArrayList를 반환하는 메소드.
	public ArrayList<BDto> list(){
		System.out.println("BDao.list");

		ArrayList<BDto> dtoList = new ArrayList<BDto>(); // BDto 타입의 arraylist 생성
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null; // 쿼리문의 실행결과를 받아올 result set 객체 

		try {
			connection = dataSource.getConnection();

			String query = "select bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent from mvc_board order by bGroup desc, bStep asc";
			// 최신글이 상단으로 가도록 bGroup은 desc(역순)으로, 답변글은 단 순서대로 나오도록 bStep은 asc(순차)로 query문 작성 
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()){ // resultSet 객체 안에 값이 없을 경우 false 있을 경우 true를 반환하는 next() 메소드. 이동할때마다 다른 값을 가리키게 함
				int bId = resultSet.getInt("bId"); // id 값 
				String bName = resultSet.getString("bName"); // 이름 
				String bTitle = resultSet.getString("bTitle"); // 제목 
				String bContent = resultSet.getString("bContent"); // 내용 
				Timestamp bDate = resultSet.getTimestamp("bDate"); // 날짜
				int bHit = resultSet.getInt("bHit"); // 조회수
				int bGroup = resultSet.getInt("bGroup"); // 그룹
				int bStep = resultSet.getInt("bStep"); // 그룹 번호
				int bIndent = resultSet.getInt("bIndent"); // 제목의 간격 수

				BDto dto = new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent); // BDto 객체 생성
				dtoList.add(dto); // ArrayList에 dto 객체를 넣어줌
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(resultSet != null) resultSet.close();
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}	
		return dtoList; // dtoList 반환
	}

	// bId로 DB에 있는 row를 찾아 BDto 객체를 반환해 주는 메소드. 단, upHit는 하지 않음
	public BDto contentFind(String strId){
		System.out.println("BDao.modifyFind");

		BDto dto = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = dataSource.getConnection();

			String query = "select * from mvc_board where bId = ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, Integer.parseInt(strId));
			resultSet = preparedStatement.executeQuery();

			if(resultSet.next()){
				int bId = resultSet.getInt("bId");
				String bName = resultSet.getString("bName");
				String bTitle = resultSet.getString("bTitle");
				String bContent = resultSet.getString("bContent");
				Timestamp bDate = resultSet.getTimestamp("bDate");
				int bHit = resultSet.getInt("bHit");
				int bGroup = resultSet.getInt("bGroup");
				int bStep = resultSet.getInt("bStep");
				int bIndent = resultSet.getInt("bIndent");

				dto = new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(resultSet != null) resultSet.close();
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return dto;
	}

	// bId로 DB에 있는 row를 찾아 BDto 객체를 반환해 주는 메소드
	public BDto contentView(String strId){
		System.out.println("BDao.contentView");

		upHit(strId);

		BDto dto = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = dataSource.getConnection();

			String query = "select * from mvc_board where bId = ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, Integer.parseInt(strId));
			resultSet = preparedStatement.executeQuery();

			if(resultSet.next()){
				int bId = resultSet.getInt("bId");
				String bName = resultSet.getString("bName");
				String bTitle = resultSet.getString("bTitle");
				String bContent = resultSet.getString("bContent");
				Timestamp bDate = resultSet.getTimestamp("bDate");
				int bHit = resultSet.getInt("bHit");
				int bGroup = resultSet.getInt("bGroup");
				int bStep = resultSet.getInt("bStep");
				int bIndent = resultSet.getInt("bIndent");

				dto = new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(resultSet != null) resultSet.close();
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return dto;
	}

	// DB에 있는 제목, 이름, 내용을 수정해주는 메소드.
	public void modify(String bId, String bName, String bTitle, String bContent){
		System.out.println("BDto.modify");

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = dataSource.getConnection();

			String query = "update mvc_board set bName = ?, bTitle = ?, bContent = ? where bId = ? ";
			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setString(1, bName);
			preparedStatement.setString(2, bTitle);
			preparedStatement.setString(3, bContent);
			preparedStatement.setInt(4, Integer.parseInt(bId));

			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}	
	}

	// bId로 DB에 있는 row를 찾아 삭제하는 메소드
	public void delete(String bId){
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = dataSource.getConnection();
			String query = "delete from mvc_board where bId = ?";

			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, Integer.parseInt(bId));

			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
	// 답변내용을 DB에 넣어주는 메소드
	public void reply(String bId, String bName, String bTitle, String bContent, String bGroup, String bStep, String bIndent){
		System.out.println("BDao.reply");
		
		replyShape(bGroup, bStep);
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = dataSource.getConnection();
			
			String query = "insert into mvc_board (bId, bName, bTitle, bContent, bGroup, bStep, bIndent) values (mvc_board_seq.nextval, ?, ?, ?, ?, ?, ?)";
			preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setString(1, bName);
			preparedStatement.setString(2, bTitle);
			preparedStatement.setString(3, bContent);
			preparedStatement.setInt(4, Integer.parseInt(bGroup)); // 답변을 달 bGroup
			preparedStatement.setInt(5, Integer.parseInt(bStep) + 1); // 답변을 달 bStep보다 1 증가
			preparedStatement.setInt(6, Integer.parseInt(bIndent) + 1); // 답변을 달 bIndent 보다 1 증가
			
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	// bId로 DB에 있는 row를 찾아 bHit(조회수)를 증가시키는 메소드
	public void upHit(String bId){
		System.out.println("BDao.upHit");

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = dataSource.getConnection();
			String query = "update mvc_board set bHit = bHit + 1 where bId = ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, bId);

			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
	// 답변의 위치를 지정하기 위한 메소드
	public void replyShape(String strGroup, String strStep){
		System.out.println("BDao.replyShape");
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = dataSource.getConnection();
			
			// DB table의 같은 bGroup을 가진 파라미터로 받은 strStep(원 게시물의 bStep 값)보다 큰 row의 bStep을 1씩 증가 시킴
			// 즉 답변이 게시판에 추가될 하나의 공간을 마련
			String query = "update mvc_board set bStep = bStep + 1 where bGroup = ? and bStep > ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, Integer.parseInt(strGroup));
			preparedStatement.setInt(2, Integer.parseInt(strStep));
			
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
}
