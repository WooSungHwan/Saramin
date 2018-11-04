package saram.in.saramin;

import java.util.ArrayList;

import saram.in.saramin.admin.AdminController;
import saram.in.saramin.admin.ReviewDTO;
import saram.in.saramin.corporation.CorporationController;
import saram.in.saramin.corporation.CorporationLoginDTO;
import saram.in.saramin.jobseeker.JobSeekerController;
import saram.in.saramin.jobseeker.JobSeekerDAO;
import saram.in.saramin.jobseeker.JobSeekerLoginDTO;
import saram.in.saramin.util.CorporationUtil;
import saram.in.saramin.util.JobSeekerUtil;
import saram.in.saramin.util.MyPrint;
import saram.in.saramin.util.MyScanner;

/**
 * 메인클래스의 컨트롤러역할
 * 
 * @author woo
 *
 */
public class MainController {
	private String title;
	private String[] header;
	private MyPrint out;
	private MyScanner sc;
	private MainDAO mdao;
	private JobSeekerDAO jdao;

	public MainController() {
		out = new MyPrint();
		sc = new MyScanner();
		mdao = new MainDAO();
		jdao = new JobSeekerDAO();
	}

	/**
	 * 메인 메서드의 실질적 실행문...
	 */
	public void mainStream() {
		while (true) {
			out.title("구인구직 프로그램");
			out.menu(new String[] { "로그인", "회원가입", "ID/PW 찾기", "후기게시판", "프로그램 종료" });
			int input = 0;
			try {
				input = sc.nextInt("선택");
			} catch (NumberFormatException e) {
				out.result("입력오류가 발생하였습니다. 신중히 입력해주시기 바랍니다.");
			}
			if (input == 1) { // 로그인
				login();
			} else if (input == 2) { // 회원가입
				join();
			} else if (input == 3) { // ID/PW 찾기
				findLogin();
			} else if (input == 4) { // 후기게시판(비로그인)
				notLoginboard();
			} else if (input == 5) { // 프로그램 종료
				out.result("프로그램을 종료합니다. 이용해주셔서 감사합니다.");
				break;
			} else {
				out.result("잘못입력하셨습니다. 신중히 입력해주시기 바랍니다.");
			}
		}

	}
	
	/**
	 * 비회원의 게시판
	 */
	private void notLoginboard() { // 비회원 게시판 이용...
		title = "후기게시판(비회원)";
		out.title(title);
		header = new String[] { "[글번호]", "[글제목]", "", "[작성자]", "[조회수]" };
		int maxPage = jdao.getBoardPage();
		ArrayList<ReviewDTO> list = jdao.getAllReview();
		ArrayList<Object[]> data = new ArrayList<Object[]>();
		for (int i = 0; i < list.size(); i++) {
			String reviewTitle = list.get(i).getContent().substring(0, 8) + "...";
			data.add(new Object[] { list.get(i).getReviewSeq(), reviewTitle, list.get(i).getJobSeekerName(),
					list.get(i).getViews() });
		}
		JobSeekerController c = new JobSeekerController();
		c.paging(title, header, maxPage, data);
		String reviewSeq = sc.next("번호");
		out.result("▶비회원으로는 접속할 수 없습니다.◀");

	}
	
