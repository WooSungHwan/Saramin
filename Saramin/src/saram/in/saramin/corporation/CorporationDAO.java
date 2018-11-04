package saram.in.saramin.corporation;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import oracle.jdbc.internal.OracleTypes;
import saram.in.saramin.jobseeker.JobSeekerDAO;
import saram.in.saramin.jobseeker.JobSeekerDTO;
import saram.in.saramin.jobseeker.RepresentationDTO;
import saram.in.saramin.util.CorporationUtil;
import saram.in.saramin.util.DBUtil;

/**
 * 기업회원의 정보를 DB와 연동하는 기능을 담당하는 클래스
 * 
 * @author woo
 *
 */
public class CorporationDAO extends JobSeekerDAO {
	private Connection conn = null;

	public CorporationDAO() {
		conn = DBUtil.getConnection();
	}
	
	/**
	 * 입력공고 정보를 DB에 추가하는 메소드
	 * @param recruitTitle 공고제목 
	 * @param jobSeq 직무번호
	 * @param qualification 지원자격
	 * @param num 채용인원
	 * @param enddate 마감일
	 * @param others 추가설명
	 * @return 결과
	 */
	public int addRecruit(String recruitTitle, String jobSeq, String qualification, String num, String enddate,
			String others) {
		String sql = "call procAddRecruit(?,?,?,?,?,?,?,?)";
		try {
			CallableStatement stat = conn.prepareCall(sql);
			stat.setString(1, CorporationUtil.loginCorporation.getCorpSeq());
			stat.setString(2, jobSeq);
			stat.setString(3, recruitTitle);
			stat.setString(4, num);
			stat.setString(5, qualification);
			stat.setString(6, others);
			stat.setString(7, enddate);
			stat.registerOutParameter(8, OracleTypes.NUMBER);
			stat.executeQuery();
			return stat.getInt(8);
		} catch (Exception e) {
			return 0;
		}

	}
	
