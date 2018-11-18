# 구인구직 관리운영 프로그램
<b>기간 : 2018.10.20 ~ 2018.10.27</b><br>
<b>환경 : 콘솔 기반 Eclipse</b><br>
<b>주제 : 구인구직</b><br>
<b>개발 : Eclipse Photon, Oracle</b><br>
<b>사용 언어 : JAVA(JDK 1.8), Oracle SQL(11g Express)</b><br>
<b>사용 기술 : JDBC + MVC모델</b><br>

# 프로젝트 상세내용
## 주제
<pre>
- JDBC, MVC 모델을 활용한 구인구직 운영관리 시스템
</pre>

## 목적
<pre>
- 개인회원(구직자)과 기업회원(구인자)에 대한 통합 관리<br>
- 구직자, 구인자의 원활한 구인구직 및 관리
</pre>

## 구현 목표
* 관리자
<pre>
- 게시판 관리<br>
- 프로그램 전반 접근 권한을 가지며, 이를 통한 자유로운 조회, 등록, 수정 삭제 용이
</pre>
* 개인 회원
<pre>
- 자신의 이력 및 자기소개를 담은 서류 작성<br>
- 구인공고 지원 및 스크랩 기능
</pre>
* 기업 회원
<pre>
- 구인자 현황조회 및 구인공고 구현
</pre>

## 사용대상
* 관리자
<pre>
1. 개인회원 관리<br>
2. 기업회원 관리<br>
3. 게시판 관리<br>
</pre>
* 개인회원
<pre>
1. 이력서 관리<br>
2. 자기소개서 관리<br>
3. 채용공고 보기<br>
4. 후기게시판<br>
5. 내 관심기업<br>
</pre>
* 기업회원
<pre>
1. 채용공고 관리<br>
2. 관심인재 관리<br>
3. 공개문서 조회<br>
4. 추천인재 조회<br>
</pre>

## Exerd 테이블 명세
![Alt text](https://github.com/WooSungHwan/Saramin/blob/master/%EB%8D%B0%EC%9D%B4%ED%84%B0%20%EC%84%A4%EA%B3%84%EB%8F%84.png)

## 구동 화면
* 회원가입 완료<br><br>
![Alt text](https://github.com/WooSungHwan/Saramin/blob/master/7.%20%ED%99%94%EB%A9%B4%EC%BA%A1%EC%B2%98/%EB%A9%94%EC%9D%B8/%EA%B0%9C%EC%9D%B8%ED%9A%8C%EC%9B%90%20%ED%9A%8C%EC%9B%90%EA%B0%80%EC%9E%85%20%EC%99%84%EB%A3%8C.PNG)
* 개인 회원ID/PW 찾기<br><br>
![Alt text](https://github.com/WooSungHwan/Saramin/blob/master/7.%20%ED%99%94%EB%A9%B4%EC%BA%A1%EC%B2%98/%EB%A9%94%EC%9D%B8/idpw%EC%B0%BE%EA%B8%B0%ED%8E%98%EC%9D%B4%EC%A7%80.png)
* 채용공고 조회 및 관심기업 등록[개인회원]<br><br>
![Alt text](https://github.com/WooSungHwan/Saramin/blob/master/7.%20%ED%99%94%EB%A9%B4%EC%BA%A1%EC%B2%98/%EA%B0%9C%EC%9D%B8%ED%9A%8C%EC%9B%90/%ED%95%B4%EB%8B%B9%EA%B3%B5%EA%B3%A0%EB%82%B4%EC%9A%A9%26%EA%B4%80%EC%8B%AC%EA%B8%B0%EC%97%85%EB%93%B1%EB%A1%9D.PNG)
* 대표문서 공개 설정[개인회원]<br><br>
![Alt text](https://github.com/WooSungHwan/Saramin/blob/master/7.%20%ED%99%94%EB%A9%B4%EC%BA%A1%EC%B2%98/%EA%B0%9C%EC%9D%B8%ED%9A%8C%EC%9B%90/%EB%8C%80%ED%91%9C%EB%AC%B8%EC%84%9C%20%EA%B3%B5%EA%B0%9C%20%EC%84%A4%EC%A0%95.PNG)
* 새 공고 작성[기업회원]<br><br>
![Alt text](https://github.com/WooSungHwan/Saramin/blob/master/7.%20%ED%99%94%EB%A9%B4%EC%BA%A1%EC%B2%98/%EA%B8%B0%EC%97%85%ED%9A%8C%EC%9B%90/%EC%83%88%EA%B3%B5%EA%B3%A0%EC%9E%91%EC%84%B1.png)

 
 
 
