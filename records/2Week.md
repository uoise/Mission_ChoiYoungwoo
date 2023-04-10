# [2Week] 최영우

## 미션 요구사항 분석 & 체크리스트

---

- [ ] `likeablePerson` constraints
    - [x] change `attractiveTypeCode` to enum `attractiveTypeCode`
    - [ ] add method can modify exist `likeablePerson`
    - [x] `fromMember` must like `toMember` only once
        - [x] testcase
        - [x] modify exist `likeablePerson`
    - [ ] `fromMember` must `like` less than **LIMIT(11)**
        - [ ] testcase
        - [ ] `count(*)` from `likeablePerson` where `fromMemberId` = `?`;
- [ ] Oauth
    - [ ] naver

---

- [ ] `@PreAuthorize` exception
    - [ ] testcase
    - [ ] custom handler
        - [ ] 4xx page custom
        - [ ] `redirect:/`
        - [ ] redirect to browser recent page
- [ ] service TestCases
- [ ] QueryDSL
    - [ ] search
- [ ] when register
    - [ ] send connected email
- [ ] find id
- [ ] find email
    - [ ] email a temporary password
- [ ] social login with profile image
- [ ] notification
    - [ ] when someone like you

## N주차 미션 요약

---

**[접근 방법]**

**[특이사항]**

