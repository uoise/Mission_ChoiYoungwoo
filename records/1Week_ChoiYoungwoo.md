# [1Week] 최영우

## 미션 요구사항 분석 & 체크리스트

---

- [x] `likeablePerson` delete
  - [x] testcase 
  - [x] findById
  - [x] delete
  - [x] service
  - [x] controller
- [x] Google Oauth
  - [x] generate google project
  - [x] add `registration.google` at `oauth.yml`

---
### 보완점

- [x] Oauth
  - [x] oauth icons
- [ ] `likeablePerson` constraints
  - [X] `fromMember` must like `toMember` only once
    - [x] testcase
    - [x] modify exist `likeablePerson`
  - [ ] `fromMember` must `like` less than **LIMIT**
    - [ ] testcase
    - [ ] `count(*)` from `likeablePerson` where `fromMemberId` = `?`;
- [ ] `@PreAuthorize` exception
  - [ ] testcase
  - [ ] custom handler
    - [ ] 4xx page custom
    - [ ] `redirect:/`
    - [ ] redirect to browser recent page

## 1주차 미션 요약

---

### `LikeablePersonService`

- 기존안 `public RsData<Boolean> delete(LikeablePerson likeablePerson, Member fromMember)`
  - 장점: `fromMember` 와 `fromInstaMember` 가 동일한지 검증
  - 단점: `LikeablePersonService` 에서 `Member` 사용
- 개선안 `public RsData<Boolean> delete(LikeablePerson likeablePerson, InstaMember fromInstaMember)`
  - 장점: `InstaMember` 를 사용해 삭제 로직만을 수행

### reference

- [connect google oauth to spring](https://lotuus.tistory.com/79)
- [batch insert for IDENTITY generation](https://docs.jboss.org/hibernate/orm/5.4/userguide/html_single/Hibernate_User_Guide.html#identifiers-generators-identity)

### 특이사항

---

- OAuth TestCase
  - Mocking 객체 활용해야 함
  - 진행하지 않음
- 서비스는 요청 실패 리턴, HttpStatusCode 2xx 리턴
  - `Rq` 에서 HttpStatus 추가 필요