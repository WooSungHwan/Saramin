package saram.in.saramin.admin;

/**
 * 제한글자에 관한 정보를 담은 클래스
 * @author user
 *
 */
public class RestrictDTO {
	private String restrictSeq;
	private String restrictLetter;
	public String getRestrictSeq() {
		return restrictSeq;
	}
	public void setRestrictSeq(String restrictSeq) {
		this.restrictSeq = restrictSeq;
	}
	public String getRestrictLetter() {
		return restrictLetter;
	}
	public void setRestrictLetter(String restrictLetter) {
		this.restrictLetter = restrictLetter;
	}
	@Override
	public String toString() {
		return "RestrictDTO [restrictSeq=" + restrictSeq + ", restrictLetter=" + restrictLetter + "]";
	}
	
	
}
