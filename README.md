# WatchaPedia (왓챠피디아)

방대한 영화·드라마·웹툰·책 평점 데이터와 사용자 취향을 기록하고 
추천하는 통합 웹 플랫폼입니다.

## 팀 프로젝트 안내
쌍용교육센터 Java Full-Stack 개발자 양성과정 최종 프로젝트로, 
4인 팀으로 진행했습니다. (작업기간: 2026.02.13 ~ 2026.03.05)

## 담당 역할 (다원)
- 프로젝트 팀장: 일정 관리 및 업무 분담 주도
- DB 설계: eXERD 기반 ERD 설계, 관계형 테이블 설계
- 회원 인증 시스템: Spring Security 기반 회원가입/로그인
- 사용자 인터랙션 기능: 평점, 댓글, 좋아요
- 마이페이지: 개인 보관함, 문의 게시판

## 기술 스택
- Back-end: Java, Spring Boot, Spring Data JPA, Spring Security, Lombok
- Front-end: HTML5, CSS, JavaScript, Thymeleaf
- Database: MySQL, eXERD
- Tools: GitHub, Notion, STS

## 트러블슈팅
초반엔 엔티티 간 연관관계 설계가 명확하지 않아 기능이 늘수록 
로직 중복과 조회 복잡도가 커지는 문제를 겪었습니다. JPA 연관관계 
매핑을 재점검하고 자주 쓰이는 로직을 공통으로 분리하며 문제를 
해결했습니다.
