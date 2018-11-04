package saram.in.saramin.jobseeker;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import oracle.jdbc.OracleTypes;
import saram.in.saramin.admin.ReviewDTO;
import saram.in.saramin.corporation.CorporationDTO;
import saram.in.saramin.corporation.RecruitmentDTO;
import saram.in.saramin.util.DBUtil;
import saram.in.saramin.util.EnumUtil;
import saram.in.saramin.util.JobSeekerUtil;

/**
 * 개인회원과 DB를 연결시켜줄 클래스
 * 
 * @author woo
 *
 */
public class JobSeekerDAO {
	private Connection conn;

	public JobSeekerDAO() {
		conn = DBUtil.getConnection();
	}

	/**
	 * 개인회원번호로 이력서를 반환하는것.
	 * 
	 * @param jobSeekerSeq
	 * @return
	 */
	public ArrayList<ResumeDTO> getResumeFromSeekerSeq(String jobSeekerSeq) {
		String sql = "call procGetResumeFromSeekerSeq(?,?)";
		try {
			CallableStatement stat = conn.prepareCall(sql);
			stat.setString(1, jobSeekerSeq);
			stat.registerOutParameter(2, OracleTypes.CURSOR);
			stat.executeQuery();
			ResultSet rs = (ResultSet) stat.getObject(2);
			ArrayList<ResumeDTO> rlist = new ArrayList<ResumeDTO>();
			while (rs.next()) {
				ResumeDTO dto = new ResumeDTO();
				dto.setResumeSeq(rs.getString("resumeSeq"));
				dto.setName(rs.getString("name"));
				dto.setToeic(rs.getString("toeic"));
				dto.setVolunteer(rs.getString("volunteer"));
				dto.setIntern(rs.getString("intern"));
				dto.setAwards(rs.getString("awards"));
				dto.setJobSeekerSeq(rs.getString("jobSeekerSeq"));
				dto.setEtc(rs.getString("etc"));
				rlist.add(dto);
			}
			return rlist;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 개인번호로 해당 개인의 자기소개서 객체리스트를 반환하는 메소드
	 * 
	 * @param jobSeekerSeq
	 *            개인번호
	 * @return 자기소개서 객체 리스트
	 */
	public ArrayList<IntroductionDTO> getIntroFromSeekerSeq(String jobSeekerSeq) {
		String sql = "call procGetIntroFromSeekerSeq(?,?)";
		try {
			CallableStatement stat = conn.prepareCall(sql);
			stat.setString(1, jobSeekerSeq);
			stat.registerOutParameter(2, OracleTypes.CURSOR);
			stat.executeQuery();
			ResultSet rs = (ResultSet) stat.getObject(2);
			ArrayList<IntroductionDTO> ilist = new ArrayList<IntroductionDTO>();
			while (rs.next()) {
				IntroductionDTO dto = new IntroductionDTO();
				dto.setIntroSeq(rs.getString("introSeq"));
				dto.setJobSeekerSeq(rs.getString("jobSeekerSeq"));
				dto.setName(rs.getString("name"));
				dto.setSelfIntroduction(rs.getString("selfIntroduction"));
				ilist.add(dto);
			}
			return ilist;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 자기소개서를 DB에 추가해주는 메소드
	 * 
	 * @param dto
	 *            자기소개서 객체
	 * @return 결과
	 */
	public int addIntroduction(IntroductionDTO dto) {
		String sql = " call procAddIntroduction(?,?,?,?)";
		try {
			CallableStatement stat = conn.prepareCall(sql);
			int seq = Integer.parseInt(dto.getJobSeekerSeq());
			stat.setInt(1, seq);
			stat.setString(2, dto.getName());
			stat.setString(3, dto.getSelfIntroduction());
			stat.registerOutParameter(4, OracleTypes.NUMBER);
			stat.executeQuery();

			return stat.getInt(4);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 이력서를 DB에 추가해주는 메소드
	 * 
	 * @param dto
	 *            이력서 객체
	 * @return 결과
	 */
	public int addResume(ResumeDTO dto) {
		String sql = "call procAddResume(?,?,?,?,?,?,?,?)";
		try {
			CallableStatement stat = conn.prepareCall(sql);
			int seq = Integer.parseInt(dto.getJobSeekerSeq());
			stat.setInt(1, seq);
			stat.setString(2, dto.getToeic());
			stat.setString(3, dto.getVolunteer());
			stat.setString(4, dto.getIntern());
			stat.setString(5, dto.getAwards());
			stat.setString(6, dto.getEtc());
			stat.setString(7, dto.getName());
			stat.registerOutParameter(8, OracleTypes.NUMBER);
			stat.executeQuery();

			return stat.getInt(8);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	/**
	 * 자기소개서의 페이지수를 반환
	 * 
	 * @param seq
	 *            해당개인의 번호
	 * @return 페이지수
	 */
	public int getIntroPage(String seq) {
		String sql = "select ceil((select count(*) from tblIntroduction " + "where jobseekerSeq = ?))/"
				+ EnumUtil.ONEPAGE + "+1 from dual";
		try {
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, seq);
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
	 * 이력서의 페이지수를 반환
	 * 
	 * @param seq
	 *            해당 개인의 번호
	 * @return 페이지수
	 */
	public int getResumePage(String seq) {
		String sql = "select ceil((select count(*) from tblResume where jobseekerSeq = ?))/" + EnumUtil.ONEPAGE
				+ "+1 from dual";
		try {
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, seq);
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
	 * 자기소개서를 DB에서 삭제해주는 메소드
	 * 
	 * @param introSeq
	 *            자기소개서번호
	 * @return 결과
	 */
	public int deleteIntroduction(String introSeq) {
		String sql = "delete from tblIntroduction where introSeq = ?";
		try {
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, introSeq);
			return stat.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 이력서를 DB에서 삭제해주는 메소드
	 * 
	 * @param resumeSeq
	 *            이력서 번호
	 * @return 결과
	 */
	public int deleteResume(String resumeSeq) {
		String sql = "delete from tblResume where resumeSeq = ?";
		try {
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, resumeSeq);
			return stat.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 대표문서에서 이력서를 변경하는 메소드
	 * 
	 * @param resumeSeq
	 *            이력서번호
	 * @return 결과
	 */
	public int updateRep(String resumeSeq) {
		String sql = "update tblRepresentation set resumeSeq = ? where jobSeekerSeq = ?";
		try {
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, resumeSeq);
			stat.setString(2, JobSeekerUtil.loginJobSeeker.getJobSeekerSeq());
			return stat.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	/**
	 * 대표문서에서 자기소개서를 변경하는 메소드
	 * 
	 * @param introSeq
	 *            자기소개서 번호
	 * @return 결과
	 */
	public int updateRepIntro(String introSeq) {
		String sql = "update tblRepresentation set introSeq = ? where jobSeekerSeq = ?";
		try {
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, introSeq);
			stat.setString(2, JobSeekerUtil.loginJobSeeker.getJobSeekerSeq());
			return stat.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 모든 채용공고 객체 리스트를 DB에서 가져오는 메소드,
	 * 
	 * @return 채용공고 객체 리스트
	 */
	public ArrayList<RecruitmentDTO> getAllRecruit() {
		String sql = "call procShowRecruit(?)";
		ArrayList<RecruitmentDTO> list = new ArrayList<RecruitmentDTO>();
		try {
			CallableStatement stat = conn.prepareCall(sql);
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.executeQuery();
			ResultSet rs = (ResultSet) stat.getObject(1);
			while (rs.next()) {
				RecruitmentDTO dto = new RecruitmentDTO();
				dto.setRegdate(rs.getString("등록일"));// 등록일
				dto.setJobName(rs.getString("직무명"));// 카테고리명
				dto.setCorpSeq(rs.getString("기업번호"));
				dto.setCorpName(rs.getString("기업명"));// 기업명
				dto.setEnddate(rs.getString("마감일"));
				dto.setNum(rs.getString("채용인원"));// 채용인원
				dto.setOtherDiscription(rs.getString("공고설명"));// 공고설명
				dto.setQualification(rs.getString("지원자격"));// 자격요건
				dto.setRecruitSeq(rs.getString("번호"));// 공고번호
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
	 * 개인의 대표 이력서 객체를 반환하는 메소드
	 * 
	 * @param jobSeekerSeq
	 *            개인번호
	 * @return 이력서객체
	 */
	public ResumeDTO getRepResume(String jobSeekerSeq) {
		String sql = "select rs.* from tblRepresentation r "
				+ "inner join tblResume rs on r.resumeSeq = rs.resumeSeq where r.jobseekerseq = ?";
		try {
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, jobSeekerSeq);
			ResultSet rs = stat.executeQuery();
			if (rs.next()) {
				ResumeDTO dto = new ResumeDTO();
				dto.setAwards(rs.getString("awards"));
				dto.setEtc(rs.getString("etc"));
				dto.setIntern(rs.getString("intern"));
				dto.setJobSeekerSeq(jobSeekerSeq);
				dto.setName(rs.getString("name"));
				dto.setResumeSeq(rs.getString("resumeSeq"));
				dto.setToeic(rs.getString("toeic"));
				dto.setVolunteer(rs.getString("volunteer"));
				return dto;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 개인의 대표 자기소개서를 반환하는 메소드
	 * 
	 * @param jobSeekerSeq
	 *            개인번호
	 * @return 자기소개서 객체
	 */
	public IntroductionDTO getRepIntro(String jobSeekerSeq) {
		String sql = "select i.* from tblRepresentation r inner join tblIntroduction i "
				+ " on r.introSeq = i.introSeq where r.jobseekerseq = ?";
		try {
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, jobSeekerSeq);
			ResultSet rs = stat.executeQuery();
			if (rs.next()) {
				IntroductionDTO dto = new IntroductionDTO();
				dto.setIntroSeq(rs.getString("introSeq"));
				dto.setJobSeekerSeq(jobSeekerSeq);
				dto.setName(rs.getString("name"));
				dto.setSelfIntroduction(rs.getString("selfIntroduction"));
				return dto;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 인기 채용공고 객체리스트 10개를 반환하는 메소드
	 * 
	 * @return 채용공고 리스트(인기)
	 */
	public ArrayList<RecruitmentDTO> getPopularRecruit() {
		String sql = "call procPopularRecruit(?)";
		ArrayList<RecruitmentDTO> list = new ArrayList<RecruitmentDTO>();
		try {
			CallableStatement stat = conn.prepareCall(sql);
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.executeQuery();
			ResultSet rs = (ResultSet) stat.getObject(1);
			while (rs.next()) {
				RecruitmentDTO dto = new RecruitmentDTO();
				dto.setRecruitSeq(rs.getString("공고번호"));
				dto.setCorpSeq(rs.getString("기업번호"));
				dto.setCorpName(rs.getString("기업명"));
				dto.setCategorySeq(rs.getString("직무번호"));
				dto.setJobName(rs.getString("직무명"));
				dto.setTitle(rs.getString("공고명"));
				dto.setNum(rs.getString("채용인원"));
				dto.setQualification(rs.getString("자격요건"));
				dto.setOtherDiscription(rs.getString("추가설명"));
				dto.setRegdate(rs.getString("등록일"));
				dto.setEnddate(rs.getString("마감일"));
				list.add(dto);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 해당 공고에 지원하는 메소드
	 * 
	 * @param recruitSeq
	 *            채용공고 번호
	 * @return 결과
	 */
	public int applyRecruit(String recruitSeq) {
		String sql = "call procApplyRecruit(?,?,?)";
		try {
			CallableStatement stat = conn.prepareCall(sql);
			stat.setString(1, JobSeekerUtil.myRepSeq);
			stat.setString(2, recruitSeq);
			stat.registerOutParameter(3, OracleTypes.NUMBER);
			stat.executeQuery();
			return stat.getInt(3);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	/**
	 * 개인의 대표문서의 번호를 반환하는 메소드
	 * 
	 * @param jobSeekerSeq
	 *            개인번호
	 * @return 대표문서번호
	 */
	public String getRepSeq(String jobSeekerSeq) {
		String sql = "select repSeq from tblRepresentation where jobseekerSeq = ?";
		try {
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, jobSeekerSeq);
			ResultSet rs = stat.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 개인이 관심기업으로 선정한 기업을 관심기업으로 추가하는 메소드
	 * 
	 * @param corpSeq
	 *            기업번호
	 * @return 결과
	 */
	public int enrollInterest(String corpSeq) {
		String sql = "insert into tblInerestedCorporation values(icSeq.nextval,?,?)";
		try {
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, JobSeekerUtil.loginJobSeeker.getJobSeekerSeq());
			stat.setString(2, corpSeq);
			return stat.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}
	
	/**
	 * 직무카테고리 객체리스트 반환
	 * @return 직무카테고리 객체리스트
	 */
	public ArrayList<JobCategoryDTO> getJobCategory() {
		String sql = "select * from tblJobCategory";
		try {
			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery(sql);
			ArrayList<JobCategoryDTO> list = new ArrayList<JobCategoryDTO>();
			while (rs.next()) {
				JobCategoryDTO dto = new JobCategoryDTO();
				dto.setCategorySeq(rs.getString("categorySeq"));
				dto.setJobName(rs.getString("jobName"));
				list.add(dto);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	/**
	 * 직무별 공고페이지수 반환
	 * @param seq 직무번호
	 * @return 공고페이지수
	 */
	public int getRecruitPageByJob(String seq) {
		String sql = "call procRecruitByJobPage(?,?)";
		try {
			CallableStatement stat = conn.prepareCall(sql);
			stat.setString(1, seq);
			stat.registerOutParameter(2, OracleTypes.NUMBER);
			stat.executeQuery();
			return stat.getInt(2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 직무별 공고 반환
	 * @param jobSeq 직무번호
	 * @return 동일직무 공고리스트
	 */
	public ArrayList<RecruitmentDTO> getRecruitByJob(String jobSeq) {
		String sql = "call procShowRecruitByJob(?,?)";
		try {
			CallableStatement stat = conn.prepareCall(sql);
			stat.setString(1, jobSeq);
			stat.registerOutParameter(2, OracleTypes.CURSOR);
			stat.executeQuery();
			ResultSet rs = (ResultSet) stat.getObject(2);
			ArrayList<RecruitmentDTO> list = new ArrayList<RecruitmentDTO>();
			while (rs.next()) {
				RecruitmentDTO dto = new RecruitmentDTO();
				dto.setTitle(rs.getString("공고제목"));
				dto.setCorpName(rs.getString("기업명"));
				dto.setJobName(rs.getString("직무명"));
				dto.setCategorySeq(rs.getString("직무번호"));
				dto.setQualification(rs.getString("지원자격"));
				dto.setRegdate(rs.getString("등록일"));
				dto.setEnddate(rs.getString("마감일"));
				dto.setOtherDiscription(rs.getString("공고설명"));
				dto.setNum(rs.getString("채용인원"));
				dto.setRecruitSeq(rs.getString("공고번호"));
				list.add(dto);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	/**
	 * 내가지원한 공고 리스트 반환 메소드
	 * @param jobSeekerSeq 개인번호
	 * @return 공고리스트
	 */
	public ArrayList<RecruitmentDTO> getMyApplyRecruit(String jobSeekerSeq) {
		String sql = "call procMyApplyRecruit(?,?)";
		try {
			CallableStatement stat = conn.prepareCall(sql);
			stat.setString(1, jobSeekerSeq);
			stat.registerOutParameter(2, OracleTypes.CURSOR);
			stat.executeQuery();
			ResultSet rs = (ResultSet) stat.getObject(2);
			ArrayList<RecruitmentDTO> list = new ArrayList<RecruitmentDTO>();
			while (rs.next()) {
				RecruitmentDTO dto = new RecruitmentDTO();
				dto.setCategorySeq(rs.getString("직무번호"));
				dto.setCorpName(rs.getString("기업명"));
				dto.setCorpSeq(rs.getString("기업번호"));
				dto.setEnddate(rs.getString("마감일"));
				dto.setJobName(rs.getString("직무명"));
				dto.setNum(rs.getString("채용인원"));
				dto.setOtherDiscription(rs.getString("공고설명"));
				dto.setQualification(rs.getString("지원자격"));
				dto.setRecruitSeq(rs.getString("공고번호"));
				dto.setRegdate(rs.getString("등록일"));
				dto.setTitle(rs.getString("공고제목"));
				list.add(dto);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	/**
	 * 내가지원한 공고의 페이지수
	 * @param jobSeekerSeq 개인번호
	 * @return 공고 페이지수
	 */
	public int getMyRecruitPage(String jobSeekerSeq) {
		String sql = "select fnGetApplyPage(?) from dual";
		try {
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, jobSeekerSeq);
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
	 * 현재 공고의 상태를 반환.
	 * @param recruitSeq 공고상태
	 * @return 상태
	 */
	public Object getRecruitState(String recruitSeq) {
		String sql = "select fnRecruitState(?) from dual";
		try {
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, recruitSeq);
			ResultSet rs = stat.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	/**
	 * 모든 게시판 게시물의 객체리스트를 반환
	 * @return 게시물 리스트
	 */
	public ArrayList<ReviewDTO> getAllReview() {
		ArrayList<ReviewDTO> list = new ArrayList<ReviewDTO>();
		String sql = "call procShowBoard(?)";
		try {
			CallableStatement stat = conn.prepareCall(sql);
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.executeQuery();
			ResultSet rs = (ResultSet) stat.getObject(1);
			while (rs.next()) {
				ReviewDTO dto = new ReviewDTO();
				dto.setContent(rs.getString("내용"));
				dto.setJobSeekerName(rs.getString("작성자"));
				dto.setJobSeekerSeq(rs.getString("작성자번호"));
				dto.setRegdate(rs.getString("등록일"));
				dto.setReviewSeq(rs.getString("글번호"));
				dto.setViews(rs.getString("조회수"));
				list.add(dto);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	/**
	 * 게시판 전체 페이지수 반환
	 * @return 페이지수
	 */
	public int getBoardPage() {
		String sql = "select fnBoardPage(?) from dual";
		try {
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setInt(1, EnumUtil.ONEPAGE);
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
	 * 게시물의 정보 반환
	 * @param reviewSeq 게시물번호
	 * @return 게시물객체
	 */
	public ReviewDTO getReview(String reviewSeq) {
		String sql = "call procRownumBoard(?,?)";
		try {
			CallableStatement stat = conn.prepareCall(sql);
			stat.setString(1, reviewSeq);
			stat.registerOutParameter(2, OracleTypes.CURSOR);
			stat.executeQuery();
			ResultSet rs = (ResultSet) stat.getObject(2);
			ReviewDTO dto = new ReviewDTO();
			if (rs.next()) {
				dto.setContent(rs.getString("내용"));
				dto.setJobSeekerName(rs.getString("작성자"));
				dto.setJobSeekerSeq(rs.getString("작성자번호"));
				dto.setRegdate(rs.getString("등록일"));
				dto.setReviewSeq(rs.getString("글번호"));
				dto.setViews(rs.getString("조회수"));
			}
			return dto;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	/**
	 * 게시물 추가 메소드
	 * @param content 추가할 게시물 내용
	 * @return 결과
	 */
	public int addReview(String content) {
		String sql = "call procAddReview(?,?,?)";
		try {
			CallableStatement stat = conn.prepareCall(sql);
			stat.setString(1, content);
			stat.setString(2, JobSeekerUtil.loginJobSeeker.getJobSeekerSeq());
			stat.registerOutParameter(3, OracleTypes.NUMBER);
			stat.executeQuery();
			return stat.getInt(3);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}
	/**
	 * 나의 게시물 반환 메소드
	 * @param jobSeekerSeq 개인번호
	 * @return 게시물리스트
	 */
	public ArrayList<ReviewDTO> getMyRecruit(String jobSeekerSeq) {
		String sql = "call procGetMyRecruit(?,?)";
		try {

			CallableStatement stat = conn.prepareCall(sql);
			stat.setString(1, jobSeekerSeq);
			stat.registerOutParameter(2, OracleTypes.CURSOR);
			stat.executeQuery();
			ArrayList<ReviewDTO> list = new ArrayList<ReviewDTO>();
			ResultSet rs = (ResultSet) stat.getObject(2);
			while (rs.next()) {
				ReviewDTO dto = new ReviewDTO();
				dto.setContent(rs.getString("내용"));
				dto.setJobSeekerName(rs.getString("작성자"));
				dto.setJobSeekerSeq(rs.getString("작성자번호"));
				dto.setRegdate(rs.getString("등록일"));
				dto.setReviewSeq(rs.getString("글번호"));
				dto.setViews(rs.getString("조회수"));
				list.add(dto);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 내게시물의 페이지수를 리턴해주는 메소드
	 * 
	 * @param jobSeekerSeq
	 *            작성자번호
	 * @return 페이지수
	 */
	public int getMyBoardPage(String jobSeekerSeq) {
		String sql = "select ceil(count(*)/10) as cnt from tblReview where jobseekerSeq = ?";
		try {
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, jobSeekerSeq);
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
	 * 해당 게시글의 수정된 내용을 DB에 최신화하는 메소드
	 * 
	 * @param reviewSeq
	 * @param content
	 * @return
	 */
	public int updateMyReview(String reviewSeq, String content) {
		String sql = "update tblReview set content = ? where reviewSeq = ?";
		try {
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, content);
			stat.setString(2, reviewSeq);
			return stat.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 게시글을 디비에서 지워주는 메소드
	 * 
	 * @param reviewSeq
	 *            게시글 번호
	 * @return 성공개수(여부)
	 */
	public int deleteReview(String reviewSeq) {
		String sql = "delete from tblReview where reviewSeq = ?";
		try {
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, reviewSeq);
			return stat.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 아이디 중복확인ㅁ ㅔ소드
	 * @param inputId 입력된 아이디
	 * @return 결과
	 */
	public int getIdCount(String inputId) {
		String sql = "select count(*) as cnt from tblJobSeekerLogin where id =?";
		try {
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, inputId);
			ResultSet rs = stat.executeQuery();
			if (rs.next()) {
				return rs.getInt("cnt");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 로그인 정보를 변경하기 위한 메소드 DB에 저장
	 * 
	 * @param id
	 *            아이디
	 * @param pw
	 *            비밀번호
	 * @return 결과
	 */
	public int loginChange(String id, String pw) {
		String sql = "update tblJobSeekerLogin set id = ?,pw = ? where jobseekerseq=?";
		try {
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, id);
			stat.setString(2, pw);
			stat.setString(3, JobSeekerUtil.loginJobSeeker.getJobSeekerSeq());
			return stat.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	/**
	 * 주민번호 뒷자리로 본인확인 하기 위한 메소드
	 * 
	 * @param ssn
	 *            주민번호 뒷자리
	 * @return 결과
	 */
	public int checkPersonal(String ssn) {
		String sql = "call procCheckPersonal(?,?)";
		try {
			CallableStatement stat = conn.prepareCall(sql);
			stat.setString(1, JobSeekerUtil.loginJobSeeker.getJobSeekerSeq());
			stat.registerOutParameter(2, OracleTypes.CURSOR);
			stat.executeQuery();
			ResultSet rs = (ResultSet) stat.getObject(2);
			if (rs.next()) {
				if (ssn.equals(rs.getString("뒷자리"))) {
					return 1;
				} else {
					return 0;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}
	
	/**
	 * 로그인한 개인의 정보를 수정
	 * @param input 선택변수
	 * @param content 수정할 내용
	 * @return 결과
	 */
	public int updatePersonalInfo(int input, String content) {
		String sql = "";
		if (input == 1) { // 이름변경
			sql = "update tbljobseeker set name = ? where jobseekerSeq = ?";
		} else if (input == 2) { // 주민변경
			sql = "update tbljobseeker set ssn = ? where jobseekerSeq = ?";
		} else if (input == 3) { // 전화번호
			sql = "update tbljobseeker set tel = ? where jobseekerSeq = ?";
		} else if (input == 4) { // 주소
			sql = "update tbljobseeker set address = ? where jobseekerSeq = ?";
		} else if (input == 5) { // 이메일
			sql = "update tbljobseeker set email = ? where jobseekerSeq = ?";
		}
		try {
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, content);
			stat.setString(2, JobSeekerUtil.loginJobSeeker.getJobSeekerSeq());
			return stat.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}
	
	/**
	 * 개인의 관심기업 목록을 반환
	 * @return 기업 객체리스트
	 */
	public ArrayList<CorporationDTO> getMyInterestCorp() {
		String sql = "call procGetMyInterestCorp(?,?)";
		try {
			CallableStatement stat = conn.prepareCall(sql);
			stat.setString(1, JobSeekerUtil.loginJobSeeker.getJobSeekerSeq());
			stat.registerOutParameter(2, OracleTypes.CURSOR);
			stat.executeQuery();
			ArrayList<CorporationDTO> list = new ArrayList<CorporationDTO>();
			ResultSet rs = (ResultSet) stat.getObject(2);
			while (rs.next()) {
				CorporationDTO dto = new CorporationDTO();
				dto.setAddress(rs.getString("주소"));
				dto.setCorpSeq(rs.getString("기업번호"));
				dto.setEmail(rs.getString("홈페이지"));
				dto.setEstablishDate(rs.getString("설립일"));
				dto.setName(rs.getString("기업명"));
				dto.setSsn(rs.getString("사업자번호"));
				dto.setTel(rs.getString("전화번호"));
				list.add(dto);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	/**
	 * 기업번호를 통해 해당기업의 객체를 반환
	 * @param corpSeq 기업번호
	 * @return 기업객체
	 */
	public CorporationDTO getCorporation(String corpSeq) {
		String sql = "call procGetCorporation(?,?)";
		try {
			CallableStatement stat = conn.prepareCall(sql);
			stat.setString(1, corpSeq);
			stat.registerOutParameter(2, OracleTypes.CURSOR);
			stat.executeQuery();
			ResultSet rs = (ResultSet)stat.getObject(2);
			if(rs.next()) {
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
	
	
	/**
	 * 해당기업이 공고를 모두 반환.
	 * @param corpSeq 기업번호
	 * @return 공고 객체 리스트
	 */
	public ArrayList<RecruitmentDTO> getCorpRecruit(String corpSeq) {
		String sql = "call procGetICRecruit(?,?)";
		try {
			CallableStatement stat = conn.prepareCall(sql);
			stat.setString(1, corpSeq);
			stat.registerOutParameter(2, OracleTypes.CURSOR);
			stat.executeQuery();
			ResultSet rs = (ResultSet)stat.getObject(2);
			ArrayList<RecruitmentDTO> list = new ArrayList<RecruitmentDTO>();
			while(rs.next()) {
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
	 * 지원자수 구하는 메소드
	 * @param recruitSeq 공고번호
	 * @return 지원자수
	 */
	public String getApplyNumber(String recruitSeq) {
		String sql = "call procApplyNumber(?,?)";
		try {
			CallableStatement stat = conn.prepareCall(sql);
			stat.setString(1, recruitSeq);
			stat.registerOutParameter(2, OracleTypes.NUMBER);
			stat.executeQuery();
			return stat.getString(2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 관심기업 삭제 메소드
	 * @param corpSeq 기업번호
	 * @return 결과
	 */
	public int deleteIC(String corpSeq) {
		String sql = "delete from tblInerestedCorporation where jobseekerseq = ? and corpSeq =?";
		
		try {
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, JobSeekerUtil.loginJobSeeker.getJobSeekerSeq());
			stat.setString(2, corpSeq);
			return stat.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	/**
	 * 0이면 둘중하나가 없는거. 1이면 둘다 등록되있는것.
	 * @return
	 */
	public int getRepresentation() {
		String sql  = "call procGetIsRep(?,?) ";
		
		try {
			CallableStatement stat = conn.prepareCall(sql);
			stat.setString(1, JobSeekerUtil.loginJobSeeker.getJobSeekerSeq());
			stat.registerOutParameter(2, OracleTypes.NUMBER);
			stat.executeQuery();
			return stat.getInt(2);
		} catch (Exception e) {
			System.out.println("JobSeekerDAO.getRepresentation :" + e.toString());
		}
		
		return 0;
	}
	
	/**
	 * 대표문서 공개 메소드
	 * @return 결과
	 */
	public int setOpenRep() {
		String sql = "update tblRepresentation set Visibility = 1 where jobseekerSeq = ?";
		try {
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, JobSeekerUtil.loginJobSeeker.getJobSeekerSeq());
			return stat.executeUpdate();
		} catch (Exception e) {
			System.out.println("JobSeekerDAO.setOpenRep :" + e.toString());
		}
		
		return 0;
	}
		
	/**
	 * 대표문서 비공개 메소드
	 * @return 결과
	 */
	public int setClpseRep() {
		String sql = "update tblRepresentation set Visibility = 0 where jobseekerSeq = ?";
		try {
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, JobSeekerUtil.loginJobSeeker.getJobSeekerSeq());
			return stat.executeUpdate();
		} catch (Exception e) {
			System.out.println("JobSeekerDAO.setOpenRep :" + e.toString());
		}
		
		return 0;
	}

	public ArrayList<String> getRestrictLetter() {
		String sql = "select  * from tblRestrict";
		try {
			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery(sql);
			ArrayList<String> list = new ArrayList<String>();
			while(rs.next()) {
				list.add(rs.getString("restrictLetter"));
			}
			return list;
		} catch (Exception e) {
			System.out.println("JobSeekerDAO.getRestrictLetter :" + e.toString());
		}
		return null;
	}

}























