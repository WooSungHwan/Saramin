package saram.in.saramin.jobseeker;

/**
 * 개인의 이력서의 이력정보를 담은 클래스
 * @author user
 *
 */
public class ResumeDTO {
	private String resumeSeq;
	private String jobSeekerSeq;
	private String toeic;
	private String volunteer;
	private String Intern;
	private String Awards;
	private String name;
	private String etc;
	
	public String getEtc() {
		return etc;
	}
	public void setEtc(String etc) {
		this.etc = etc;
	}
	public String getResumeSeq() {
		return resumeSeq;
	}
	public void setResumeSeq(String resumeSeq) {
		this.resumeSeq = resumeSeq;
	}
	public String getJobSeekerSeq() {
		return jobSeekerSeq;
	}
	public void setJobSeekerSeq(String jobSeekerSeq) {
		this.jobSeekerSeq = jobSeekerSeq;
	}
	public String getToeic() {
		return toeic;
	}
	public void setToeic(String toeic) {
		this.toeic = toeic;
	}
	public String getVolunteer() {
		return volunteer;
	}
	public void setVolunteer(String volunteer) {
		this.volunteer = volunteer;
	}
	public String getIntern() {
		return Intern;
	}
	public void setIntern(String intern) {
		Intern = intern;
	}
	public String getAwards() {
		return Awards;
	}
	public void setAwards(String awards) {
		Awards = awards;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "ResumeDTO [resumeSeq=" + resumeSeq + ", jobSeekerSeq=" + jobSeekerSeq + ", toeic=" + toeic
				+ ", volunteer=" + volunteer + ", Intern=" + Intern + ", Awards=" + Awards + ", name=" + name + ", etc="
				+ etc + "]";
	}
	
	
	
}
