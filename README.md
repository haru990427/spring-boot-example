# SpringBoot-example

## 개요
**SpringBoot**를 사용하여 기초적인 시스템 개발 (CRUD)

평소에 개발해보고 싶었던 기능 개발


## 환경
* [Java](https://adoptium.net/temurin/releases) 21.0.8 (Eclipse Adoptium)
  > 추후 25+ 업데이트 예정
  * [Spring Boot](https://spring.io/projects/spring-boot) 3.4.4
    >   4.0+ 업데이트 예정
    
* [MySQL](https://dev.mysql.com/downloads/mysql/) 8.4.7

## 설치 방법
*Window 기준으로 설명합니다.*



## 개발 기능
**순서와 별개로 개발될 수 있습니다.**


- [ ] **모델링** 
  - [x] User
  - [ ] Admin
  - [ ] 추후 추가
    > 추가되면서 기존 모델링 변경 가능
    >> 생성된 것을 기준으로 체크 표시 

- [ ] **자체 로그인 구현**
  - [ ] JWT 사용
  - [ ] 유저 권한 설정

- [ ] **소셜 로그인 구현**
  - [ ] Oauth 2.0 사용
  - [ ] JWT 사용
  - [ ] 유저 권한 설정
  - [ ] 기존 회원 로그인 통합

- [ ] **보안 설정**
  - [ ] brute-force 방지 (회원가입 및 로그인)
  - [ ] 로그인 실패 원인 제공 X (ID, PW 뭐가 틀렸는지 X)
  - [ ] JWT 보안 BlackList 기법 사용
  - [ ] 유저 권한에 맞게 서비스 제공 (API 접근 차단)

- [ ] **CI/CD**
  - [ ] AWS 사용하여 서버 / DB 서버 / 추후 AI 서버 분리
    > 비용은..? 좀 더 고민 
  - [ ] Slack 배포 알림 설정

- [ ] **개발 고민 기능에서 추가 예정**


## 개발 예정 기능
**순서와 별개로 개발될 수 있습니다.**

**1. 결제 시스템**

**2. AI 연계 시스템**
  * 외부 AI 연동? (ChatGPT, Grok 등)
  * 내부 AI 서버 사용 <- 시간 오래 걸릴 듯

**3. 특정 데이터 크롤링 (우선 공공기관 데이터)**

**4. 모바일 푸쉬 알림**

**5. 이메일 인증 시스템**

