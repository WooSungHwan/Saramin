package saram.in.saramin.jobseeker;

import java.util.ArrayList;
import java.util.Iterator;

import saram.in.saramin.admin.ReviewDTO;
import saram.in.saramin.corporation.CorporationDTO;
import saram.in.saramin.corporation.RecruitmentDTO;
import saram.in.saramin.util.EnumUtil;
import saram.in.saramin.util.JobSeekerUtil;
import saram.in.saramin.util.MyPrint;
import saram.in.saramin.util.MyScanner;

/**
 * 개인회원의 기능들을 포함하는 클래스
 * @author woo
 *
 */
public class JobSeekerController {
	private MyPrint out;
	private MyScanner sc;
	private JobSeekerDAO jdao;
	private String title;
	private String header[];

	public JobSeekerController() {
		out = new MyPrint();
		sc = new MyScanner();
		jdao = new JobSeekerDAO();
		getUtil();
	}
	
	/**
	 * 로그인한 개인의 이력서,자기소개서,대표이력서,대표문서의 객체를 생성.
	 */
	private void getUtil() {
		if (JobSeekerUtil.loginJobSeeker != null) {
			JobSeekerUtil.ResumeList = jdao.getResumeFromSeekerSeq(JobSeekerUtil.loginJobSeeker.getJobSeekerSeq());
			JobSeekerUtil.introList = jdao.getIntroFromSeekerSeq(JobSeekerUtil.loginJobSeeker.getJobSeekerSeq());
			JobSeekerUtil.myRepResume = jdao.getRepResume(JobSeekerUtil.loginJobSeeker.getJobSeekerSeq());
			JobSeekerUtil.myRepIntroduction = jdao.getRepIntro(JobSeekerUtil.loginJobSeeker.getJobSeekerSeq());
			JobSeekerUtil.myRepSeq = jdao.getRepSeq(JobSeekerUtil.loginJobSeeker.getJobSeekerSeq());
		}
	}
	/**
	 * 개인회원의 메인 분기문.
	 */
	public void jobSeekerMain() {
		while (true) {
			out.title("개인 회원");
			out.menu(new String[] { "이력서 관리", "자기소개서 관리", "채용공고", "후기게시판", "개인정보 변경", "관심기업 관리","대표문서 공개설정" ,"로그아웃" });// 1,2,3,4,5,6,0
			int input = sc.nextInt("선택");
			if (input == 8) {// 로그아웃
				JobSeekerUtil.loginJobSeeker = null;
				JobSeekerUtil.introList = null;
				JobSeekerUtil.ResumeList = null;
				return;
			} else if (input == 1) { // 이력서관리
				resumeManage();
			} else if (input == 2) { // 자기소개서 관리
				introManage();
			} else if (input == 3) { // 채용공고
				recruitManage();
			} else if (input == 4) { // 후기게시판
				board();
			} else if (input == 5) { // 개인정보 변경
				myInfoChange();
			} else if (input == 6) { // 관심기업 관리
				interestCorp();
			} else if(input==7) { //대표 문서 공개설정
				repVisiblility();
			}
			
			else {
				out.result("잘못입력하셨습니다. 신중히 입력해주시기 바랍니다.");
			}
		}
	}

	/**
	 * 로그인한 개인의 대표문서 공개여부
	 */
	private void repVisiblility() {
		//로그인한 객체가 이력서 공개가 되어있는가 공개1 비공개0
		title="대표문서 공개설정";
		out.title(title);
		out.menu(new String[] {
				"대표문서 공개","대표문서 비공개","돌아가기"
		});
		int input = sc.nextInt("선택");
		if(input ==1) {//공개
			int result = jdao.getRepresentation();
			if(result==1) {
				out.line();
				String sel = sc.next("\t\t대표이력서와 대표자기소개서를 공개설정하시겠습니까?(y/n)");
				out.line();
				if(sel.equals("y")) {
					result = jdao.setOpenRep();
					out.result(result, "해당 대표문서를 공개설정하였습니다.");
				}else {
					return;
				}
			}else {
				out.result("아직 등록되지않은 이력서 혹은 자기소개서가 존재합니다. 등록후 진행해주세요.");
			}
		}else if(input==2){//비공개
			out.line();
			String sel = sc.next("\t\t대표이력서와 대표자기소개서를 비공개설정하시겠습니까?(y/n)");
			out.line();
			if(sel.equals("y")) {
				int result=jdao.setClpseRep();
				out.result(result, "해당 대표문서를 비공개설정하였습니다.");
			}else {
				return;
			}
		}else if(input==3) {//돌아가기
			return;
		}else {
			out.result("잘못입력하셨습니다. 신중히 입력해주시기 바랍니다.");
			return;
		}
		
		
	}