	/**
	 * 회원가입 메소드
	 */
	private void join() { // 회원가입
		while (true) {
			out.title("회원가입");
			out.menu(new String[] { "개인회원", "기업회원", "돌아가기" });
			int input = 0;
			try {
				input = sc.nextInt("선택");
			} catch (Exception e) {
				out.result("입력오류가 발생하였습니다. 신중히 입력해주시기 바랍니다.");
				continue;
			}
			if (input == 1) { // 개인회원
				while (true) {
					out.title("회원가입");
					String id = sc.next("아이디");
					int result = mdao.idDoubleCheck(input, id);
					if (result == 1) {
						out.result(result, "중복된 아이디가 있습니다. 아이디를 다시 입력해주세요.");
						continue;
					} else {
						out.pause("중복된 아이디가 없습니다. 계속하시려면 엔터를 입력해주세요.");
					}
					String pw = sc.next("비밀번호");
					String name = sc.next("성명");
					String ssn = sc.next("주민등록번호");
					String tel = sc.next("전화번호");
					String address = sc.next("주소");
					String email = sc.next("이메일");
					result = mdao.addJobSeeker(id,pw,name,ssn,tel,address,email);
					out.result(result, "▶회원가입을 완료하였습니다. 계속하시려면 엔터를 눌러주세요.◀");
					return;
				}
			} else if (input == 2) { // 기업회원
				out.title("회원가입");
				String id = sc.next("아이디");
				int result = mdao.idDoubleCheck(input, id);
				if (result == 1) {
					out.result(result, "중복된 아이디가 있습니다. 아이디를 다시 입력해주세요.");
					continue;
				} else {
					out.pause("중복된 아이디가 없습니다. 계속하시려면 엔터를 입력해주세요.");
				}
				String pw = sc.next("비밀번호");
				String name = sc.next("기업명");
				String ssn = sc.next("사업자번호");
				String tel = sc.next("전화번호");
				String address = sc.next("주소");
				String email = sc.next("홈페이지주소");
				String establishDate = sc.next("회사설립일");
				result = mdao.addCorp(id,pw,name,ssn,tel,address,email,establishDate);
				out.result(result, "▶회원가입을 완료하였습니다. 계속하시려면 엔터를 눌러주세요.◀");
				return;
				
			} else if (input == 3) {
				return; // 돌아가기
			} else {
				out.result("잘못입력하셨습니다. 신중히 입력해주시기 바랍니다.");
				continue;
			}
			
		}
	}
	/**
	 * ID/PW 찾기 메소드
	 */
	private void findLogin() { // ID/PW 찾기
		while (true) {
			title = "ID/PW 찾기";
			out.title(title);
			out.menu(new String[] { "개인회원", "기업회원", "뒤로가기" });
			int input = 0;
			try {
				input = sc.nextInt("선택");
			} catch (Exception e) {
				out.result("입력오류가 발생하였습니다. 신중히 입력해주시기 바랍니다.");
				continue;
			}
			if (input == 1) { // 개인회원
				out.title(title);
				String name = sc.next("이름");
				String ssn = sc.next("주민등록번호 뒷자리");
				out.line();
				JobSeekerLoginDTO dto = mdao.getJobSeekerIdPw(name, ssn);
				if (dto == null) {
					out.result("해당 정보에 일치하는 계정이 없습니다. 다시 진행해주세요.");
					continue;
				}
				out.result("▶" + dto.getJobSeekerSeq() + "님의 아이디와 비밀번호입니다.◀");
				System.out.println();
				System.out.println("\t\t\t▶아이디 : " + dto.getId());
				System.out.println();
				System.out.println("\t\t\t▶비밀번호 : " + dto.getPw());
				System.out.println();
				out.pause();
			} else if (input == 2) {// 기업회원
				out.title(title);
				String name = sc.next("기업명");
				String ssn = sc.next("사업자번호");
				out.line();
				CorporationLoginDTO dto = mdao.getCorporationIdPw(name, ssn);
				if (dto == null) {
					out.result("해당 정보에 일치하는 계정이 없습니다. 다시 진행해주세요.");
					continue;
				}
				out.result("▶" + dto.getCorpSeq() + "님의 아이디와 비밀번호입니다.◀");
				System.out.println();
				System.out.println("\t\t\t▶아이디 : " + dto.getId());
				System.out.println();
				System.out.println("\t\t\t▶비밀번호 : " + dto.getPw());
				System.out.println();
				out.pause();
			} else if (input == 3) {// 뒤로가기
				return;
			} else {
				out.result("잘못입력하셨습니다. 신중히 입력해주시기 바랍니다.");
				continue;
			}
		}

	}
	
	/**
	 * 로그인하기 메소드
	 */
	private void login() { // 로그인하기.
		while (true) {
			out.title("로그인");
			out.menu(new String[] { "개인 회원", "기업 회원", "관리자", "돌아가기" });
			int input = 0;
			try {
				input = sc.nextInt("선택");
				out.eline();
			} catch (NumberFormatException e) {
				out.result("입력오류가 발생하였습니다. 신중히 입력해주시기 바랍니다.");
				continue;
			}
			if (input == 4) {// 돌아가기
				break;
			}
			String id = sc.next("아이디");
			String pw = sc.next("비밀번호");
			if (input == 1) {
				int result = mdao.loginCheck(input, id, pw);
				if (result == 1) {
					String seq = mdao.getJobSeekerSeq(id, pw);
					JobSeekerUtil.loginJobSeeker = mdao.getJobSeekerDTO(seq);
					out.line();
					out.pause("▶ " + JobSeekerUtil.loginJobSeeker.getName() + "님 안녕하세요. 로그인에" + " 성공하였습니다.◀");
					JobSeekerController jc = new JobSeekerController();
					jc.jobSeekerMain();
					break;
				}
			} else if (input == 2) {
				int result = mdao.loginCheck(input, id, pw);
				if(result==1) {
					String seq=mdao.getCorporationseq(id, pw);
					CorporationUtil.loginCorporation=mdao.getCorporation(seq);
					
					out.line();
					out.pause("▶ " + CorporationUtil.loginCorporation.getName() + "님 안녕하세요. 로그인에" + " 성공하였습니다.◀");
					CorporationController cc = new CorporationController();
					cc.corporationMain();
					break;
				}
			} else if (input == 3) {

				int result = mdao.loginCheck(input, id, pw);

				if (result == 1) {
					AdminController admin = new AdminController();
					admin.adminMain();
					break;
				} else {
					out.result(result, "알 수 없는 오류가 발생했습니다. 다시 진행해주세요.");
					continue;
				}

			} else if (input == 4) {
				break;
			} else {
				out.result("잘못입력하셨습니다. 신중히 입력해주시기 바랍니다.");
			}
		}
	}

}
