# ✨Caching Server

**IDE** - IntelliJ

**Spring** - 3.1.6

**MySql** - 8.0.32

**Docker(Redis:latest)** Docker Official Image - 7.2.3


---

### Folder Structure

- 계층형 패키지 구조

- `src`: 메인 로직
  `src`에는 도메인 별로 패키지를 구성하도록 했다. **도메인**이란 회원(User), 게시글(Post), 댓글(Comment), 주문(Order) 등 소프트웨어에 대한 요구사항 혹은 문제 영역이라고 생각하면
  된다.

> `Config` - `Controller` - `Service` - `DAO` - `DTO` - `Repository` - `Entity`

- `common` 및 `util` 폴더: 메인 로직은 아니지만 `src` 에서 필요한 부차적인 파일들을 모아놓은 폴더

> `BaseException` , `BaseResponse` , `BaseResponseStatus` ,  `Constant`

---

### Naming Convention

* Plugin : saveAction
    * 불필요한 import 자동 삭제
    * 탭 사이즈, 공백, 주석컨벤션등 사용하기 편함

---

### Request & Response Process

![img.png](src%2Fmain%2Fresources%2Freadme%2Fimg%2Fimg.png)