	/**
	 * 관심기업을 관리하는 메소드
	 */
	private void interestCorp() {
		title = "관심기업 관리";
		while (true) {
			out.title(title);
			out.menu(new String[] { "관심기업 조회", "관심기업 채용공고 조회", "관심기업 삭제", "뒤로 가기" });
			int input = 0;
			try {
				input = sc.nextInt("선택");
			} catch (Exception e) {
				out.result("입력오류가 발생하였습니다. 다시 진행해주시기 바랍니다.");
				continue;
			}

			if (input == 1) { // 관심기업 조회
				title = "관심기업 조회";
				out.title(title);
				ArrayList<CorporationDTO> list = jdao.getMyInterestCorp();
				int maxPage = (int) (Math.ceil(list.size() / 10) + 1);

				ArrayList<Object[]> data = new ArrayList<Object[]>();
				header = new String[] { "번호", "기업명" };
				for (CorporationDTO dto : list) {
					data.add(new Object[] { dto.getCorpSeq(), dto.getName() });
				}
				paging(title, header, maxPage, data);
				out.line();
				String corpSeq = sc.next("번호");
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).getCorpSeq().equals(corpSeq)) {
						break;
					}
					if (i == list.size()) {
						out.result("번호를 잘못입력하셨습니다. 다시 진행해주시기 바랍니다.");
						continue;
					}
				}

