package saram.in.saramin.corporation;

/**
 * 기업관련 정보를 저장하는 클래스
 * @author user
 *
 */
public class CorporationDTO {
	private String corpSeq;
	private String name;
	private String ssn;
	private String tel;
	private String address;
	private String email;
	private String establishDate;
	public String getCorpSeq() {
		return corpSeq;
	}
	public void setCorpSeq(String corpSeq) {
		this.corpSeq = corpSeq;
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
	public String getEstablishDate() {
		return establishDate;
	}
	public void setEstablishDate(String establishDate) {
		this.establishDate = establishDate;
	}
	@Override
	public String toString() {
		return "CorporationDTO [corpSeq=" + corpSeq + ", name=" + name + ", ssn=" + ssn + ", tel=" + tel + ", address="
				+ address + ", email=" + email + ", establishDate=" + establishDate + "]";
	}
	
	
}
