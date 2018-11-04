package saram.in.saramin.admin;

import java.util.ArrayList;

import saram.in.saramin.MainClass;
import saram.in.saramin.util.EnumUtil;
import saram.in.saramin.util.LoginType;
import saram.in.saramin.util.MyPrint;
import saram.in.saramin.util.MyScanner;

/**
 * 관리자파트의 모든 기능을 담당하는 클래스
 * @author woo
 *
 */
public class AdminController {
	private MyPrint out;
	private MyScanner sc;
	private AdminDAO adao;
	private ArrayList<Object[]> list;

	public AdminController() {
		out = new MyPrint();
		sc = new MyScanner();
		adao = new AdminDAO();
		String type = LoginType.관리자.toString();
		EnumUtil.isAuth = type;
	}
	
	/**
	 * 관리자의 메인 분기문
	 */
	public void adminMain() {
		while (true) {
			out.title("관리자");
			out.menu(new String[] { "전체 공고지원 내역조회", "직무 카테고리 관리", "게시판 제한글자 관리", "로그아웃" });
			int input = -1;
			try {
				input = sc.nextInt("선택");
			} catch (NumberFormatException e) {
				out.result("입력오류가 발생하였습니다. 신중히 입력해주시기 바랍니다.");
			}
			if (input == 1) {// 전체 공고지원내역
				applyRecruitList();
			} else if (input == 2) {// 직무 카테고리 관리
				jobManage();
			} else if (input == 3) { // 게시판 제한글자관리
				restrictLetter();
			} else if (input == 4) { // 로그아웃
				EnumUtil.isAuth = null;
				return;
			} else {
				out.result("잘못입력하셨습니다. 신중히 입력해주시기 바랍니다.");
				continue;
			}

		}

	}
	
