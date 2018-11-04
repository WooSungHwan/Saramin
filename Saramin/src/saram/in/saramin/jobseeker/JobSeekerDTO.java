package saram.in.saramin.jobseeker;

/**
 * 개인회원의 정보를 담은 클래스
 * @author user
 *
 */
public class JobSeekerDTO {
	private String jobSeekerSeq;
	private String name;
	private String ssn;
	private String tel;
	private String address;
	private String email;
	public String getJobSeekerSeq() {
		return jobSeekerSeq;
	}
	public void setJobSeekerSeq(String jobSeekerSeq) {
		this.jobSeekerSeq = jobSeekerSeq;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSsn() {
		return ssn;
	}
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "JobSeekerDTO [jobSeekerSeq=" + jobSeekerSeq + ", name=" + name + ", ssn=" + ssn + ", tel=" + tel
				+ ", address=" + address + ", email=" + email + "]";
	}
	
	
	
}
