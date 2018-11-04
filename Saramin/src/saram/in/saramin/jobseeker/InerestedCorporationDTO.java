package saram.in.saramin.jobseeker;

/**
 * 관심기업 정보를 담은 클래스
 * @author user
 *
 */
public class InerestedCorporationDTO {
	private String icSeq;
	private String jobSeekerSeq;
	private String corpSeq;
	public String getIcSeq() {
		return icSeq;
	}
	public void setIcSeq(String icSeq) {
		this.icSeq = icSeq;
	}
	public String getJobSeekerSeq() {
		return jobSeekerSeq;
	}
	public void setJobSeekerSeq(String jobSeekerSeq) {
		this.jobSeekerSeq = jobSeekerSeq;
	}
	public String getCorpSeq() {
		return corpSeq;
	}
	public void setCorpSeq(String corpSeq) {
		this.corpSeq = corpSeq;
	}
	@Override
	public String toString() {
		return "InerestedCorporationDTO [icSeq=" + icSeq + ", jobSeekerSeq=" + jobSeekerSeq + ", corpSeq=" + corpSeq
				+ "]";
	}
	
	
}
