package saram.in.saramin.jobseeker;

/**
 * 개인회원의 로그인 정보를 담은 클래스
 * @author user
 *
 */
public class JobSeekerLoginDTO {
	private String jobSeekerSeq;
	private String id;
	private String pw;
	private String regdate;
	public String getJobSeekerSeq() {
		return jobSeekerSeq;
	}
	public void setJobSeekerSeq(String jobSeekerSeq) {
		this.jobSeekerSeq = jobSeekerSeq;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	@Override
	public String toString() {
		return "JobSeekerLoginDTO [jobSeekerSeq=" + jobSeekerSeq + ", id=" + id + ", pw=" + pw + ", regdate=" + regdate
				+ "]";
	}
	
	
	
	
}
