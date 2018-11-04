package saram.in.saramin.corporation;

import java.util.ArrayList;

import saram.in.saramin.jobseeker.JobCategoryDTO;
import saram.in.saramin.jobseeker.JobSeekerController;
import saram.in.saramin.jobseeker.JobSeekerDAO;
import saram.in.saramin.jobseeker.JobSeekerDTO;
import saram.in.saramin.jobseeker.RepresentationDTO;
import saram.in.saramin.util.CorporationUtil;
import saram.in.saramin.util.EnumUtil;
import saram.in.saramin.util.MyPrint;
import saram.in.saramin.util.MyScanner;

/**
 * 기업측의 기능을 포함하는 클래스( paging메소드의 재사용을 위해 상속)
 * 
 * @author user
 *
 */
public class CorporationController extends JobSeekerController {
	private MyPrint out;
	private MyScanner sc;
	private JobSeekerDAO jdao;
	private CorporationDAO cdao;
	private String title;
	private String header[];
	private ArrayList<Object[]> data;

	public CorporationController() {
		out = new MyPrint();
		sc = new MyScanner();
		jdao = new JobSeekerDAO();
		cdao = new CorporationDAO();
		data = new ArrayList<Object[]>();
	}

	/**
	 * 기업회원 측의 메인 분기문
	 */
	public void corporationMain() {
		while (true) {
			title = "기업회원";
			out.title(title);
			out.menu(new String[] { "공고 관리", "관심인재 관리", "공개 문서 조회", "추천인재 조회", "후기게시판", "로그아웃" });
			int input = 0;
			try {
				input = sc.nextInt("▶선택");
			} catch (Exception e) {
				out.result("입력오류가 발생하였습니다. 다시 진행해주시기 바랍니다.");
				continue;
			}
			out.eline();
			if (input == 1) { // 공고관리
				recruitManage();
			} else if (input == 2) {// 관심인재 관리
				interestPersonManage();
			} else if (input == 3) {// 공개이력서 조회
				showOpenRep();
			} else if (input == 4) {// 추천인재 조회
				recomendedJobSeeker();
			} else if (input == 5) {// 후기게시판
				board();
			} else if (input == 6) {// 로그아웃
				CorporationUtil.loginCorporation = null;
				return;
			} else {
				out.result("잘못입력하셨습니다. 신중히 입력해주시기 바랍니다.");
				continue;
			}

		}

	}
	
	@Override
	/**
	 * 후기게시판 메인 흐름문
	 */
	public void board() {
		title = "후기게시판";
		while (true) {
			out.title(title);
			out.menu(new String[] { "전체 게시글 조회", "게시글 작성", "게시글 수정", "게시글 삭제", "돌아가기" });
			int input = sc.nextInt("선택");
			if (input == 1) { // 전체게시글 조회
				super.allBoard();
			} else if (input == 2 ||input == 3||input == 4) { // 게시글 작성
				out.result("개인회원만 접근 가능한 공간입니다.");
			}  else if (input == 5) { // 돌아가기
				return;
			} else {
				out.result("잘못입력하셨습니다. 신중히 입력해주시기 바랍니다.");
			}
		}
	}
	
