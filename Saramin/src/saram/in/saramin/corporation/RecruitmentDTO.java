package saram.in.saramin.corporation;

/**
 * 채용공고의 정보를 담은 클래스
 * @author user
 *
 */
public class RecruitmentDTO {
	private String recruitSeq;
	private String corpSeq;
	private String categorySeq;
	private String jobName;
	private String title;
	private String num;
	private String qualification;
	private String otherDiscription;
	private String regdate;
	private String enddate;
	private String corpName;
	
	
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getCorpName() {
		return corpName;
	}
	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}
	public String getRecruitSeq() {
		return recruitSeq;
	}
	public void setRecruitSeq(String recruitSeq) {
		this.recruitSeq = recruitSeq;
	}
	public String getCorpSeq() {
		return corpSeq;
	}
	public void setCorpSeq(String corpSeq) {
		this.corpSeq = corpSeq;
	}
	public String getCategorySeq() {
		return categorySeq;
	}
	public void setCategorySeq(String categorySeq) {
		this.categorySeq = categorySeq;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getQualification() {
		return qualification;
	}
	public void setQualification(String qualification) {
		this.qualification = qualification;
	}
	public String getOtherDiscription() {
		return otherDiscription;
	}
	public void setOtherDiscription(String otherDiscription) {
		this.otherDiscription = otherDiscription;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	@Override
	public String toString() {
		return "RecruitmentDTO [recruitSeq=" + recruitSeq + ", corpSeq=" + corpSeq + ", categorySeq=" + categorySeq
				+ ", jobName=" + jobName + ", title=" + title + ", num=" + num + ", qualification=" + qualification
				+ ", otherDiscription=" + otherDiscription + ", regdate=" + regdate + ", enddate=" + enddate
				+ ", corpName=" + corpName + "]";
	}
	
	
	
}
