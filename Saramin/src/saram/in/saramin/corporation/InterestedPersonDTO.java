package saram.in.saramin.corporation;

/**
 * 기업이 저장한 관심인재의 정보를 담은 클래스
 * @author user
 *
 */
public class InterestedPersonDTO {
	private String ipSeq;
	private String corpSeq;
	private String jobSeekerSeq;
	public String getIpSeq() {
		return ipSeq;
	}
	public void setIpSeq(String ipSeq) {
		this.ipSeq = ipSeq;
	}
	public String getCorpSeq() {
		return corpSeq;
	}
	public void setCorpSeq(String corpSeq) {
		this.corpSeq = corpSeq;
	}
	public String getJobSeekerSeq() {
		return jobSeekerSeq;
	}
	public void setJobSeekerSeq(String jobSeekerSeq) {
		this.jobSeekerSeq = jobSeekerSeq;
	}
	@Override
	public String toString() {
		return "InterestedPersonDTO [ipSeq=" + ipSeq + ", corpSeq=" + corpSeq + ", jobSeekerSeq=" + jobSeekerSeq + "]";
	}
	
	
}
