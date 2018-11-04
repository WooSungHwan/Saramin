package saram.in.saramin.corporation;

/**
 * 기업고객의 로그인정보를 가진 클래스
 * @author user
 *
 */
public class CorporationLoginDTO {
	private String corpSeq;
	private String id;
	private String pw;
	private String regdate;
	public String getCorpSeq() {
		return corpSeq;
	}
	public void setCorpSeq(String corpSeq) {
		this.corpSeq = corpSeq;
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
		return "CorporationLoginDTO [corpSeq=" + corpSeq + ", id=" + id + ", pw=" + pw + ", regdate=" + regdate + "]";
	}
	
	
}