	/**
	 * 관리자 제한글자 흐름메소드
	 */
	private void restrictLetter() {
		while (true) {
			out.title("게시판 제한글자 관리");
			String[] menu = new String[] { "제한글자 조회", "제한글자 추가", "제한글자 수정", "제한글자 삭제", "돌아가기" };
			out.menu(menu);
			int input = -1;
			try {
				input = sc.nextInt("선택");
			} catch (NumberFormatException e) {
				out.result("입력오류가 발생하였습니다. 신중히 입력해주시기 바랍니다.");
			}
			if (input == 1) { // 조회
				showLetter();
			} else if (input == 2) { // 추가
				String letter = sc.next("추가할 단어");
				int result = adao.addLetter(letter);
				out.result(result,"▶  추가하였습니다. 계속하시려면 엔터키를 눌러주세요. ◀");
			} else if (input == 3) { // 수정
				showLetter();
				input = sc.nextInt("수정할 번호");
				String letter = sc.next("수정할 단어");
				int result = adao.updateLetter(input,letter);
				out.result(result,"▶  수정하였습니다. 계속하시려면 엔터키를 눌러주세요. ◀");
			} else if (input == 4) { // 삭제
				showLetter();
				input = sc.nextInt("삭제할 번호");
				int result = adao.deleteLetter(input);
				out.result(result,"▶  삭제하였습니다. 계속하시려면 엔터키를 눌러주세요. ◀");
			} else if (input == 5) {
				break;
			}

			else {
				out.result("잘못입력하셨습니다. 신중히 입력해주시기 바랍니다.");
				continue;
			}

		}

	}
	/**
	 * 제한글자 보여주기 메소드
	 */
	private void showLetter() {

		while (true) {
			out.title("재한글자 조회");
			int maxPage = adao.getRestrictPage();
			list = adao.getLetterList();
			// 첫페이지
			String[] a = new String[] { "[번호]", "[제한글자]" };
			out.header(a);
			for (int i = 0; i < EnumUtil.ONEPAGE; i++) {
				try {
					out.data(list.get(i));
				} catch (Exception e) {
					break;
				}//for
			}
			out.eline();
			System.out.println("\t\t\t" + EnumUtil.FIRSTPAGE + "/" + maxPage);
			out.eline();
			while (true) {
				int page = sc.nextInt("페이지(다음단계 : 0)");
				if (page > maxPage) {
					out.pause("페이지 범위를 초과하셨습니다. 계속하시려면 엔터를 눌러주세요.");
					continue;
				}
				if (page == 0) {
					return;
				}
				int last = page * EnumUtil.ONEPAGE;
				int first = last - EnumUtil.ONEPAGE;
				out.title("제한글자 조회");
				out.header(a);
				for (int i = first; i < last; i++) {
					try {
						out.data(list.get(i));
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
	 * 직무카테고리 관리 메소드
	 */
	private void jobManage() {

		while (true) {

			out.title(">> 직무 카테고리 관리 <<");
			out.menu(new String[] { "직무 추가하기", "직무 삭제하기", "직무 수정하기", "돌아가기" });

			int input = sc.nextInt("선택");
			out.eline();
			if (input == 1) {// 직무추가
				addJob();
			} else if (input == 2) {// 직무삭제
				deleteJob(input);
			} else if (input == 3) {// 직무수정
				updateJob(input);
			} else if (input == 4) { // 돌아가기
				break;
			} else {
				out.result("잘못입력하셨습니다. 신중히 입력해주시기 바랍니다.");
				continue;
			}

		}

	}
	
	/**
	 * 직무명 수정 메소드
	 * @param input 선택변수
	 */
	private void updateJob(int input) {
		deleteJob(input);
		int num = sc.nextInt("번호(수정취소 : 0)");
		if (num == 0) {
			return;
		}
		String name = sc.next("수정할 직무명");
		int result = adao.updatejob(name, num);
		out.result(result, "▶  수정을 성공하였습니다. 계속하시려면 엔터키를 눌러주세요. ◀");
	}
	
	/**
	 * 직무삭제 메소드
	 * @param input 선택변수
	 */
	private void deleteJob(int input) {
		out.title("직무 삭제하기");
		int maxPage = adao.getJobTotalPage();
		list = adao.getJobList();
		// 첫페이지
		String[] a = new String[] { "[번호]", "[직무명]" };
		out.header(a);
		for (int i = 0; i < EnumUtil.ONEPAGE; i++) {
			out.data(list.get(i));
		}
		out.eline();
		System.out.println("\t\t\t" + EnumUtil.FIRSTPAGE + "/" + maxPage);
		out.eline();
		// 페이징작업
		while (true) {
			int page = sc.nextInt("페이지(삭제하러가기 : 0)");
			if (page > maxPage) {
				out.pause("페이지 범위를 초과하셨습니다. 계속하시려면 엔터를 눌러주세요.");
				continue;
			}
			if (page == 0) {
				break;
			}
			int last = page * EnumUtil.ONEPAGE;
			int first = last - EnumUtil.ONEPAGE;
			out.title("전체 공고지원 내역조회");
			out.header(a);
			for (int i = first; i < last; i++) {
				try {
					out.data(list.get(i));
				} catch (Exception e) {
					break;
				}
			}
			out.eline();
			System.out.println("\t\t\t" + page + "/" + maxPage);
			out.eline();
		}
		if (input == 2) { // 삭제
			int num = sc.nextInt("번호(삭제취소 : 0)");
			if (num == 0) {
				return;
			}
			int result = adao.deleteJob(num);
			out.result(result, "▶ 삭제를 성공하였습니다. 계속하시려면 엔터키를 눌러주세요. ◀");
		} else {
			return;
		}
	}
	
	/**
	 * 직무 추가 메소드
	 */
	private void addJob() {
		String name = sc.next("추가할 직무명");
		int result = adao.addJob(name);
		out.result(result, "▶ 추가되었습니다. 계속하시려면 엔터키를 눌러주세요. ◀");
	}
	
	/**
	 * 공고지원한 내역 조회 메소드
	 */
	private void applyRecruitList() {
		out.title("전체 공고지원 내역조회");
		int maxPage = adao.getApplyTotalPage();
		list = new ArrayList<Object[]>();
		list = adao.getApplyList(EnumUtil.FIRSTPAGE, EnumUtil.ONEPAGE);
		String[] a = new String[] { "[성명]", "[지원일]", "", "[지원기업명]" };
		out.header(a);
		for (Object[] data : list) {
			out.data(data);
		} // 처음 1페이지 보여주는거
		out.eline();
		System.out.println("\t\t\t" + EnumUtil.FIRSTPAGE + "/" + adao.getApplyTotalPage());
		out.eline();

		while (true) {
			int page = sc.nextInt("페이지");
			if (page > maxPage) {
				out.pause("페이지 범위를 초과하셨습니다. 계속하시려면 엔터를 눌러주세요.");
				continue;
			}
			if (page == 0) {
				break;
			}
			int last = page * EnumUtil.ONEPAGE;
			int first = last - EnumUtil.ONEPAGE;
			list = adao.getApplyList(first, last);
			out.title("전체 공고지원 내역조회");
			out.header(a);
			for (Object[] data : list) {
				out.data(data);
			}
			out.eline();
			System.out.println("\t\t\t" + page + "/" + maxPage);
			out.eline();
		}
	}

}
