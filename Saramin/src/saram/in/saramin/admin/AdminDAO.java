package saram.in.saramin.admin;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import oracle.jdbc.OracleTypes;
import saram.in.saramin.util.DBUtil;
import saram.in.saramin.util.EnumUtil;

/**
 * 관리자측의 DB연동 관련 기능 모음 클래스
 * @author user
 *
 */
public class AdminDAO {
	private Connection conn = null;

	public AdminDAO() {
		conn = DBUtil.getConnection();
	}
	
	/**
	 * 공고에 지원한 리스트를 반환
	 * @param firstPage 첫페이지
	 * @param lastPage 마지막페이지
	 * @return 지원리스트 반환
	 */
	public ArrayList<Object[]> getApplyList(int firstPage, int lastPage) {

		ArrayList<Object[]> list = new ArrayList<Object[]>();
		String sql = "call procApplyRecruit(?,?,?,?)";
		try {
			CallableStatement stat = conn.prepareCall(sql);
			stat.setInt(1, firstPage);
			stat.setInt(2, lastPage);
			stat.registerOutParameter(3, OracleTypes.CURSOR);
			stat.setInt(4, EnumUtil.ONEPAGE);
			stat.executeQuery();
			ResultSet rs = (ResultSet) stat.getObject(3);
			while (rs.next()) {
				list.add(new Object[] { rs.getString("성명"), rs.getString("지원일"), rs.getString("기업명") });
			}

			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 지원 페이지를 계산해서 반환.
	 * @return 지원 페이지
	 */
	public int getApplyTotalPage() {
		String sql = "select ceil((select count(*) from tblApplyRecruit))/" + EnumUtil.ONEPAGE + "+1 from dual";
		try {
			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery(sql);
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;

	}
	
	/**
	 * 직무명을 DB에 추가하는 메소드
	 * @param name 직무명
	 * @return 결과
	 */
	public int addJob(String name) {
		String sql = "insert into tblJobCategory values(categorySeq.nextval,?)";
		try {
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, name);
			return stat.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}
	
	/**
	 * 전체 직무의 페이지를 반환
	 * @return 직무 페이지수
	 */
	public int getJobTotalPage() {
		String sql = "select ceil((select count(*) as cnt from tblJobCategory))/"+EnumUtil.ONEPAGE+"+1 from dual";
		try {
			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery(sql);
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}
	
	/**
	 * 전체 직무리스트를 반환하는 메소드
	 * @return 직무리스트
	 */
	public ArrayList<Object[]> getJobList() {
		String sql = "select categorySeq,jobName from tblJobCategory";
		try {
			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery(sql);
			ArrayList<Object[]> list = new ArrayList<Object[]>();
			while (rs.next()) {
				list.add(new Object[] { rs.getString("categorySeq"), rs.getString("jobName") });
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	/**
	 * 직무번호를 받아 직무를 삭제하는 메소드
	 * @param num 직무번호
	 * @return 결과
	 */
	public int deleteJob(int num) {
		String sql = "delete from tblJobCategory where categorySeq = ?";
		try {
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setInt(1, num);
			return stat.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}
	/**
	 * 직무명을 수정하는 메소드
	 * @param name 직무명
	 * @param seq 직무번호
	 * @return 결과
	 */
	public int updatejob(String name, int seq) {
		String sql = "update tblJobCategory set jobName = ? where categorySeq = ?";
		try {
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, name);
			stat.setInt(2, seq);
			return stat.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return 0;
	}
	
	/**
	 * 제한글자의 페이지를 반환
	 * @return 제한글자 페이지
	 */
	public int getRestrictPage() {
		String sql = "select ceil((select count(*) as cnt from tblRestrict))/"+EnumUtil.ONEPAGE+"+1 from dual";
		try {
			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery(sql);
			if(rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 제한글자 리스트를 반환
	 * @return 제한글자 리스트
	 */
	public ArrayList<Object[]> getLetterList() {
		String sql = "select * from tblRestrict";
		try {
			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery(sql);
			ArrayList<Object[]> list = new ArrayList<Object[]>();
			while(rs.next()) {
				list.add(new Object[] {
					rs.getString(1),rs.getString(2)	
				});
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 제한글자를 추가하는 메소드
	 * @param letter 제한글자
	 * @return 결과
	 */
	public int addLetter(String letter) {
		String sql = "insert into tblRestrict values(restrictSeq.nextval,?)";
		try {
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, letter);
			return stat.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	/**
	 * 제한글자를 수정하는 메소드
	 * @param input 제한글자 번호
	 * @param letter 수정할 제한글자명
	 * @return 결과
	 */
	public int updateLetter(int input, String letter) {
		String sql="update tblRestrict set restrictLetter = ? where restrictSeq = ?";
		
		try {
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, letter);
			stat.setInt(2, input);
			return stat.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return 0;
	}
	
	/**
	 * 제한글자 삭제 메소드
	 * @param input 제한글자 번호
	 * @return 결과
	 */
	public int deleteLetter(int input) {
		String sql="delete from tblRestrict where restrictSeq = ?";
		
		try {
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setInt(1, input);
			return stat.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}

}
