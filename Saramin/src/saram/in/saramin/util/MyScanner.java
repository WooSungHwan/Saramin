package saram.in.saramin.util;

import java.util.Scanner;

import saram.in.saramin.admin.AdminDAO;

/**
 * 입출력기능을 담당하는 클래스
 * @author user
 *
 */
public class MyScanner {
	private Scanner scan;
	private MyPrint out;

	public MyScanner() {
		scan = new Scanner(System.in);
		out = new MyPrint();
	}

	// String입력
	/**
	 * 입력제목을 받아 스캐너를 실행하는 메소드
	 * 
	 * @param label
	 *            입력제목
	 * @return
	 */
	public String next(String label) {
		try {
			System.out.printf(label + " :");
			return scan.nextLine();
		} catch (NumberFormatException ne) {
			out.result("입력오류가 발생했습니다. 신중히 입력해주세요.");

		} catch (Exception e) {
			out.result("입력오류가 발생했습니다. 신중히 입력해주세요.");

		}
		return null;
	}

	// String 다음칸 입력
	/**
	 * 입력제목을 받아 라인을 띄우고 스캐너를 실행하는 메소드
	 * 
	 * @param label
	 *            입력제목
	 * @return
	 */
	public String nextln(String label) {
		try {
			System.out.println(label + " :");
			return scan.nextLine();
		} catch (NumberFormatException ne) {
			out.result("입력오류가 발생했습니다. 신중히 입력해주세요.");

		} catch (Exception e) {
			out.result("입력오류가 발생했습니다. 신중히 입력해주세요.");

		}
		return null;
	}

	// Int입력
	/**
	 * 인트입력
	 * 
	 * @param label
	 *            라벨
	 * @return
	 */
	public int nextInt(String label) {
		try {
			System.out.print(label + " :");
			return Integer.parseInt(scan.nextLine());
		} catch (NumberFormatException ne) {
			out.result("입력오류가 발생했습니다. 신중히 입력해주세요.");

		} catch (Exception e) {
			out.result("입력오류가 발생했습니다. 신중히 입력해주세요.");

		}
		return 0;
	}

	// Int 다음칸 입력
	/**
	 * 인트 다음칸 입력
	 * 
	 * @param label
	 *            라벨
	 * @return 스캐너
	 */
	public int nextIntln(String label) {
		try {
			System.out.println(label + " :");
			return Integer.parseInt(scan.nextLine());
		} catch (NumberFormatException ne) {
			out.result("입력오류가 발생했습니다. 신중히 입력해주세요.");

		} catch (Exception e) {
			out.result("입력오류가 발생했습니다. 신중히 입력해주세요.");

		}
		return 0;
	}

	/**
	 * 다음칸 STring 입력
	 * 
	 * @return 스캐너
	 */
	public String nextLine() {
		try {
			return scan.nextLine();
		} catch (NumberFormatException ne) {
			out.result("입력오류가 발생했습니다. 신중히 입력해주세요.");

		} catch (Exception e) {
			out.result("입력오류가 발생했습니다. 신중히 입력해주세요.");

		}
		return null;
	}

	/**
	 * 인트 다음칸 입력
	 * 
	 * @return 스캐너
	 */
	public int nextInt() {
		try {
			return Integer.parseInt(scan.nextLine());
		} catch (NumberFormatException ne) {
			out.result("입력오류가 발생했습니다. 신중히 입력해주세요.");

		} catch (Exception e) {
			out.result("입력오류가 발생했습니다. 신중히 입력해주세요.");

		}
		return 0;
	}
}