				CorporationDTO dto = jdao.getCorporation(corpSeq);
				out.showCorpInfo(dto);
				out.pause();
			} else if (input == 2) { // 관심기업 채용공고 조회
				title = "관심기업 채용공고 조회";
				out.title(title);
				ArrayList<CorporationDTO> list = jdao.getMyInterestCorp();
				int maxPage = (int) (Math.ceil(list.size() / 10) + 1);

				ArrayList<Object[]> data = new ArrayList<Object[]>();
				header = new String[] { "번호", "기업명" };
				for (CorporationDTO dto : list) {
					data.add(new Object[] { dto.getCorpSeq(), dto.getName() });
				}
				paging(title, header, maxPage, data);
				out.line();
				String corpSeq = sc.next("번호");
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).getCorpSeq().equals(corpSeq)) {
						break;
					}
					if (i == list.size()) {
						out.result("번호를 잘못입력하셨습니다. 다시 진행해주시기 바랍니다.");
						continue;
					}
				}
				ArrayList<RecruitmentDTO> rlist = jdao.getCorpRecruit(corpSeq);
				data.clear();
				for (RecruitmentDTO dto : rlist) {
					data.add(new Object[] { dto.getRecruitSeq(), dto.getTitle() });
				}
				maxPage = (int) (Math.ceil(rlist.size() / 10) + 1);
				paging(title, new String[] { "번호", "공고제목" }, maxPage, data);
				String recruitSeq = sc.next("번호");
				for (int i = 0; i < rlist.size(); i++) {
					if (rlist.get(i).getRecruitSeq().equals(recruitSeq)) {
						out.title(title);
						System.out.println("[현재 지원한 인원] : " + jdao.getApplyNumber(recruitSeq) + "명");
						out.line();
						out.showRecruit(rlist.get(i));
						out.pause();
						continue;
					}
				}
			} else if (input == 3) { // 관심기업 삭제
				deleteIC();

			} else if (input == 4) { // 뒤로가기
				return;
			} else {
				out.result("입력오류가 발생하였습니다. 다시 진행해주시기 바랍니다.");
				continue;
			}

		}

	}
	/**
	 * 관심기업 삭제하는 메소드
	 */
	private void deleteIC() {
		title = "관심기업 삭제";
		out.title(title);
		ArrayList<CorporationDTO> list = jdao.getMyInterestCorp();
		int maxPage = (int) (Math.ceil(list.size() / 10) + 1);
		ArrayList<Object[]> data = new ArrayList<Object[]>();
		header = new String[] { "번호", "기업명" };
		for (CorporationDTO dto : list) {
			data.add(new Object[] { dto.getCorpSeq(), dto.getName() });
		}
		paging(title, header, maxPage, data);
		out.line();
		String corpSeq = sc.next("번호");
		int result = jdao.deleteIC(corpSeq);
		out.result(result, "▶ 관심기업에서 삭제되었습니다. 계속하시려면 엔터키를 눌러주세요. ◀");
	}
	
	/**
	 * 개인정보를 변경하는 메소드
	 */
	private void myInfoChange() {
		while (true) {
			title = "개인정보 변경";
			out.title(title);
			out.menu(new String[] { "로그인 정보변경", "신상정보 변경", "뒤로 가기" });
			int input = 0;
			try {
				input = sc.nextInt("선택");
			} catch (Exception e) {
				out.result("입력오류가 발생하였습니다. 다시 진행해주시기 바랍니다.");
				continue;
			}
			if (input == 1) { // 로그인 정보 변경
				loginChange();
			} else if (input == 2) { // 신상 정보 변경
				personalInfo();
			} else if (input == 3) { // 뒤로가기
				return;
			} else {
				out.result("잘못입력하셨습니다. 신중히 입력해주시기 바랍니다.");
			}
		}

	}

	/**
	 * 신상정보 변경 메소드
	 */
	private void personalInfo() {
		title = "신상정보 변경";
		out.title(title);
		out.menu(new String[] { "성명", "주민등록번호", "전화번호", "주소", "이메일", "돌아가기" });
		int input = 0;
		String content = "";
		try {
			input = sc.nextInt("선택");
			if (input == 6) {
				return;
			} else if (input <= 0 || input > 6) {
				return;
			}
			out.line();
			content = sc.next("변경 내용");
			out.eline();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String ssn = sc.nextln("본인확인을 위해 주민번호 뒷자리를 입력해주세요");
		int result = jdao.checkPersonal(ssn);
		result = result * jdao.updatePersonalInfo(input, content);
		if (input == 1) {
			JobSeekerUtil.loginJobSeeker.setName(content);
		} else if (input == 2) {
			JobSeekerUtil.loginJobSeeker.setSsn(content);
		} else if (input == 3) {
			JobSeekerUtil.loginJobSeeker.setTel(content);
		} else if (input == 4) {
			JobSeekerUtil.loginJobSeeker.setAddress(content);
		} else if (input == 5) {
			JobSeekerUtil.loginJobSeeker.setEmail(content);
		}
		out.result(result, "▶ 신상 정보가 수정되었습니다. 계속하시려면 엔터키를 눌러주세요. ◀");
	}

	/**
	 * 로그인정보 변경 메소드
	 */
	private void loginChange() {
		title = "로그인 정보 변경";
		out.title(title);
		String inputId = "";
		try {
			inputId = sc.next("현재 아이디");
		} catch (Exception e) {
			out.result("입력오류가 발생하였습니다. 다시 진행해주시기 바랍니다.");
		}
		int result = jdao.getIdCount(inputId);
		if (result == 1) {// 로그인 정보 1개 => 유효
			String id = sc.next("변경할 아이디");
			String pw = sc.next("변경할 비밀번호");
			result = jdao.loginChange(id, pw);
			String ssn = sc.nextln("본인확인을 위해 주민번호 뒷자리를 입력해주세요");
			result = result * jdao.checkPersonal(ssn);
			out.result(result, "▶ 로그인 정보가 수정되었습니다. 계속하시려면 엔터키를 눌러주세요. ◀");
			return;
		} else if (result == 0) { // 로그인 정보 0개 -> 없음.
			out.result(result, "아이디를 잘못입력하셨거나 해당 정보가 없습니다.");
			return;
		}

	}
	
	/**
	 * 후기게시판의 메인 흐름문
	 */
	protected void board() {
		title = "후기게시판";
		while (true) {
			out.title(title);
			out.menu(new String[] { "전체 게시글 조회", "게시글 작성", "게시글 수정", "게시글 삭제", "돌아가기" });
			int input = sc.nextInt("선택");
			if (input == 1) { // 전체게시글 조회
				allBoard();
			} else if (input == 2) { // 게시글 작성
				addBoard();
			} else if (input == 3) { // 게시글 수정
				updateBoard();
			} else if (input == 4) { // 게시글 삭제
				deleteBoard();
			} else if (input == 5) { // 돌아가기
				return;
			} else {
				out.result("잘못입력하셨습니다. 신중히 입력해주시기 바랍니다.");
			}
		}
	}

	/**
	 * 게시판 글 삭제하는 메소드
	 */
	private void deleteBoard() {
		title = "게시글 삭제";
		out.title(title);
		header = new String[] { "[글번호]", "[작성자]", "[조회수]", "[글제목]" };
		int maxPage = jdao.getMyBoardPage(JobSeekerUtil.loginJobSeeker.getJobSeekerSeq());
		ArrayList<ReviewDTO> list = jdao.getMyRecruit(JobSeekerUtil.loginJobSeeker.getJobSeekerSeq());
		ArrayList<Object[]> data = new ArrayList<Object[]>();
		for (int i = 0; i < list.size(); i++) {
			String content = list.get(i).getContent().substring(0, 3) + "...";
			data.add(new Object[] { list.get(i).getReviewSeq(), list.get(i).getJobSeekerName(), list.get(i).getViews(),
					content });
		}
		paging(title, header, maxPage, data);
		String reviewSeq = sc.next("번호");
		ReviewDTO rdto = jdao.getReview(reviewSeq);
		out.showReview(rdto);
		String answer = sc.next("해당 게시글을 삭제하시겠습니까?(y/n)");
		if (answer.equals("y")) { // 지움
			int result = jdao.deleteReview(reviewSeq);
			out.result(result, "▶ 해당 게시글이 삭제되었습니다. 계속하시려면 엔터키를 눌러주세요. ◀");
		} else if (answer.equals("n")) { // 안지움
			out.pause();
		} else {
			out.result("잘못 입력하셨습니다. 다시 진행해주시기 바랍니다.");
		}

	}
	/**
	 * 게시글 수정 메소드
	 */
	private void updateBoard() {
		title = "게시글 수정";
		out.title(title);
		header = new String[] { "[글번호]", "[작성자]", "[조회수]", "[글제목]" };
		int maxPage = jdao.getMyBoardPage(JobSeekerUtil.loginJobSeeker.getJobSeekerSeq());
		ArrayList<ReviewDTO> list = jdao.getMyRecruit(JobSeekerUtil.loginJobSeeker.getJobSeekerSeq());
		ArrayList<Object[]> data = new ArrayList<Object[]>();
		for (int i = 0; i < list.size(); i++) {
			String content = list.get(i).getContent().substring(0, 8) + "...";
			data.add(new Object[] { list.get(i).getReviewSeq(), list.get(i).getJobSeekerName(), list.get(i).getViews(),
					content });
		}
		paging(title, header, maxPage, data);
		String reviewSeq = sc.next("번호");
		ReviewDTO rdto = jdao.getReview(reviewSeq);
		out.title(title);
		out.showReview(rdto);
		String content = sc.next("수정할 내용 입력");
		int result = jdao.updateMyReview(reviewSeq, content);
		out.result(result, "▶ 해당 게시글이 수정되었습니다. 계속하시려면 엔터키를 눌러주세요. ◀");
	}
	
	/**
	 * 게시글 작성 메소드
	 */
	private void addBoard() {
		while (true) {
			title = "게시글 작성";
			out.title(title);
			String content = sc.next("내용을 입력하세요");
			if (content.length() < 8) {
				out.result("최소 8자 이상 입력해주시기 바랍니다.");
				continue;
			}
			int result = jdao.addReview(content);
			out.result(result, "▶ 게시글을 작성하였습니다. 계속하시려면 엔터키를 눌러주세요. ◀");
			break;
		}
	}
	/**
	 * 전체 게시글 조회 메소드
	 */
	protected void allBoard() {
		title = "전체 게시글 조회";
		header = new String[] { "[글번호]", "[작성자]", "[조회수]", "[글제목]" };
		int maxPage = jdao.getBoardPage();
		ArrayList<ReviewDTO> list = jdao.getAllReview();
		ArrayList<Object[]> olist = new ArrayList<Object[]>();
		for (ReviewDTO data : list) {
			
			olist.add(new Object[] { data.getReviewSeq(), "  ",data.getJobSeekerName(),"   " ,data.getViews(),
					"   ",data.getContent().substring(0, 5) + ".." });
		}
		paging(title, header, maxPage, olist);
		String reviewSeq = sc.next("글번호");
		ReviewDTO dto = jdao.getReview(reviewSeq);
		out.title(title);
		out.showReview(dto);
		out.pause();
	}
	
	/**
	 * 채용공고 관리 주 흐름메소드
	 */
	private void recruitManage() {
		while (true) {
			out.title("채용 공고");
			out.menu(new String[] { "모든 채용공고", "인기 채용공고", "직무별 채용공고", "현재 지원한 채용공고", "돌아가기" });
			int input = sc.nextInt("선택");
			if (input == 1) { // 모든 채용공고
				allRecruit();
			} else if (input == 2) { // 인기 채용공고
				popularRecruit();
			} else if (input == 3) { // 직무별 채용공고
				jobByRecruit();
			} else if (input == 4) { // 현재 지원한 채용공고
				myApplyRecruit();
			} else if (input == 5) { // 돌아가기
				break;
			} else {
				out.result("잘못입력하셨습니다. 신중히 입력해주시기 바랍니다.");
			}
		}
	}
	
	/**
	 * 현재 개인이 지원한 공고를 확인하는 메소드
	 */
	private void myApplyRecruit() {
		title = "현재 지원한 공고";
		header = new String[] { "[번호]", "[상태]", "[공고제목]" };
		int maxPage = jdao.getMyRecruitPage(JobSeekerUtil.loginJobSeeker.getJobSeekerSeq());

		while (true) {
			ArrayList<RecruitmentDTO> list = jdao.getMyApplyRecruit(JobSeekerUtil.loginJobSeeker.getJobSeekerSeq());
			ArrayList<Object[]> data = new ArrayList<Object[]>();

			for (RecruitmentDTO dto : list) {
				data.add(new Object[] { dto.getRecruitSeq(), jdao.getRecruitState(dto.getRecruitSeq()),
						dto.getTitle() });
			}
			paging(title, header, maxPage, data);
			String recruitSeq = "";
			try {
				recruitSeq = sc.next("공고번호");
				boolean init = false;
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).getRecruitSeq().equals(recruitSeq)) {
						init = true;
						break;
					}
				}
				if (!init) {
					out.result("▶해당 공고에 지원하지 않으셨습니다. 번호를 다시 입력해주세요.◀");
					out.pause();
					continue;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			for (int i = 0; i < list.size(); i++) {
				if (recruitSeq.equals(list.get(i).getRecruitSeq())) {
					out.title(title);
					out.showRecruit(list.get(i));
					out.pause();
					return;
				}
			}
		}
	}
	
	/**
	 * 직무별 공고에 대한 메소드
	 */
	private void jobByRecruit() {
		String[] header = new String[] { "번호", "직무명" };
		ArrayList<JobCategoryDTO> list = jdao.getJobCategory();
		int maxPage = (int) Math.ceil(list.size() / EnumUtil.ONEPAGE) + 1;
		ArrayList<Object[]> data = new ArrayList<Object[]>();
		for (int i = 0; i < list.size(); i++) {
			data.add(new Object[] { list.get(i).getCategorySeq(), list.get(i).getJobName() });
		}
		paging("직무 선택", header, maxPage, data);
		out.line();
		String jobSeq = sc.next("번호");
		out.line();
		maxPage = jdao.getRecruitPageByJob(jobSeq);// jobSeq임

		header = new String[] { "번호", "공고제목" };

		ArrayList<RecruitmentDTO> rlist = jdao.getRecruitByJob(jobSeq);
		data.clear();
		for (int i = 0; i < rlist.size(); i++) {
			data.add(new Object[] { rlist.get(i).getRecruitSeq(), rlist.get(i).getTitle() });
		}
		paging("직무별 채용공고", header, maxPage, data);
		String Recruitseq = sc.next("번호");
		out.title("직무별 채용공고");
		for (int i = 0; i < rlist.size(); i++) {
			if (rlist.get(i).getRecruitSeq().equals(Recruitseq)) {
				out.showRecruit(rlist.get(i));
				ApplyOrInterest(Recruitseq, rlist.get(i).getCorpSeq());
				break;
			}
		}
	}
	
	/**
	 * Object배열 컬렉션을 페이징 처리하는 메소드
	 * @param title 제목
	 * @param header 헤더
	 * @param maxPage 최대페이지
	 * @param data 해당Object배열
	 */
	public void paging(String title, String[] header, int maxPage, ArrayList<Object[]> data) {
		while (true) {
			out.title(title);
			out.header(header);
			// ArrayList<RecruitmentDTO> list = jdao.getAllRecruit();
			// int maxPage = (int) Math.ceil(list.size() / EnumUtil.ONEPAGE);
			for (int i = 0; i < EnumUtil.ONEPAGE; i++) {
				try {
					out.data(data.get(i));
				} catch (Exception e) {
					break;
				}
			} // for
			out.line();
			System.out.println("\t\t\t" + EnumUtil.FIRSTPAGE + "/" + maxPage);
			out.line();
			while (true) {
				int page = sc.nextInt("페이지(다음단계 : 0)");
				if (page > maxPage) {
					out.pause("페이지의 범위를 초과하셨습니다. 계속하시려면 엔터키를 눌러주세요.");
					continue;
				}
				if (page == 0) {
					return;
				}
				int last = page * EnumUtil.ONEPAGE;
				int first = last - EnumUtil.ONEPAGE;
				out.title("모든 채용공고 조회");
				out.header(header);
				for (int i = first; i < last; i++) {
					try {
						out.data(data.get(i));
					} catch (Exception e) {
						break;
					}
				}
				out.eline();
				System.out.println("\t\t\t" + page + "/" + maxPage);
				out.eline();
			}
		}

	}
	/**
	 * 모든 공고내용 조회메소드
	 */
	private void allRecruit() {
		while (true) {
			out.title("모든 채용공고 조회");
			String[] header = new String[] { "번호", "공고제목" };
			out.header(header);
			ArrayList<RecruitmentDTO> list = jdao.getAllRecruit();
			int maxPage = (int) Math.ceil(list.size() / EnumUtil.ONEPAGE);
			for (int i = 0; i < EnumUtil.ONEPAGE; i++) {
				try {
					out.data(new Object[] { list.get(i).getRecruitSeq(), list.get(i).getTitle() });
				} catch (Exception e) {
					break;
				}
			} // for
			out.line();
			System.out.println("\t\t\t" + EnumUtil.FIRSTPAGE + "/" + maxPage);
			out.line();
			while (true) {
				int page = sc.nextInt("페이지(다음단계 : 0)");
				if (page > maxPage) {
					out.pause("페이지의 범위를 초과하셨습니다. 계속하시려면 엔터키를 눌러주세요.");
					continue;
				}
				if (page == 0) {
					break;
				}
				int last = page * EnumUtil.ONEPAGE;
				int first = last - EnumUtil.ONEPAGE;
				out.title("모든 채용공고 조회");
				out.header(header);
				for (int i = first; i < last; i++) {
					try {
						out.data(new Object[] { list.get(i).getRecruitSeq(), list.get(i).getTitle() });
					} catch (Exception e) {
						break;
					}
				}
				out.eline();
				System.out.println("\t\t\t" + page + "/" + maxPage);
				out.eline();
			}
			String seq = sc.next("번호");
			out.title("모든 채용공고 조회");
			Iterator<RecruitmentDTO> iter = list.iterator();
			while (iter.hasNext()) {
				RecruitmentDTO dto = iter.next();
				if (dto.getRecruitSeq().equals(seq)) {
					out.showRecruit(dto);
					// 공고지원? 관심기업 등록?
					ApplyOrInterest(dto.getRecruitSeq(), dto.getCorpSeq());
					break;
				}
			}

			break;
		} // while
	}
	/**
	 * 인기 채용공고 조회 메소드
	 */
	private void popularRecruit() {

		out.title("인기 채용공고");
		out.header(new String[] { "번호", "공고제목" });
		ArrayList<RecruitmentDTO> list = jdao.getPopularRecruit();
		Iterator<RecruitmentDTO> iter = list.iterator();
		while (iter.hasNext()) {
			RecruitmentDTO dto = iter.next();
			out.data(new Object[] { dto.getRecruitSeq(), dto.getTitle() });
		} // while
		out.line();
		String recruitSeq = sc.next("번호");
		out.title("인기 채용공고 조회");
		Iterator<RecruitmentDTO> iter2 = list.iterator();
		while (iter2.hasNext()) {
			RecruitmentDTO dto = iter2.next();
			if (dto.getRecruitSeq().equals(recruitSeq)) {
				out.showRecruit(dto);
				ApplyOrInterest(dto.getRecruitSeq(), dto.getCorpSeq());
				break;
			}
		} // while
	}
	/**
	 * 공고에 대해 지원 또는 관심기업으로 등록하는 메소드
	 * @param recruitSeq 공고번호
	 * @param corpSeq 기업번호
	 */
	private void ApplyOrInterest(String recruitSeq, String corpSeq) {
		out.menu(new String[] { "공고 지원", "관심기업으로 등록", "돌아가기" });
		int input = 0;
		try {
			input = sc.nextInt("선택");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (input == 1) {// 공고 지원
			int result = jdao.applyRecruit(recruitSeq);
			out.result(result, "▶ 해당 공고에 지원하였습니다. 계속하시려면 엔터키를 눌러주세요. ◀ ");
		} else if (input == 2) {// 관심기업 등록
			int result = jdao.enrollInterest(corpSeq);
			out.result(result, "▶ 관심기업으로 등록하였습니다. 계속하시려면 엔터키를 눌러주세요. ◀ ");
		} else if (input == 3) {// 돌아가기
			return;
		}

	}
	/**
	 * 자기소개서 관리 메인 흐름문
	 */
	private void introManage() {
		while (true) {
			out.title("자기소개서 관리");
			out.menu(new String[] { "새 자기소개서 작성", "내 자기소개서 조회", "자기소개서 삭제", "자기소개서 수정", "대표자기소개서 설정", "돌아가기" });
			int input = sc.nextInt("선택");
			if (input == 1) { // 새 자기소개서 작성
				addIntroduction();
			} else if (input == 2) { // 내 자기소개서 조회
				showIntroduction(input);
			} else if (input == 3) { // 자기소개서 삭제
				deleteIntroduction();
			} else if (input == 4) { // 자기소개서 수정
				updateIntro();
			} else if (input == 5) { // 대표자기소개서 설정
				setRepIntro();
			} else if (input == 6) { // 돌아가기
				break;
			} else {
				out.result("잘못입력하셨습니다. 신중히 입력해주시기 바랍니다.");
			}

		}
	}
	/**
	 * 이력서관리 메인 흐름문
	 */
	private void resumeManage() {
		while (true) {
			out.title("이력서 관리");
			out.menu(new String[] { "새 이력서 작성", "내 이력서 조회", "이력서 삭제", "이력서 수정", "대표이력서 설정" ,"돌아가기" });// 1,2,3,4,5,6
			int input = sc.nextInt("선택");

			if (input == 1) { // 새 이력서 작성
				addResume();
			} else if (input == 2) { // 이력서 조회
				showResume(input);
			} else if (input == 3) { // 이력서 삭제
				deleteResume();
			} else if (input == 4) { // 이력서 수정
				updateResume();
			} else if (input == 5) { // 대표이력서 설정
				setRepResume();
			} else if (input == 6) { // 돌아가기
				break;
			} else {
				out.result("잘못입력하셨습니다. 신중히 입력해주시기 바랍니다.");
				continue;
			}
		}
	}
	/**
	 * 대표자기소개서로 설정
	 */
	private void setRepIntro() {
		showIntroduction(-1);
		out.line();
		String introSeq = sc.next("번호");
		int result = jdao.updateRepIntro(introSeq);
		out.result(result, "▶ 대표자기소개서로 설정하였습니다. 계속하시려면 엔터키를 눌러주세요. ◀");
	}
	/**
	 * 대표이력서로 설정
	 */
	private void setRepResume() {
		showResume(-1);// 무의미한 숫자
		out.line();
		String resumeSeq = sc.next("번호");
		int result = jdao.updateRep(resumeSeq);
		out.result(result, "▶ 대표이력서로 설정하였습니다. 계속하시려면 엔터키를 눌러주세요. ◀");
	}

	/**
	 * 이력서 수정메소드
	 */
	private void updateResume() {
		deleteResume();
		addResume();

	}

	/**
	 * 자기소개서 수정메소드
	 */
	private void updateIntro() {
		deleteIntroduction();
		addIntroduction();
	}
	
	/**
	 * 자기소개서 삭제 메소드
	 */
	private void deleteIntroduction() {
		showIntroduction(-1);
		out.title("자기소개서 삭제");
		String introSeq = sc.next("번호");
		ArrayList<IntroductionDTO> list = JobSeekerUtil.introList;
		for (int i = 0; i < list.size(); i++) {
			IntroductionDTO dto = list.get(i);
			if (dto.getIntroSeq().equals(introSeq)) {
				out.showIntroduction(dto);
				String input = sc.next("해당이력서를 삭제하시겠습니까?(y/n)");
				if (input.equals("y")) {
					list.remove(i);
					int result = jdao.deleteIntroduction(introSeq);
					out.result(result, "▶ 자기소개서를 삭제하였습니다. 계속하시려면 엔터키를 눌러주세요. ◀");
				} else {
					return;
				}
			}
		}
	}
	
	/**
	 * 이력서 삭제 메소드
	 */
	private void deleteResume() {
		showResume(-1); // 숫자 의미없음
		String resumeSeq = sc.next("번호");
		ArrayList<ResumeDTO> list = JobSeekerUtil.ResumeList;
		for (int i = 0; i < list.size(); i++) {
			ResumeDTO dto = list.get(i);
			if (dto.getResumeSeq().equals(resumeSeq)) {
				out.showResume(dto);
				String input = sc.next("해당이력서를 삭제하시겠습니까?(y/n)");
				if (input.equals("y")) {
					list.remove(i);
					int result = jdao.deleteResume(resumeSeq);
					out.result(result, "▶ 이력서를 삭제하였습니다. 계속하시려면 엔터키를 눌러주세요. ◀");
				} else {
					return;
				}
			}
		}

	}
	
	/**
	 * 자기소개서 조회 메소드
	 * @param input 선택변수
	 */
	private void showIntroduction(int input) {
		while (true) {
			out.title("자기소개서 조회");
			ArrayList<IntroductionDTO> list = JobSeekerUtil.introList;
			int maxPage = jdao.getIntroPage(JobSeekerUtil.loginJobSeeker.getJobSeekerSeq());
			String[] a = new String[] { "[번호]", "[자기소개서명]" };
			out.header(a);
			for (int i = 0; i < EnumUtil.ONEPAGE; i++) {
				try {
					out.data(new Object[] { list.get(i).getIntroSeq(), list.get(i).getName() });
				} catch (Exception e) {
					break;
				}
			} // for
			out.line();
			System.out.println("\t\t\t" + EnumUtil.FIRSTPAGE + "/" + maxPage);
			out.line();
			while (true) {
				int page = sc.nextInt("페이지(다음단계 : 0)");
				if (page > maxPage) {
					out.pause("페이지 범위를 초과하셨습니다. 계속하시려면 엔터를 눌러주세요.");
					continue;
				}
				if (page == 0) {
					break;
				}
				int last = page * EnumUtil.ONEPAGE;
				int first = last - EnumUtil.ONEPAGE;
				out.title("자기소개서서 조회");
				out.header(a);
				for (int i = first; i < last; i++) {
					try {
						out.data(new Object[] { list.get(i).getIntroSeq(), list.get(i).getName() });
					} catch (Exception e) {
						break;
					}
				}
				out.eline();
				System.out.println("\t\t\t" + page + "/" + maxPage);
				out.eline();
			}
			if (input == 2) {
				String introSeq = sc.next("번호");
				out.title("자기소개서 조회");
				Iterator<IntroductionDTO> iter = list.iterator();
				while (iter.hasNext()) {
					IntroductionDTO dto = iter.next();
					if (dto.getIntroSeq().equals(introSeq)) {
						out.showIntroduction(dto);
						out.pause();
						return;
					}
				}
			} else {
				break;
			}
		}
	}
	
	/**
	 * 이력서 조회 변수
	 * @param input 선택변수
	 */
	private void showResume(int input) {
		while (true) {
			out.title("이력서 조회");
			ArrayList<ResumeDTO> list = JobSeekerUtil.ResumeList;
			int maxPage = jdao.getResumePage(JobSeekerUtil.loginJobSeeker.getJobSeekerSeq());
			String[] a = new String[] { "[번호]", "[이력서명]" };
			out.header(a);
			for (int i = 0; i < EnumUtil.ONEPAGE; i++) {
				try {
					out.data(new Object[] { list.get(i).getResumeSeq(), list.get(i).getName() });
				} catch (Exception e) {
					break;
				}
			} // for
			out.line();
			System.out.println("\t\t\t" + EnumUtil.FIRSTPAGE + "/" + maxPage);
			out.line();
			while (true) {
				int page = sc.nextInt("페이지(다음단계 : 0)");
				if (page > maxPage) {
					out.pause("페이지 범위를 초과하셨습니다. 계속하시려면 엔터를 눌러주세요.");
					continue;
				}
				if (page == 0) {
					break;
				}
				int last = page * EnumUtil.ONEPAGE;
				int first = last - EnumUtil.ONEPAGE;
				out.title("이력서 조회");
				out.header(a);
				for (int i = first; i < last; i++) {
					try {
						out.data(new Object[] { list.get(i).getResumeSeq(), list.get(i).getName() });
					} catch (Exception e) {
						break;
					}
				}
				out.eline();
				System.out.println("\t\t\t" + page + "/" + maxPage);
				out.eline();

			}
			if (input == 2) {
				String resumeSeq = sc.next("번호");
				out.title("이력서 조회");
				Iterator<ResumeDTO> iter = list.iterator();
				while (iter.hasNext()) {
					ResumeDTO dto = iter.next();
					if (dto.getResumeSeq().equals(resumeSeq)) {
						out.showResume(dto);
						out.pause();
						return;
					}
				}
			} else {
				break;
			}

		}
	}
	
	/**
	 * 새 자기소개서 작성 메소드
	 */
	private void addIntroduction() {
		out.title("새 자기소개서 작성");
		IntroductionDTO dto = new IntroductionDTO();
		String title = "", selfIntro = "";
		try {
			title = sc.next("자기소개서 제목");
			selfIntro = sc.next("내용");
		} catch (Exception e) {
			e.printStackTrace();
		}
		dto.setName(title);
		dto.setJobSeekerSeq(JobSeekerUtil.loginJobSeeker.getJobSeekerSeq());
		dto.setSelfIntroduction(selfIntro);

		int result = jdao.addIntroduction(dto);
		JobSeekerUtil.introList = jdao.getIntroFromSeekerSeq(JobSeekerUtil.loginJobSeeker.getJobSeekerSeq());
		out.result(result, "▶자기소개서 작성을 완료하였습니다. 계속하시려면 엔터키를 눌러주세요◀");
	}
	
	/**
	 * 이력서 추가 메소드
	 */
	private void addResume() {
		out.title("새 이력서 작성");
		ResumeDTO dto = new ResumeDTO();
		String title = "", toeic = "", volunteer = "", intern = "", awards = "", etc = "";
		try {
			title = sc.next("이력서 제목");
			toeic = sc.next("토익성적");
			volunteer = sc.next("봉사활동 횟수");
			intern = sc.next("인턴 경험");
			awards = sc.next("수상 경험");
			etc = sc.next("추가 기술 사항");
		} catch (Exception e) {
			e.printStackTrace();
		}
		dto.setName(title);
		dto.setAwards(awards);
		dto.setIntern(intern);
		dto.setJobSeekerSeq(JobSeekerUtil.loginJobSeeker.getJobSeekerSeq());
		dto.setToeic(toeic);
		dto.setVolunteer(volunteer);
		dto.setEtc(etc);

		int result = jdao.addResume(dto);
		JobSeekerUtil.ResumeList = jdao.getResumeFromSeekerSeq(JobSeekerUtil.loginJobSeeker.getJobSeekerSeq());
		out.result(result, "▶이력서 작성을 완료하였습니다. 계속하시려면 엔터키를 눌러주세요◀");

	}

}
