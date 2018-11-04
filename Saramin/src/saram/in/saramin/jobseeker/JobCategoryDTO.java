package saram.in.saramin.jobseeker;

/**
 * 직무 정보를 담은 클래스
 * @author user
 *
 */
public class JobCategoryDTO {
	private String categorySeq;
	private String jobName;
	public String getCategorySeq() {
		return categorySeq;
	}
	public void setCategorySeq(String categorySeq) {
		this.categorySeq = categorySeq;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	@Override
	public String toString() {
		return "JobCategoryDTO [categorySeq=" + categorySeq + ", jobName=" + jobName + "]";
	}
	
	
	
}
