package saram.in.saramin.jobseeker;

/**
 * 자기소개서 정보를 담은 클래스
 * @author user
 *
 */
public class IntroductionDTO {
	private String introSeq;
	private String jobSeekerSeq;
	private String selfIntroduction;
	private String name;
	public String getIntroSeq() {
		return introSeq;
	}
	public void setIntroSeq(String introSeq) {
		this.introSeq = introSeq;
	}
	public String getJobSeekerSeq() {
		return jobSeekerSeq;
	}
	public void setJobSeekerSeq(String jobSeekerSeq) {
		this.jobSeekerSeq = jobSeekerSeq;
	}
	public String getSelfIntroduction() {
		return selfIntroduction;
	}
	public void setSelfIntroduction(String selfIntroduction) {
		this.selfIntroduction = selfIntroduction;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "IntroductionDTO [introSeq=" + introSeq + ", jobSeekerSeq=" + jobSeekerSeq + ", selfIntroduction="
				+ selfIntroduction + ", name=" + name + "]";
	}
	
}
