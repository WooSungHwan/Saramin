package saram.in.saramin.util;

import java.util.ArrayList;
import java.util.Scanner;

import saram.in.saramin.admin.ReviewDTO;
import saram.in.saramin.corporation.CorporationDTO;
import saram.in.saramin.corporation.RecruitmentDTO;
import saram.in.saramin.jobseeker.IntroductionDTO;
import saram.in.saramin.jobseeker.ResumeDTO;

/**
 * 출력기능을 담당하는 클래스
 * @author user
 *
 */
public class MyPrint {
	String jobseeker = null;
	String corporation = null;
	String admin = null;
	String be = null;
	

	public MyPrint() {
		
		jobseeker = LoginType.개인회원.toString();
		corporation = LoginType.기업회원.toString();
		admin = LoginType.관리자.toString();
		be = LoginType.비회원.toString();
	}

	/**
	 * "="선을 긋기위한 메소드
	 */
	public void eline() {
		for (int i = 0; i < EnumUtil.LONG; i++) {
			System.out.print("=");
		}
		System.out.println();
	}

	/**
	 * "-"선을 긋기위한 메소드
	 */
	public void line() {
		for (int i = 0; i < EnumUtil.LONG; i++) {
			System.out.print("-");
		}
		System.out.println();
	}

	/**
	 * 제목을 출력하기 위한 메소드
	 * 
	 * @param label
	 *            제목
	 */
	public void title(String label) {
		for (int i = 0; i < 30; i++) {
			System.out.println();
		}
		eline();
		
		for (int i = 0; i < (EnumUtil.LONG / 2)-label.length(); i++) {
			System.out.print(" ");
		}
		System.out.println(label);
		if(JobSeekerUtil.loginJobSeeker != null) {
			System.out.println(JobSeekerUtil.loginJobSeeker.getName()+" 회원님 안녕하세요.");
			System.out.print("[이력서] : ");
			System.out.println(JobSeekerUtil.ResumeList!=null ? "등록" : "미등록");
			System.out.print("[자기소개서] : ");
			System.out.println(		JobSeekerUtil.introList!=null ? "등록":"미등록");
			
		}
		if(CorporationUtil.loginCorporation!=null) {
			System.out.println("'"+CorporationUtil.loginCorporation.getName()+"'"+ "님 안녕하세요");
		}
		eline();
	}

	/**
	 * 출력할 메뉴를 출력
	 * 
	 * @param str
	 *            메뉴리스트
	 */
	public void menu(String[] str) {
		for (int i = 1; i <= str.length; i++) {
			System.out.printf("\t\t\t\t\t%s. %s\n", i, str[i - 1]);
		}
		line();
	}

	/**
	 * 헤더는 문자열 타입이므로 데이터의 헤더 출력
	 * 
	 * @param list
	 *            문자열 배열
	 */
	public void header(String[] list) {
		for (int i = 0; i < list.length; i++) {
			System.out.printf("%s\t", list[i]);
		}
		System.out.println();
	}

	/**
	 * 데이터를 콘솔창에 출력하기 위한 메소드
	 * 
	 * @param datas
	 *            오브젝트 배열
	 */
	public void data(Object[] datas) {
		line();
		for (Object data : datas) {
			System.out.printf("%s\t", data);
		}
		System.out.println();
		
	}

	/**
	 * 흐름을 잠시 멈추기 위한 메소드
	 */
	public void pause() {
		line();
		System.out.println("\t\t\t\t★계속하시려면 엔터키를 입력하세요★");
		line();
		Scanner sc = new Scanner(System.in);
		sc.nextLine();
	}

	/**
	 * 잠시 흐름을 멈추기 위한 메소드
	 * 
	 * @param label
	 *            중지메시지
	 */
	public void pause(String label) {
		line();
		System.out.println("\t\t\t" + label);
		line();
		Scanner sc = new Scanner(System.in);
		sc.nextLine();
	}

	/**
	 * 1일때 결과 출력 0 일때 결과 미출력
	 * 
	 * @param result
	 * @param msg
	 */
	public void result(int result, String msg) {
		if (result > 0) {
			eline();
			for(int i =0; i<(EnumUtil.LONG/2)-(msg.length()); i++) {
				System.out.print(" ");
			}
			System.out.println("[결과]" + msg);
			eline();
		} else {
			eline();
			System.out.println("\t\t\t실패하였습니다. 관리자에게 문의해주세요.");
			eline();
		}
		Scanner sc = new Scanner(System.in);
		sc.nextLine();
	}

