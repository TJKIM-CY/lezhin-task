# README

## 1.프로젝트 정의

- `lezhin-task`라는 이름의 Spring Boot 애플리케이션으로, 유저 및 작품 관리 기능을 제공. 
- 주요 기능 :  작품 조회 및 관리 API.

## 2.개발환경

### - 개발 환경

- **Java 17**
- **Spring Boot 3.2**
- **Gradle**
- **H2 DB**
- **Mybatis**
- **IntelliJ**
- **Swagger**

### - 설정 방법

- **Git**

   ```bash
   https://github.com/TJKIM-CY/lezhin-task.git
   ```

- **H2 콘솔 접속**

  - **URL**: `http://localhost:8080/h2-console`
  - **JDBC URL**: `jdbc:h2:mem:lezhindb`
  - **사용자명**: `sa`
  - **비밀번호**: `1234`


- **Swagger 접속**

  - **URL**: `http://localhost:8080/swagger-ui/index.html`
  - **회원가입**, 로그인 제외한 API는 인증 필요
  - API 실행 : **로그인** > token Authorize > API 테스트

## 3.API 정의

### - 주요 API

- **작품 조회 이력**
  - **Endpoint**: `GET /api/content/view-history`
  - **설명**: 유저가 조회한 작품의 이력

- **인기 작품 상위 10개**
  - **Endpoint**: `GET /api/content/top-view`
  - **설명**: 조회수가 많은 상위 10개의 작품

- **작품 구매**
  - **Endpoint**: `POST /api/content/purchase`
  - **설명**: 유저 작품 구매

- **작품 및 조회 이력 삭제**
  - **Endpoint**: `DELETE /api/content/{contentId}`
  - **설명**: 특정 작품과 해당 작품의 조회 이력 삭제

- **구매 인기 작품 상위 10개 조회**
  - **Endpoint**: `GET /api/content/top-purchase`
  - **설명**: 구매수가 많은 상위 10개의 작품을 조회

- **작품 리스트 조회**
  - **Endpoint**: `GET /api/content`
  - **설명**: 전체 작품 리스트 조회

- **회원가입**
  - **Endpoint**: `POST /api/auth/signup`
  - **설명**: 유저 회원 가입

- **로그인**
  - **Endpoint**: `POST /api/auth/login`
  - **설명**: 유저 로그인 (토큰 발급)

## 4.API 실행 방법

API는 Swagger UI를 통해 쉽게 테스트 가능. Swagger UI는 다음 URL에서 접근 : `http://localhost:8080/swagger-ui.html`

## 5.개발한 코드에 대해 참고할 수 있는 내용

### - DB

- **`schema.sql`**: 데이터베이스 테이블 및 제약 조건 정의
  - `USERS`, `CONTENT`, `CONTENT_FREE_PROMOTION`, `CONTENT_VIEW_HISTORY`, `CONTENT_PURCHASE` 테이블 정의.

- **`data.sql`**: 임시 데이터

### - 기본 구조

- content와 user의 기능으로 패키지가 나눠져 있으며 controller > service > mapper > sql 로 로직이 실행.

### - 인증

- Spring security, JWT를 사용하여 인증

### - 예외 처리

- GlobalExceptionHandler로 전역 예외 처리

### - 캐싱

- Spring 캐시 @Cacheable 사용

### - API 요청 제한

- Bucket4j 라이브러리 사용하여 API 요청 제한 1분 100건

### - Response 구조

```json
{
  "code": "string",
  "message": "string",
  "data": [
    {
      "string": "string"
    }
  ]
}
```

## 6.고려했던 상황과 해결 방안

### - 데이터 무결성 문제

- **문제** : `CONTENT` 테이블의 레코드를 삭제할 때 외래 키 제약 조건으로 인해 에러가 발생. 개발 요건처럼 CONTENT 테이블과 CONTENT_VIEW_HISTORY 테이블의 데이터만 삭제 하고 싶었지만 CONTENT_PURCHASE, CONTENT_FREE_PROMOTION 테이블 외래키 제약조건으로 삭제 불가 현상.
- **해결 방안** : 소프트 삭제를 고민하다가 CONTENT_PURCHASE, CONTENT_FREE_PROMOTION 테이블에 `ON DELETE SET NULL` 옵션을 사용하여 외래 키 컬럼을 `NULL`로 설정함으로써 참조 무결성을 유지하면서 레코드를 삭제할 수 있도록 변경.

### - 성능 최적화

- **캐싱** : `@Cacheable` 어노테이션을 사용하여 자주 조회되는 데이터에 대해 캐싱을 적용. 데이터베이스 부하를 줄이고 응답 속도를 개선.

### - DB 변경

- MySQL로 개발을 진행도중 개발 생산성 및 외부에서 프로젝트 설정 시 MySQL 설치의 번거로움이 있어 H2 DB로 변경.