	/**
	 * 로그인한 기업의 공고를 반환.
	 * @return
	 */
	public ArrayList<RecruitmentDTO> getCorpMyRecruit() {
		String sql = "call procSeeRecruit(?,?)";
		try {
			CallableStatement stat = conn.prepareCall(sql);
			stat.setString(1, CorporationUtil.loginCorporation.getCorpSeq());
			stat.registerOutParameter(2, OracleTypes.CURSOR);
			stat.executeQuery();
			ResultSet rs = (ResultSet) stat.getObject(2);
			ArrayList<RecruitmentDTO> list = new ArrayList<RecruitmentDTO>();
			while (rs.next()) {
				RecruitmentDTO dto = new RecruitmentDTO();
				dto.setRegdate(rs.getString("등록일"));// 등록일
				dto.setJobName(rs.getString("직무명"));// 카테고리명
				dto.setCorpName(rs.getString("기업명"));// 기업명
				dto.setEnddate(rs.getString("마감일"));
				dto.setNum(rs.getString("채용인원"));// 채용인원
				dto.setOtherDiscription(rs.getString("공고설명"));// 공고설명
				dto.setQualification(rs.getString("지원자격"));// 자격요건
				dto.setRecruitSeq(rs.getString("공고번호"));// 공고번호
				dto.setTitle(rs.getString("공고제목"));// 공고제목
				list.add(dto);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	/**
	 * 로그인한 기업 공고의 페이지 반환
	 * @return 페이지수
	 */
	public int getMyRecruitPage() {
		String sql = "select fnMyRecruitPage(?) from dual";
		try {
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, CorporationUtil.loginCorporation.getCorpSeq());
			ResultSet rs = stat.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	/**
	 * DB상에 공고를 삭제
	 * @param recruitSeq 공고번호
	 * @return 결과
	 */
	public int deleteRecruit(String recruitSeq) {
		String sql = "delete from tblRecruitment where recruitSeq = ?";
		try {
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, recruitSeq);
			return stat.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * DB상에 공고를 수정
	 * @param input 선택변수
	 * @param recruitSeq 공고번호
	 * @param content 수정내용
	 * @return 결과
	 */
	public int updateRecruit(int input, String recruitSeq, String content) {
		String column="";
		
		if (input == 1) { //제목수정
			column = "title";
		} else if (input == 2) { //자격수정
			column = "qualification";
		} else if (input == 3) { //마감일수정	
			column="enddate";
		} else if (input == 4) {//공고설명 수정
			column = "otherDiscription";
		} else if (input == 5) { //채용인원 수정
			column="num";
		} 
		String sql = "update tblRecruitment set "+column+" = ? where recruitSeq = ?";
		try {
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, content);
			stat.setString(2, recruitSeq);
			return stat.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	/**
	 * 관심인재의 정보들을 반환.
	 * @param corpSeq 기업번호
	 * @return 관심인재 리스트
	 */
	public ArrayList<JobSeekerDTO> getIPJobSeeker(String corpSeq) {
		String sql = "select js.jobseekerSeq as 번호, js.name as 성명, js.ssn as 주민번호, js.tel as 전화번호, js.address as 주소 " + 
				"    ,js.email as 이메일 " + 
				"    from tblInterestedPerson ip inner join tblJobSeeker js on ip.jobseekerseq = js.jobseekerSeq " + 
				"    where ip.corpSeq = ?";
		try {
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, corpSeq);
			ResultSet rs = stat.executeQuery();
			ArrayList<JobSeekerDTO> list = new ArrayList<JobSeekerDTO>();
			while(rs.next()) {
				JobSeekerDTO dto = new JobSeekerDTO();
				dto.setAddress(rs.getString("주소"));
				dto.setEmail(rs.getString("이메일"));
				dto.setJobSeekerSeq(rs.getString("번호"));
				dto.setName(rs.getString("성명"));
				dto.setSsn(rs.getString("주민번호"));
				dto.setTel(rs.getString("전화번호"));
				list.add(dto);
			}
			return list;
		} catch (Exception e) {
			System.out.println("CorporationDAO.getIPJobSeeker :" + e.toString());
		}
		
		
		return null;
	}
	
	
	/**
	 * 관심인재목록에서 삭제하는 메소드
	 * @param jobSeekerSeq 개인번호
	 * @param corpSeq 기업번호
	 * @return 결과
	 */
	public int deleteIP(String jobSeekerSeq, String corpSeq) {
		String sql = "delete from tblInterestedPerson where jobseekerSeq = ? and corpSeq = ?";
		try {
			PreparedStatement stat =conn.prepareStatement(sql);
			stat.setString(1, jobSeekerSeq);
			stat.setString(2, corpSeq);
			return stat.executeUpdate();
		} catch (Exception e) {
			System.out.println("CorporationDAO.deleteIP :" + e.toString());
		}
		
		return 0;
	}
	
	/**
	 * 공개 문서정보를 반환하는 메소드
	 * @return
	 */
	public ArrayList<RepresentationDTO> getOpenRep() {
		String sql = "select rep.repseq as 번호, js.name as 성명, re.name as 이력서명, re.toeic as 토익성적, re.intern as 인턴경험, " + 
				"    re.volunteer as 봉사경험, re.awards as 수상경험, i.name as 자기소개서명, i.selfintroduction as 자기소개내용 " + 
				"    from tblRepresentation rep inner join tblResume re on re.resumeSeq = rep.resumeSeq " + 
				"    inner join tblIntroduction i on i.introSeq = rep.introSeq  " + 
				"        inner join tblJobSeeker js on js.jobseekerSeq = rep.jobseekerSeq where rep.Visibility = 1";
		try {
			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery(sql);
			ArrayList<RepresentationDTO> list = new ArrayList<RepresentationDTO>();
			while(rs.next()) {
				RepresentationDTO dto = new RepresentationDTO();
				dto.setIntroTitle(rs.getString("자기소개서명"));
				dto.setIntroContent(rs.getString("자기소개내용"));
				dto.setInturn(rs.getString("인턴경험"));
				dto.setJobSeekerName(rs.getString("성명"));
				dto.setRepSeq(rs.getString("번호"));
				dto.setResumeTitle(rs.getString("이력서명"));
				dto.setToeic(rs.getString("토익성적"));
				dto.setVolun(rs.getString("봉사경험"));
				dto.setAwards(rs.getString("수상경험"));
				list.add(dto);
			}
			return list;
			
		} catch (Exception e) {
			System.out.println("CorporationDAO.getOpenRep :" + e.toString());
		}
		
		return null;
	}
	
	/**
	 * 개인의 희망직무 정보를 반환하는 메소드
	 * @param jobSeq
	 * @return
	 */
	public ArrayList<RepresentationDTO> getRepHope(String jobSeq) {
		String sql = "select js.jobseekerSeq as 번호, js.name as 성명, r.toeic as 토익점수, r.awards as 수상경험, r.volunteer as 봉사경험, " + 
				"    r.intern as 인턴경험, r.name as 이력서명, i.name as 자기소개서명,i.selfintroduction as 자기소개서내용 " + 
				"            from tblHopeJob hj inner join tbljobseeker js on hj.jobseekerSeq = js.jobseekerSeq  " + 
				"                inner join tbljobcategory jc on jc.categorySeq = hj.categorySeq " + 
				"                    inner join tblRepresentation rep on rep.jobSeekerSeq = js.jobseekerSeq " + 
				"                        inner join tblResume r on r.resumeSeq = rep.resumeSeq\r\n" + 
				"                            inner join tblintroduction i on i.introSeq = rep.introSeq " + 
				"                                where jc.categorySeq=? and rep.Visibility=1";
		try {
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, jobSeq);
			ResultSet rs = stat.executeQuery();
			ArrayList<RepresentationDTO> list = new ArrayList<RepresentationDTO>();
			while(rs.next()) {
				RepresentationDTO dto =new RepresentationDTO();
				dto.setAwards(rs.getString("수상경험"));
				dto.setIntroContent(rs.getString("자기소개서내용"));
				dto.setIntroTitle(rs.getString("자기소개서명"));
				dto.setInturn(rs.getString("인턴경험"));
				dto.setJobSeekerName(rs.getString("성명"));
				dto.setJobSeekerSeq(rs.getString("번호"));
				dto.setResumeTitle(rs.getString("이력서명"));
				dto.setToeic(rs.getString("토익점수"));
				dto.setVolun(rs.getString("봉사경험"));
				list.add(dto);
			}
			return list;
		} catch (Exception e) {
			System.out.println("CorporationDAO.getRepHope :" + e.toString());
		}
		
		return null;
	}
	
	/**
	 * 관심인재목록을 추가하는 메소드
	 * @param jobseekerSeq
	 * @return
	 */
	public int addIP(String jobseekerSeq) {
		String sql = "insert into tblInterestedPerson values(ipSeq.nextval,?,?)";
		try {
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, CorporationUtil.loginCorporation.getCorpSeq());
			stat.setString(2,jobseekerSeq);
			return stat.executeUpdate();			
		} catch (Exception e) {
			System.out.println("CorporationDAO.addIP :" + e.toString());
		}
		
		return 0;
	}

}























