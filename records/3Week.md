# 3Week_최영우.md

## Title: [3Week] 최영우

### 미션 요구사항 분석 & 체크리스트

---

- [x] Deploy 
  - [x] NCP
  - [x] AGP setting
  - [x] CentOS
    - [x] Docker
    - [x] MariaDB
    - [x] nginx 
      - [x] [Domain](https://www.uoise.xyz)
      - [x] HTTPS
- [x] Modifying like has a cool time of 3 hours
  - [x] humanReadable
  - [x] validation
- [ ] Notification
  - [x] event
  - [x] event listener
  - [ ] sse to send event

### 3주차 미션 요약

---

**[접근 방법]**

- likeablePerson 생성, 수정 시 이벤트로 Notification 생성
- Notification 생성시, SSE 사용하여 변경사항 전파
  - 클라이언트의 rq.getInstaMember 로 toInstaMember 와 동일하다면 전송

**[특이사항]**

- sse 수신 받는 과정에서 문제 발생
- sse 이벤트 생성후 기존 항목을 무한히 반복해서 전송하는 것으로 보임
  - /sse/notification/unread 가 너무 내용이 많아 json 이 잘린 형태로 전송 됨
  - 기존 항목을 보냈다면 다시 보내지 않는 검증하는 로직이 필요
    - lastPublishedDate 나 boolean 값으로 list 에서 검증 하는 식으로 테스트 예정