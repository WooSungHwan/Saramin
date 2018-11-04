package saram.in.saramin.jobseeker;


/**
 * 개인 희망직무 정보를 담은 클래스		
 * @author user
 *
 */
public class HopeJobDTO {
	private String hopeJobSeq;
	private String jobSeekerSeq;
	private String categorySeq;
	public String getHopeJobSeq() {
		return hopeJobSeq;
	}
	public void setHopeJobSeq(String hopeJobSeq) {
		this.hopeJobSeq = hopeJobSeq;
	}
	public String getJobSeekerSeq() {
		return jobSeekerSeq;
	}
	public void setJobSeekerSeq(String jobSeekerSeq) {
		this.jobSeekerSeq = jobSeekerSeq;
	}
	public String getCategorySeq() {
		return categorySeq;
	}
	public void setCategorySeq(String categorySeq) {
		this.categorySeq = categorySeq;
	}
	@Override
	public String toString() {
		return "HopeJobDTO [hopeJobSeq=" + hopeJobSeq + ", jobSeekerSeq=" + jobSeekerSeq + ", categorySeq="
				+ categorySeq + "]";
	}
	
	
	
}