	/**
	 * 추천인재 조회 메소드
	 */
	private void recomendedJobSeeker() {
		title = "추천인재 조회";
		out.title(title);
		String[] header = new String[] { "번호", "직무명" };
		ArrayList<JobCategoryDTO> list = jdao.getJobCategory();
		int maxPage = (int) Math.ceil(list.size() / EnumUtil.ONEPAGE) + 1;
		ArrayList<Object[]> data = new ArrayList<Object[]>();
		for (int i = 0; i < list.size(); i++) {
			data.add(new Object[] { list.get(i).getCategorySeq(), list.get(i).getJobName() });
		}
		paging(title, header, maxPage, data);
		out.line();
		String jobSeq = sc.next("번호");
		out.line();
		ArrayList<RepresentationDTO> rlist=cdao.getRepHope(jobSeq);
		maxPage=(int) Math.ceil(rlist.size() / EnumUtil.ONEPAGE) + 1;
		data.clear();
		for (int i = 0; i < rlist.size(); i++) {
			data.add(new Object[] { rlist.get(i).getJobSeekerSeq(), rlist.get(i).getJobSeekerName(),rlist.get(i).getResumeTitle(),rlist.get(i).getIntroTitle() });
		}
		header= new String[] {
			"번호","성명","이력서명","자기소개서명"	
		};
		paging(title,header,maxPage,data);
		String jobseekerSeq = sc.next("번호");
		for(int i=0; i<rlist.size();i++) {
			if(rlist.get(i).getJobSeekerSeq().equals(jobseekerSeq)) {
				RepresentationDTO dto = rlist.get(i);
				out.title("추천인재 조회");
				System.out.println("[성명] : " + dto.getJobSeekerName());
				System.out.println("[이력서명] : " + dto.getResumeTitle());
				System.out.println("[토익성적] : " + dto.getToeic() + "점");
				System.out.println("[봉사활동] : " + dto.getVolun() + "회");
				System.out.println("[인턴경험] : " + dto.getInturn() + "회");
				System.out.println("[수상횟수] : " + dto.getAwards() + "회");
				System.out.println("[자기소개서명] : " + dto.getIntroTitle());
				System.out.print("[자기소개서 내용] : ");
				String[] content = new String[dto.getIntroContent().length()];
				for (int j = 0; j < content.length; j++) {
					if (j % 50 == 0) {
						System.out.println();
					}
					content[j] = dto.getIntroContent().substring(j, j + 1);
					try {
						System.out.print(content[j]);
					} catch (Exception e) {
						break;
					}
				}
				System.out.println();
				out.line();
				String sel = sc.nextln("해당 인원을 관심인재로 등록하시겠습니까?(y/n)");
				if(sel.equals("y")) {
					int result = cdao.addIP(jobseekerSeq);
					out.result(result, "해당 인원을 관심인재로 등록하였습니다.");
				}else {
					out.pause();
					return;
				}
				
				
				
			}
			
		}

	}
	
