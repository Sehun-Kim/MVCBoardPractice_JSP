package com.javaex.ex.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.javalec.ex.dto.MDto;

public class MDao {
	// static final로 상수 선언 
	public static final int MEMBER_NONEXITENT = 0; // 멤버가 없을 경우
	public static final int MEMBER_EXITENT = 1; // 멤버가 있을 경우
	public static final int MEMBER_JOIN_FAIL = 0; // 멤버 가입 실패
	public static final int MEMBER_JOIN_SUCCESS = 1; // 멤버 가입 성공
	public static final int MEMBER_LOGIN_PW_NO_GOOD = 0; // 로그인시 비밀번호가 틀릴 때
	public static final int MEMBER_LOGIN_SUCESS = 1; // 로그인 성공
	public static final int MEMBER_LOGIN_IS_NOT = -1; // 로그인시 아이디가 아예 없을때

	// singleton : static으로 객체 하나만 생성하여 서버에 접속하는 모든 클라이언트들이 공유하도록 하기 위해
	private static MDao instance = new MDao();
	
	// MDao 생성자
	private MDao(){}
	
	// 외부에서 멤버 객체를 얻기 위해서 유일하게 접근할 수 있는 메소드 
	public static MDao getInstance(){
		return instance;
	}
	
	// login할 user가 있는지 없는지 확인하는 메소드
	public int userCheck(String id, String pw) {
		int returnInt = 0; // 로그인한 유저가 있는지 없는지 확인하는 상수가 저장될 변수
		String dbPassword; // db에서 받아올 user 비밀번호가 저장될 변수
		
		Connection connection = null; // connection 변수
		PreparedStatement prepareStatement = null; // prestatement변수 
		ResultSet resultSet = null;
		String query = "select pw from members where id=?";
		
		try{
			connection = getConnection(); // 커넥션풀에서 얻은 커넥션으로 초기화
			prepareStatement = connection.prepareStatement(query); // prefareStatement에 쿼리문을 넣어줌
			prepareStatement.setString(1, id); // user의 id를 쿼리문에 넣어줌
			resultSet = prepareStatement.executeQuery(); // 쿼리문 실행
			
			// 쿼리문을 돌려 member table에 해당 id값과 같은 row가 있는지 탐색
			if(resultSet.next()){
				// 같은 id가 있을 경우 
				dbPassword = resultSet.getString("pw"); // 해당 row의 비밀번호 값을 가져옴
				
				if(dbPassword.equals(pw)){
					returnInt = MDao.MEMBER_LOGIN_SUCESS; // 비밀번호가 같은경우
				}else{
					returnInt = MDao.MEMBER_LOGIN_PW_NO_GOOD; // 비밀번호가 다른경우
				}
			}else{
				returnInt = MDao.MEMBER_NONEXITENT; // 동일한 id가 없는경우
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				if(connection != null) connection.close();
				if(prepareStatement != null) prepareStatement.close();
				if(resultSet != null) resultSet.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return returnInt; // user check 결과 값 반환
	}
	
	// id값으로 MDao 객체에서 MDto객체를 찾아 반환하는 메소드
	public MDto getMember(String id){
		
		Connection connection = null; // connection 변수
		PreparedStatement preparedStatement = null; // prestatement 변수 
		ResultSet resultSet = null;
		String query = "select * from members where id=?";
		
		MDto dto = null;
		
		try{
			connection = getConnection();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, id);
			resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()){ // id값과 같은 row를 찾았을 때 
				dto = new MDto(); // dto 객체 생성
				
				// 해당 객체에 쿼리문으로 찾은 값들을 넣어줌
				dto.setId(resultSet.getString("id"));
				dto.setPw(resultSet.getString("pw"));
				dto.setName(resultSet.getString("name"));
				dto.seteMail(resultSet.getString("eMail"));
				dto.setrDate(resultSet.getTimestamp("rDate"));
				dto.setAddress(resultSet.getString("address"));
				
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(connection != null) connection.close();
				if(preparedStatement != null) preparedStatement.close();
				if(resultSet != null) resultSet.close();	
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return dto; // dto 객체 반환
	}
	
	// id가 있는지 없는지 확인하는 메소드
	public int confirmId(String id){
		int resultInt = 0;
		
		Connection connection = null; // connection 변수
		PreparedStatement prepareStatement = null; // prestatement변수 
		ResultSet resultSet = null;
		String query = "select * from members where id=?";
		
		try{
			connection = getConnection();
			prepareStatement = connection.prepareStatement(query);
			prepareStatement.setString(1, id);
			resultSet = prepareStatement.executeQuery();
			
			// id가 있을 경우 
			if(resultSet.next()){
				resultInt = MDao.MEMBER_EXITENT; // 1
			}else{
				resultInt = MDao.MEMBER_NONEXITENT; // 0
			}	
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(connection != null) connection.close();
				if(prepareStatement != null) prepareStatement.close();
				if(resultSet != null) resultSet.close();	
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return resultInt;
	}
	
	public int insertMember(MDto dto){
		int resultInt = 0;
		
		Connection connection = null; // connection 변수
		PreparedStatement prepareStatement = null; // prestatement변수 
		String query = "insert into members values (?,?,?,?,?,?)";
		
		try {
			connection = getConnection();
			prepareStatement = connection.prepareStatement(query);
			
			prepareStatement.setString(1, dto.getId());
			prepareStatement.setString(2, dto.getPw());
			prepareStatement.setString(3, dto.getName());
			prepareStatement.setString(4, dto.geteMail());
			prepareStatement.setTimestamp(5, dto.getrDate());
			prepareStatement.setString(6, dto.getAddress());
			prepareStatement.executeUpdate();
			
			resultInt = MDao.MEMBER_JOIN_SUCCESS;
		} catch (Exception e) {
			resultInt = MDao.MEMBER_JOIN_FAIL;
			e.printStackTrace();
		}finally{
			try {
				if(connection != null) connection.close();
				if(prepareStatement != null) prepareStatement.close();	
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			
		}
		return resultInt;
	}
	
	// member를 수정하는 메소드 
	public int updateMember(MDto dto) {
		int resultInt = 0;
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		String query = "update members set pw=?, eMail=?, address=? where id=?";
		
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, dto.getPw());
			pstmt.setString(2, dto.geteMail());
			pstmt.setString(3, dto.getAddress());
			pstmt.setString(4, dto.getId());
			resultInt = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				connection.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return resultInt;
	}
	
	
	// 커넥션풀에 연결하여 커넥션을 반환하는 메소드 
	private Connection getConnection(){
		Context context = null;
		DataSource dataSource = null;
		Connection connection = null;
		try {
			context = new InitialContext();
			dataSource = (DataSource)context.lookup("java:comp/env/jdbc/Oracle11g");
			connection = dataSource.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return connection;
	}
}
