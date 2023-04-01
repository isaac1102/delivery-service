# delivery-service

## API 명세
- 회원가입 - `POST /api/v1/users/signup`
  - param 
    - `SignUpRequest(userId, password, name)`
  - return 
    - `String`
- 로그인 - `POST /api/v1/users/login`
  - param 
    - `LoginRequest(userId, password)`
  - return 
    - `String`
- 배달조회 - `GET /api/v1/delivery`
  - param 
    - `DeliveryRequest(startDate, endDate)` 
  - return
    - `List<DeliveryResponse>`
- 배달주문 수정 - `PATCH /api/v1/delivery/{id}`
  - param 
    - `id, userId, destination` 
  - return 
    - `none`
      
## 실행환경
- `Java 17`, `MySQL 8.0.32`, `SpringBoot 3.0.5`
- MySQL이 default port(3306)로 실행중이어야 어플리케이션 실행이 가능합니다.

## 테스트 데이터
- AppRunner를 통해 application 실행 시에 테스트용 데이터가 생성됩니다
- `user_test`, `delivery_test` 테이블이 생성됩니다.
  - `user_test`테이블의 데이터를 가지고 회원가입, 로그인 기능을 테스트 하시면 됩니다. 
  - `delivery_test`테이블의 데이터를 가지고 배달조회, 배달주문정보 수정 기능을 테스트하실 수 있습니다. 