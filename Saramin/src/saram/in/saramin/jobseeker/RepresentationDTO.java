package saram.in.saramin.jobseeker;

/**
 * 대표문서의 정보들을 담은 클래스
 * @author user
 *
 */
public class RepresentationDTO {
	private String repSeq;
	private String resumeSeq;
	private String introSeq;
	private String jobSeekerSeq;
	private String jobSeekerName;
	private String resumeTitle;
	private String jobName;
	private String introTitle;
	private String Visibility;
	private String volun;
	private String inturn;
	private String toeic;
	private String introContent;
	private String awards;
	
	
	
	public String getAwards() {
		return awards;
	}
	public void setAwards(String awards) {
		this.awards = awards;
	}
	public String getIntroContent() {
		return introContent;
	}
	public void setIntroContent(String introContent) {
		this.introContent = introContent;
	}
	public String getVolun() {
		return volun;
	}
	public void setVolun(String volun) {
		this.volun = volun;
	}
	public String getInturn() {
		return inturn;
	}
	public void setInturn(String inturn) {
		this.inturn = inturn;
	}
	public String getToeic() {
		return toeic;
	}
	public void setToeic(String toeic) {
		this.toeic = toeic;
	}
	
	public String getJobSeekerName() {
		return jobSeekerName;
	}
	public void setJobSeekerName(String jobSeekerName) {
		this.jobSeekerName = jobSeekerName;
	}
	public String getResumeTitle() {
		return resumeTitle;
	}
	public void setResumeTitle(String resumeTitle) {
		this.resumeTitle = resumeTitle;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getIntroTitle() {
		return introTitle;
	}
	public void setIntroTitle(String introTitle) {
		this.introTitle = introTitle;
	}
	public String getRepSeq() {
		return repSeq;
	}
	public void setRepSeq(String repSeq) {
		this.repSeq = repSeq;
	}
	public String getResumeSeq() {
		return resumeSeq;
	}
	public void setResumeSeq(String resumeSeq) {
		this.resumeSeq = resumeSeq;
	}
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
	public String getVisibility() {
		return Visibility;
	}
	public void setVisibility(String visibility) {
		Visibility = visibility;
	}
	@Override
	public String toString() {
		return "RepresentationDTO [repSeq=" + repSeq + ", resumeSeq=" + resumeSeq + ", introSeq=" + introSeq
				+ ", jobSeekerSeq=" + jobSeekerSeq + ", Visibility=" + Visibility + "]";
	}
	
	
	
}
