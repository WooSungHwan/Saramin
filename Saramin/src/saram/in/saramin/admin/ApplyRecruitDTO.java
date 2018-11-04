package saram.in.saramin.admin;

/**
 * 공고에 지원한 정보를 담은 클래스
 * @author user
 *
 */
public class ApplyRecruitDTO {
	private String applySeq;
	private String repSeq;
	private String recruitSeq;
	public String getApplySeq() {
		return applySeq;
	}
	public void setApplySeq(String applySeq) {
		this.applySeq = applySeq;
	}
	public String getRepSeq() {
		return repSeq;
	}
	public void setRepSeq(String repSeq) {
		this.repSeq = repSeq;
	}
	public String getRecruitSeq() {
		return recruitSeq;
	}
	public void setRecruitSeq(String recruitSeq) {
		this.recruitSeq = recruitSeq;
	}
	@Override
	public String toString() {
		return "ApplyRecruitDTO [applySeq=" + applySeq + ", repSeq=" + repSeq + ", recruitSeq=" + recruitSeq + "]";
	}
	
	
}
