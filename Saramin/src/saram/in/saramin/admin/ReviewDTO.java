package saram.in.saramin.admin;

/**
 * 게시판정보를 담은 클래스
 * @author user
 *
 */
public class ReviewDTO {
	private String reviewSeq;
	private String jobSeekerSeq;
	private String jobSeekerName;
	private String views;
	private String content;
	private String regdate;
	
	
	
	public String getJobSeekerName() {
		return jobSeekerName;
	}
	public void setJobSeekerName(String jobSeekerName) {
		this.jobSeekerName = jobSeekerName;
	}
	public String getViews() {
		return views;
	}
	public void setViews(String views) {
		this.views = views;
	}
	public String getReviewSeq() {
		return reviewSeq;
	}
	public void setReviewSeq(String reviewSeq) {
		this.reviewSeq = reviewSeq;
	}
	public String getJobSeekerSeq() {
		return jobSeekerSeq;
	}
	public void setJobSeekerSeq(String jobSeekerSeq) {
		this.jobSeekerSeq = jobSeekerSeq;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	@Override
	public String toString() {
		return "ReviewDTO [reviewSeq=" + reviewSeq + ", jobSeekerSeq=" + jobSeekerSeq + ", jobSeekerName="
				+ jobSeekerName + ", views=" + views + ", content=" + content + ", regdate=" + regdate + "]";
	}
	
	
	
}