	/**
	 * 결과 메시지 출력 메소드
	 * 
	 * @param msg
	 *            메시지
	 */
	public void result(String msg) {
		eline();
		System.out.println("\t\t\t[결과]" + msg);
		eline();
		Scanner sc = new Scanner(System.in);
		sc.nextLine();
	}
	
	/**
	 * 이력서 형식 출력
	 * @param dto 객체
	 */
	public void showResume(ResumeDTO dto) {
		System.out.println("[이름] : "+JobSeekerUtil.loginJobSeeker.getName());
		System.out.println("[이력서명] : "+dto.getName());
		System.out.println("[토익성적] : "+dto.getToeic()+"점");
		System.out.println("[봉사활동] : "+dto.getVolunteer()+"회");
		System.out.println("[인턴경험] : "+dto.getIntern()+"회");
		System.out.println("[수상횟수] : "+dto.getAwards()+"회");
		System.out.print("[추가사항] : ");
		String str = JobSeekerUtil.restrictLetterCatch(dto.getEtc());
		String[] content=new String[str.length()];
		
		for(int i =0; i<content.length;i++) {
			if(i%50==0) {
				System.out.println();
			}
			content[i]=str.substring(i, i+1);
			try {
				System.out.print(content[i]);
			} catch (Exception e) {
				break;
			}
		}
		line();
	}
		
	/**
	 * 자기소개서 정보를 출력
	 * @param dto 자기소개서 객체
	 */
	public void showIntroduction(IntroductionDTO dto) {
		System.out.println("[이름] : "+JobSeekerUtil.loginJobSeeker.getName());
		System.out.println("[자기소개서명] : "+dto.getName());
		System.out.print("[내용] : ");
		String str=JobSeekerUtil.restrictLetterCatch(dto.getSelfIntroduction());
		
		String[] content=new String[str.length()];
		
		for(int i =0; i<content.length;i++) {
			if(i%50==0) {
				System.out.println();
			}
			content[i]=str.substring(i, i+1);
			try {
				System.out.print(content[i]);
			} catch (Exception e) {
				break;
			}
		}
		System.out.println();
		line();
	}
	
	/**
	 * 공고 정보를 출력
	 * @param dto 공고객체
	 */
	public void showRecruit(RecruitmentDTO dto) {
		System.out.println("[공고제목] : "+dto.getTitle());
		System.out.println("[기업명] : "+dto.getCorpName());
		System.out.println("[직무] : "+dto.getJobName());
		System.out.println("[지원자격] : "+dto.getQualification());
		System.out.println("[등록일] : " +dto.getRegdate());
		System.out.println("[마감일] : "+dto.getEnddate());
		System.out.println("[채용인원] : "+dto.getNum()+" 명[0 : 1-9 | 00 : 10-99]");
		System.out.print("[공고설명] : ");
		String str=JobSeekerUtil.restrictLetterCatch(dto.getOtherDiscription());
		String[] content=new String[str.length()];
		
		for(int i =0; i<content.length;i++) {
			if(i%50==0) {
				System.out.println();
			}
			content[i]=str.substring(i, i+1);
			try {
				System.out.print(content[i]);
			} catch (Exception e) {
				break;
			}
		}
		System.out.println();
		
		line();
	}
	
	/**
	 * 게시물 객체
	 * @param dto 게시판 객체
	 */
	public void showReview(ReviewDTO dto) {
		System.out.println("[작성자] : "+dto.getJobSeekerName());
		System.out.println("[조회수] : "+dto.getViews());
		System.out.print("[내용] : ");
		String str = JobSeekerUtil.restrictLetterCatch(dto.getContent());
		String[] content = new String[str.length()];
		for(int i =0; i<content.length;i++) {
			if(i%50==0) {
				System.out.println();
			}
			content[i]=str.substring(i, i+1);
			try {
				System.out.print(content[i]);
			} catch (Exception e) {
				break;
			}
		}
		System.out.println();
		line();
	}
	/**
	 * 기업정보를 출력
	 * @param dto 기업객체
	 */
	public void showCorpInfo(CorporationDTO dto) {
		System.out.println("[기업명] : "+dto.getName());
		System.out.println("[설립일] : "+dto.getEstablishDate());
		System.out.println("[주소] : "+dto.getAddress());
		System.out.println("[연락처] : "+dto.getTel());
		String temp="";
		if(dto.getEmail()==null) {
			temp="미기재";
		}else {
			temp=dto.getEmail();
		}
		System.out.println("[홈페이지] : "+temp);
		line();
	}

}
