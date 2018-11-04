package saram.in.saramin.util;

import java.util.ArrayList;
import java.util.Iterator;

import saram.in.saramin.jobseeker.IntroductionDTO;
import saram.in.saramin.jobseeker.JobSeekerDAO;
import saram.in.saramin.jobseeker.JobSeekerDTO;
import saram.in.saramin.jobseeker.ResumeDTO;

/**
 * 개인 구직자의 유틸과 관련된 정보 클래스
 * @author woo
 *
 */
public class JobSeekerUtil {
	
	
	
	
	//로그아웃시 반드시 null 해줘야할 변수들
	/**
	 * 현재 로그인한 개인의 정보를 담을 변수
	 */
	public static JobSeekerDTO loginJobSeeker;
	/**
	 * 로그인한 개인의 이력서리스트
	 */
	public static ArrayList<ResumeDTO> ResumeList;
	/**
	 * 로그인한 개인의 자기소개서 리스트
	 */
	public static ArrayList<IntroductionDTO> introList;
	/**
	 * 대표 이력서 객체
	 */
	public static ResumeDTO myRepResume;
	/**
	 * 대표 자기소개서 객체
	 */
	public static IntroductionDTO myRepIntroduction; 
	/**
	 * 로그인한 개인의 대표 문서번호
	 */
	public static String myRepSeq;
	
	public static String restrictLetterCatch(String content) {
		JobSeekerDAO jdao=new JobSeekerDAO();
		ArrayList<String> restrictLetter = jdao.getRestrictLetter();
		Iterator<String> iter = restrictLetter.iterator();
		while(iter.hasNext()) {
			String letter = iter.next();
			if(content.contains(letter)){
				content = content.replace(letter, "*** ");
			}
		}
		return content;
	}
	
	
}
