	/**
	 * 공개문서 조회
	 */
	private void showOpenRep() {
		title = "공개 문서서 조회";
		header = new String[] { "[번호]", "[성명]", "[이력서명]", "[자기소개서명]" };
		ArrayList<RepresentationDTO> list = cdao.getOpenRep();
		data.clear();
		for (int i = 0; i < list.size(); i++) {
			data.add(new Object[] { list.get(i).getRepSeq(), list.get(i).getJobSeekerName(),
					list.get(i).getResumeTitle(), list.get(i).getIntroTitle() });
		}
		int maxPage = (int) Math.ceil(list.size() / EnumUtil.ONEPAGE) + 1;
		paging(title, header, maxPage, data);
		String repSeq = sc.next("번호");
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getRepSeq().equals(repSeq)) {
				RepresentationDTO dto = list.get(i);
				out.title("공개 문서 조회");
				System.out.println("[성명] : " + dto.getJobSeekerName());
				System.out.println("[이력서명] : " + dto.getResumeTitle());
				System.out.println("[토익성적] : " + dto.getToeic() + "점");
				System.out.println("[봉사활동] : " + dto.getVolun() + "회");
				System.out.println("[인턴경험] : " + dto.getInturn() + "회");
				System.out.println("[수상횟수] : " + dto.getAwards() + "회");
				System.out.println("[자기소개서명] : " + dto.getIntroTitle());
				System.out.print("[자기소개서 내용] : ");
				String[] content = new String[dto.getIntroContent().length()];
				for (int j = 0; j < content.length; j++) {
					if (j % 50 == 0) {
						System.out.println();
					}
					content[j] = dto.getIntroContent().substring(j, j + 1);
					try {
						System.out.print(content[j]);
					} catch (Exception e) {
						break;
					}
				}
				System.out.println();
				out.pause();

			}
		}

	}
	
	/**
	 * 관심인재 관리의 주 흐름문
	 */
	private void interestPersonManage() {
		while (true) {
			title = "관심인재 관리";
			out.title(title);
			out.menu(new String[] { "관심인재 목록", "관심인재 삭제", "돌아가기" });
			int input = 0;
			try {
				input = sc.nextInt("▶선택");
			} catch (Exception e) {
				out.result("입력오류가 발생하였습니다. 다시 진행해주시기 바랍니다.");
				continue;
			}
			if (input == 1) { // 관심인재 목록 조회
				title = "관심인재 목록";
				showIP(title);
			} else if (input == 2) {// 관심목록 삭제
				title = "관심인재 삭제";
				deleteIP(title);
			} else if (input == 3) {// 돌아가기
				return;
			} else {

			}
		}

	}

	/**
	 * 관심인재 목록을 삭제함.
	 * 
	 * @param title
	 */
	private void deleteIP(String title) {
		out.title(title);
		header = new String[] { "번호", "성명", "전화번호", "", "이메일" };
		ArrayList<JobSeekerDTO> list = cdao.getIPJobSeeker(CorporationUtil.loginCorporation.getCorpSeq());
		data.clear();
		for (int i = 0; i < list.size(); i++) {
			data.add(new Object[] { list.get(i).getJobSeekerSeq(), list.get(i).getName(), list.get(i).getTel(),
					list.get(i).getEmail() });
		}
		int maxPage = (int) Math.ceil(list.size() / EnumUtil.ONEPAGE) + 1;
		paging(title, header, maxPage, data);
		String jobSeekerSeq = sc.next("삭제할 번호");
		int result = cdao.deleteIP(jobSeekerSeq, CorporationUtil.loginCorporation.getCorpSeq());
		out.result(result, "해당 인원을 관심인재목록에서 삭제하였습니다.");
	}

	/**
	 * 관심인재 목록을 페이징까지 보여주는 메소드
	 */
	private void showIP(String title) {
		out.title(title);
		header = new String[] { "번호", "성명", "전화번호", "", "이메일" };
		ArrayList<JobSeekerDTO> list = cdao.getIPJobSeeker(CorporationUtil.loginCorporation.getCorpSeq());
		data.clear();
		for (int i = 0; i < list.size(); i++) {
			data.add(new Object[] { list.get(i).getJobSeekerSeq(), list.get(i).getName(), list.get(i).getTel(),
					list.get(i).getEmail() });
		}
		int maxPage = (int) Math.ceil(list.size() / EnumUtil.ONEPAGE) + 1;
		paging(title, header, maxPage, data);
	}

	/**
	 * 공고관리 메소드
	 */
	private void recruitManage() {
		title = "공고 관리";
		while (true) {
			out.title(title);
			out.menu(new String[] { "새 공고 작성", "공고 조회", "공고 삭제", "공고 수정", "뒤로가기" });
			int input = 0;
			try {
				input = sc.nextInt("▶선택");
			} catch (Exception e) {
				out.result("입력오류가 발생하였습니다. 다시 진행해주시기 바랍니다.");
				continue;
			}
			if (input == 1) { // 새공고 작성
				addRecruit();
			} else if (input == 2) {// 공고조회
				myRecruit();
			} else if (input == 3) {// 공고삭제
				deleteRecruit();
			} else if (input == 4) {// 공고 수정
				updateRecruit();
			} else if (input == 5) {// 뒤로가기
				return;
			} else {
				out.result("잘못입력하셨습니다. 신중히 입력해주시기 바랍니다.");
				continue;
			}
		}
	}

	/**
	 * 공고 수정 메소드
	 */
	private void updateRecruit() {
		title = "공고 수정";
		out.title(title);
		ArrayList<RecruitmentDTO> list = cdao.getCorpMyRecruit();
		data.clear();
		for (RecruitmentDTO dto : list) {
			data.add(new Object[] { dto.getRecruitSeq(), dto.getTitle() });
		}
		header = new String[] { "[번호]", "[공고제목]" };
		int maxPage = cdao.getMyRecruitPage();
		paging(title, header, maxPage, data);
		String recruitSeq = sc.next("번호");
		for (int i = 0; i < list.size(); i++) {
			out.title(title);
			System.out.println("[현재 지원한 인원] : " + jdao.getApplyNumber(recruitSeq) + "명");
			out.line();
			out.showRecruit(list.get(i));
			out.menu(new String[] { "제목수정", "지원자격 수정", "마감일 수정", "공고설명 수정", "채용인원 수정", "취소하기" });
			int input = sc.nextInt("선택");

			if (input == 6) {
				return;
			}
			String content = sc.next("수정할 내용");
			int result = cdao.updateRecruit(input, recruitSeq, content);
			out.result(result, "▶ 해당 공고를 수정하였습니다. 계속하시려면 엔터키를 눌러주세요. ◀");
			break;
		}
	}

	/**
	 * 공고삭제 담당 메소드
	 */
	private void deleteRecruit() {
		title = "공고 삭제";
		out.title(title);
		ArrayList<RecruitmentDTO> list = cdao.getCorpMyRecruit();
		data.clear();
		for (RecruitmentDTO dto : list) {
			data.add(new Object[] { dto.getRecruitSeq(), dto.getTitle() });
		}
		header = new String[] { "[번호]", "[공고제목]" };
		int maxPage = cdao.getMyRecruitPage();
		paging(title, header, maxPage, data);
		String recruitSeq = sc.next("번호");
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getRecruitSeq().equals(recruitSeq)) {
				out.title(title);
				System.out.println("[현재 지원한 인원] : " + jdao.getApplyNumber(recruitSeq) + "명");
				out.line();
				out.showRecruit(list.get(i));
				String sel = sc.next("해당 공고를 삭제하시겠습니까?(y/n)");
				if (sel.equals("y")) {
					int result = cdao.deleteRecruit(recruitSeq);
					out.result(result, "▶ 해당 공고를 삭제하였습니다. 계속하시려면 엔터키를 눌러주세요. ◀");
				} else if (sel.equals("n")) {
					out.pause();
					return;
				} else {
					out.result("y 또는 n만 입력해주시기 바랍니다. 다시진행해주세요.");
					return;
				}
			}
		}
	}

	/**
	 * 나의 공고 조회 메소드
	 */
	private void myRecruit() {
		title = "공고 조회";
		out.title(title);
		ArrayList<RecruitmentDTO> list = cdao.getCorpMyRecruit();
		data.clear();
		for (RecruitmentDTO dto : list) {
			data.add(new Object[] { dto.getRecruitSeq(), dto.getTitle() });
		}
		header = new String[] { "[번호]", "[공고제목]" };
		int maxPage = cdao.getMyRecruitPage();
		paging(title, header, maxPage, data);
		String recruitSeq = sc.next("번호");
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getRecruitSeq().equals(recruitSeq)) {
				out.title(title);
				System.out.println("[현재 지원한 인원] : " + jdao.getApplyNumber(recruitSeq) + "명");
				out.line();
				out.showRecruit(list.get(i));
				out.pause();
				return;
			}
		}

	}

	/**
	 * 공고작성 메소드
	 */
	private void addRecruit() {
		title = "새 공고 작성";
		out.title(title);
		String recruitTitle = sc.next("[공고 제목]");
		title = "새 공고 작성(직무 선택)";
		out.title(title);
		header = new String[] { "번호", "직무명" };
		ArrayList<JobCategoryDTO> jlist = jdao.getJobCategory();
		int maxPage = (int) Math.ceil(jlist.size() / EnumUtil.ONEPAGE) + 1;
		data.clear();
		for (int i = 0; i < jlist.size(); i++) {
			data.add(new Object[] { jlist.get(i).getCategorySeq(), jlist.get(i).getJobName() });
		}
		paging(title, header, maxPage, data);
		out.line();
		String jobSeq = sc.next("[직무번호]");
		title = "새 공고 작성";
		out.title(title);
		String qualification = sc.next("[지원자격]");
		String num = sc.next("[채용 인원]");
		String enddate = sc.next("마감일");
		String others = sc.next("채용 설명");
		int result = cdao.addRecruit(recruitTitle, jobSeq, qualification, num, enddate, others);
		out.result(result, "▶ 새 공고를 작성하였습니다. 계속하시려면 엔터키를 눌러주세요. ◀");
	}

	@Override
	/**
	 * 메소드 오버라이딩.
	 */
	public void paging(String title, String[] header, int maxPage, ArrayList<Object[]> data) {
		super.paging(title, header, maxPage, data);
	}

}
