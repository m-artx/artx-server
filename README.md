# ARTX
![Group 1402](https://github.com/m-artx/artx-server/assets/102597172/2a9c1b41-c55c-4f95-ae19-c8de78d89888)

## 프로젝트 소개
누구나 쉽게 작가가 되어 미술품을 판매할 수 있는 플랫폼입니다.

## 업데이트 내역
- [2023-12-16] Github Actions Workflow CI/CD Slack Bot Notification 추가
- [2023-12-06] 이미지 저장 및 응답 속도를 고려하여 이미지 리사이징 구현

## 사용 예시
사용 예시를 보여주는 것이기 때문에 누락된 페이지가 다수 존재할 수 있습니다.

### 1️⃣ 메인 페이지
> 새롭게 등록된 작가의 작품 또는 카테고리별 다양한 작품들을 확인할 수 있습니다.

![image](https://github.com/m-artx/artx-server/assets/102597172/937596de-c95c-4c8b-ae86-188629c1a5ef)

### 2️⃣ 로그인 및 회원가입 페이지
> 해당 페이지 내에서 로그인 및 회원가입을 할 수 있습니다.

![image](https://github.com/m-artx/artx-server/assets/102597172/d2255958-2be8-4547-9c3c-1aba4479f28c)

### 3️⃣ 작품 리스트 페이지
> 등록된 작품들을 모두 확인할 수 있습니다.

![image](https://github.com/m-artx/artx-server/assets/102597172/380055c0-c3a1-4faf-975f-70589c8a14ac)

### 4️⃣ 작품 상세 페이지
> 작품에 대한 상세 정보를 확인할 수 있으며, 장바구니 추가 및 주문 및 결제가 가능합니다.

![image](https://github.com/m-artx/artx-server/assets/102597172/0ebce88a-12bc-4b82-8399-8f1294fa31bb)

### 5️⃣ 장바구니 페이지
> 상세 페이지에서 장바구니 추가를 통해 추가된 작품들을 확인할 수 있습니다.

![image](https://github.com/m-artx/artx-server/assets/102597172/49aa3e21-c6f7-4beb-8ce5-d802f08aeed3)

### 6️⃣ 주문 및 배송 페이지
> 주문이 완료된 상품들의 정보를 확인할 수 있습니다.

![image](https://github.com/m-artx/artx-server/assets/102597172/e5ccf204-0b8b-46d3-9900-c287c14f2c4d)

## 사용 기술

### Back-end
- Go(Email Server)
- Java 17
- Spring Boot 3.1.5
- JPA(Hibernate)
- Spring Security
- JWT Token

### Infra
- Docker
- Oracle Cloud A1
- Github Actions
- Krampoline(Web IDE)
- D2Hub
- Kargo
- MySQL

## 프로젝트 구성
![pj](https://github.com/m-artx/artx-server/assets/102597172/86b544ab-d275-4560-8e4f-6f3e42fd69af)

## 빌드 및 배포

### 1. Krampoline 빌드 및 배포(2023-11-28 이전)
![image](https://github.com/m-artx/artx-server/assets/102597172/f1b1298e-979d-4897-92a8-f7887f22cf33)


### 2. Github Actions CI/CD + 클라우드 인스턴스 환경 빌드 및 배포(2023-11-28 이후)
![image](https://github.com/m-artx/artx-server/assets/102597172/308fe5aa-b113-4039-a470-524c7486ad41)

## ERD 설계
![Group 2](https://github.com/m-artx/artx-server/assets/102597172/2631f8ec-7314-4c17-a54f-df989e696cae)

## API 명세서

### 회원 도메인
  
  1)   [x] 회원가입 또는 로그인을 할 수 있습니다.
       - [x] 회원가입 시 이메일, 아이디 등 유효성 검증을 실시합니다.
       - [x] 패스워드는 암호화를 거친 후 저장이 됩니다.
       - [x] 아이디 찾기, 비밀번호 초기화할 수 있습니다.
       - [x] 로그인 시 JWT Access Token 및 Refresh Token을 제공하여 Stateless 상태를 유지합니다.
       - [x] JWT Token 유효기간 만료시 재발급 요청을 할 수 있습니다.
  2)   [x] 마이페이지를 통해 유저 정보를 수정할 수 있습니다.
       - [x] 프로필 이미지를 등록 및 변경할 수 있습니다.
       - [x] 패스워드, 주소지 등록, 변경 및 기본 주소지를 변경할 수 있습니다.
  3)   [x] 모든 유저는 권한을 부여받습니다.
       - [x] 회원가입과 로그인 그리고 조회 관련 API를 제외한 대부분의 API는 인가 및 권한을 거칩니다.
       - [x] 권한 신청 및 조회를 할 수 있습니다.
  4)   [x] SMTP 프로토콜 서비스를 이용하여 이메일을 전송할 수 있습니다.
  5)   [x] 인증을 위한 이메일을 전송할 수 있습니다.
  6)   [x] 패스워드 초기화 시 새로운 패스워드를 만들어 이메일을 전송할 수 있습니다.

 ### 작품 도메인

  1)   [x] 모든 작가는 작품을 등록, 수정 또는 삭제할 수 있습니다.
  2)   [x] 모든 회원은 작품을 조회할 수 있습니다.
       - [x] 신규 작가 및 인기 작가의 작품 조회할 수 있습니다.
       - [x] 작품 조회는 카테고리, 유저 및 작품명으로 페이지 조회할 수 있습니다.
       - [x] 작품 조회시 작품 조회수 카운트가 누적시킨 후 일괄적으로 적용시킵니다.

 ### 주문 도메인

  1)   [x] 모든 사용자는 작품을 주문할 수 있습니다.
       - [x] 재고 상태 변경의 동시성 문제를 해결하기 위해 Optimisstic Lock을 적용합니다.
  2)   [x] 최근 주문 날짜를 기준으로 조회를 할 수 있습니다.
  3)   [x] 주문 상태, 배송 상태에 따라 취소 가능 여부가 결정됩니다
       - [x] 주문은 준비 또는 완료 상태의 경우에만 취소가 가능합니다.
       - [x] 배송은 배송 준비 상태의 경우에만 취소가 가능합니다.
  4)   [x] 모든 작가는 주문된 상품들을 조회할 수 있습니다.

 ### 결제 도메인

  1)   [x] 카카오페이를 통해 결제를 할 수 있습니다.
  2)   [x] 결제 상태를 관리합니다.
  4)   [x] 결제 취소는 주문, 배송 상태에 따라 부분 취소 및 전체 취소가 가능합니다.
       - [x] 주문은 준비 또는 완료 상태의 경우에만 취소가 가능합니다.
       - [x] 배송은 준비 상태의 경우에만 취소가 가능합니다.

 ### 관리자 도메인

  1) [x] 통계 정보를 확인할 수 있습니다.
       - [x] 일간, 월간, 연간 주문 총 카운트 및 증감 카운트
       - [x] 새로운 유저 및 신규 전환 작가
  2) [x] 배너를 등록할 수 있습니다.
  3) [x] 권한 요청을 조회하고 승인 및 거절할 수 있습니다.
  4) [x] 카테고리를 생성할 수 있습니다.
  5) [x] 공지사항 게시글을 생성할 수 있습니다.
  6) [x] 회원을 조회할 수 있습니다.


