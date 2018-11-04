package saram.in.saramin;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import oracle.jdbc.OracleTypes;
import saram.in.saramin.corporation.CorporationDTO;
import saram.in.saramin.corporation.CorporationLoginDTO;
import saram.in.saramin.jobseeker.JobSeekerDTO;
import saram.in.saramin.jobseeker.JobSeekerLoginDTO;
import saram.in.saramin.util.DBUtil;

/**
 * 메인 파트의 DB연동 기능 모음 클래스
 * @author user
 *
 */
public class MainDAO {
	private Connection conn = null;

	public MainDAO() {
		conn = DBUtil.getConnection();
	}
	
	/**
	 * 로그인을 체크하는 메소드(개인,기업,관리자)
	 * @param input 분기변수
	 * @param id 아이디
	 * @param pw 비밀번호
	 * @return 결과
	 */
	public int loginCheck(int input, String id, String pw) {
		String sql = "";
		if (input == 1) {
			// 개인회원
			sql = "select count(*) from tblJobSeekerLogin where id = ? and pw =?";
		} else if (input == 2) {
			// 기업회원
			sql = "select count(*) from tblCorporationLogin where id =? and pw =?";
		} else if (input == 3) {
			// 관리자
			sql = "select count(*) from admin where id = ? and pw =?";
		}
		try {
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, id);
			stat.setString(2, pw);
			ResultSet rs = stat.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			return 0;
		}
		return 0;
	}
	
	/**
	 * 아이디와 비밀번호로 개인의 번호를 알아냄
	 * @param id 아이디
	 * @param pw 비밀번호
	 * @return
	 */
	public String getJobSeekerSeq(String id, String pw) {
		String sql = "call procGetJobseekerSeq(?,?,?)";
		try {
			CallableStatement stat = conn.prepareCall(sql);
			stat.setString(1, id);
			stat.setString(2, pw);
			stat.registerOutParameter(3, OracleTypes.NUMBER);
			stat.executeQuery();
			return stat.getString(3);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 개인번호로 개인의 객체를 반환
	 * @param seq 개인번호
	 * @return 개인 객체
	 */
	public JobSeekerDTO getJobSeekerDTO(String seq) {
		String sql = "call procGetJobSeeker(?,?)";
		try {
			CallableStatement stat = conn.prepareCall(sql);
			stat.setString(1, seq);
			stat.registerOutParameter(2, OracleTypes.CURSOR);

			stat.executeQuery();
			ResultSet rs = (ResultSet) stat.getObject(2);
			JobSeekerDTO dto = new JobSeekerDTO();
			if (rs.next()) {
				dto.setJobSeekerSeq(rs.getString("jobSeekerSeq"));
				dto.setName(rs.getString("name"));
				dto.setAddress(rs.getString("address"));
				dto.setEmail(rs.getString("email"));
				dto.setSsn(rs.getString("ssn"));
				dto.setTel(rs.getString("tel"));
			}
			return dto;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 개인회원의 아이디와 비밀번호를 얻어오는 메소드
	 * 
	 * @param name
	 *            이름
	 * @param ssn
	 *            주민번호
	 * @return 아이디,비밀번호
	 */
	public JobSeekerLoginDTO getJobSeekerIdPw(String name, String ssn) {
		String sql = "select id,pw,name from tblJobseeker js inner join tbljobseekerlogin jsl on js.jobseekerseq = jsl.jobseekerseq"
				+ "    where name=? and substr(ssn,8,7)=?";
		try {
			try {
				PreparedStatement stat = conn.prepareStatement(sql);
				stat.setString(1, name);
				stat.setString(2, ssn);
				ResultSet rs = stat.executeQuery();
				if (rs.next()) {
					JobSeekerLoginDTO dto = new JobSeekerLoginDTO();
					dto.setId(rs.getString("id"));
					dto.setPw(rs.getString("pw"));
					dto.setJobSeekerSeq(rs.getString("name"));
					return dto;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 기업회원의 아이디와 비밀번호를 가진 객체를 반환하는 메소드
	 * 
	 * @param name
	 *            기업명
	 * @param ssn
	 *            사업자번호
	 * @return 아이디,비밀번호 객체
	 */
	public CorporationLoginDTO getCorporationIdPw(String name, String ssn) {
		String sql = "select id,pw,name from tblCorporation c inner join tblCorporationLogin cl on c.corpSeq = cl.corpSeq "
				+ " where name = ? and ssn = ?";
		try {
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, name);
			stat.setString(2, ssn);
			ResultSet rs = stat.executeQuery();
			if (rs.next()) {
				System.out.println(1);
				CorporationLoginDTO dto = new CorporationLoginDTO();
				dto.setId(rs.getString("id"));
				dto.setPw(rs.getString("pw"));
				dto.setCorpSeq(rs.getString("name"));
				return dto;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 아이디의 중복체크 메소드
	 * @param input 분기변수
	 * @param id
	 * @return
	 */
	public int idDoubleCheck(int input, String id) {
		String sql = "";

		if (input == 1) { // 개인
			sql = "select count(*) from tblJobSeekerLogin where id = ?";
			try {
				PreparedStatement stat = conn.prepareStatement(sql);
				stat.setString(1, id);
				ResultSet rs = stat.executeQuery();
				if (rs.next()) {
					return rs.getInt(1);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (input == 2) {// 기업
			sql = "select count(*) from tblCorporationLogin where id = ?";
			try {
				PreparedStatement stat = conn.prepareStatement(sql);
				stat.setString(1, id);
				ResultSet rs = stat.executeQuery();
				if (rs.next()) {
					return rs.getInt(1);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return 0;
	}
	
	/**
	 * 개인의 정보를 추가하는 메소드
	 * @param id 아이디
	 * @param pw 비밀번호
	 * @param name 이름
	 * @param ssn 주민번호
	 * @param tel 전번
	 * @param address 주소
	 * @param email 이메일
	 * @return 결과
	 */
	public int addJobSeeker(String id, String pw, String name, String ssn, String tel, String address, String email) {
		String sql = "call procAddJobSeeker(?,?,?,?,?,?,?,?)";

		try {
			CallableStatement stat = conn.prepareCall(sql);
			stat.setString(1, id);
			stat.setString(2, pw);
			stat.setString(3, name);
			stat.setString(4, ssn);
			stat.setString(5, tel);
			stat.setString(6, address);
			stat.setString(7, email);
			stat.registerOutParameter(8, OracleTypes.NUMBER);
			stat.executeQuery();
			return stat.getInt(8);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}
	
	/**
	 * 기업의 정보를 추가하는 메소드
	 * @param id 아이디
	 * @param pw 비밀번호
	 * @param name 기업명
	 * @param ssn 사업자번호
	 * @param tel 전화번호
	 * @param address 주소
	 * @param email 홈페이지주소
	 * @param establishDate 설ㄹ립일
	 * @return 기업객체
	 */
	public int addCorp(String id, String pw, String name, String ssn, String tel, String address, String email,
			String establishDate) {
		String sql = "call procAddCorp(?,?,?,?,?,?,?,?,?)";
		try {
			CallableStatement stat = conn.prepareCall(sql);
			stat.setString(1, id);
			stat.setString(2, pw);
			stat.setString(3, name);
			stat.setString(4, ssn);
			stat.setString(5, tel);
			stat.setString(6, address);
			stat.setString(7, email);
			stat.setString(8, establishDate);
			stat.registerOutParameter(9, OracleTypes.NUMBER);
			stat.executeQuery();
			return stat.getInt(9);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}
	/**
	 * 기업의 id와 pw로 기업번호를 반환
	 * @param id 아이디
	 * @param pw 비밀번호
	 * @return 기업번호
	 */
	public String getCorporationseq(String id, String pw) {
		String sql = "select corpSeq from tblCorporationLogin where id=? and pw=?";
		try {
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, id);
			stat.setString(2, pw);
			ResultSet rs = stat.executeQuery();
			while (rs.next()) {
				return rs.getString("corpSeq");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 기업번호로 기업의 객체롤 반환
	 * @param seq 기업번호
	 * @return 기업객체
	 */
	public CorporationDTO getCorporation(String seq) {
		String sql = "call procGetCorporationfromSeq(?,?)";
		try {

			CallableStatement stat = conn.prepareCall(sql);
			stat.setString(1, seq);
			stat.registerOutParameter(2, OracleTypes.CURSOR);
			stat.executeQuery();
			ResultSet rs = (ResultSet) stat.getObject(2);
			if (rs.next()) {
				CorporationDTO dto = new CorporationDTO();
				dto.setAddress(rs.getString("주소"));
				dto.setCorpSeq(rs.getString("기업번호"));
				dto.setEmail(rs.getString("홈페이지"));
				dto.setEstablishDate(rs.getString("설립일"));
				dto.setName(rs.getString("기업명"));
				dto.setSsn(rs.getString("사업자번호"));
				dto.setTel(rs.getString("전화번호"));
				return dto;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
