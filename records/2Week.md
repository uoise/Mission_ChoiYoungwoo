# [2Week] 최영우

## 미션 요구사항 분석 & 체크리스트

---

- [x] `likeablePerson` constraints
    - [x] change `attractiveTypeCode` to enum `attractiveTypeCode`
    - [x] add method can modify exist `likeablePerson`
    - [x] `fromMember` must like `toMember` only once
        - [x] testcase
        - [x] modify exist `likeablePerson`
    - [x] `fromMember` must `like` less than **LIMIT(11)**
        - [x] testcase
        - [x] `count(*)` from `likeablePerson` where `fromMemberId` = `?`;
- [x] OAuth
    - [x] naver

---

- [x] OAuth
    - [x] AuthProvider enum
    - [x] OAuth2AttributeFactory
    - [x] OAuth2UserFactory
    - [ ] refactor
- [ ] exception pages
    - [ ] testcase
    - [x] error page custom
    - [x] link to `redirect:/`
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

## 2주차 미션 요약

---

**[접근 방법]**

### OAuth

CustomOAuth2UserService
**초기안**

1. `Provider` 를 Enum 으로 정의
2. type 별 OAuth 개인정보 파싱 및 생성

type 별로 `MemberService` 주입? -> 로직 분리

**개선안**

1. `Provider` 를 Enum 으로 정의
2. type 별 OAuth 개인정보 파싱
3. `memberService` 등록/조회
4. user 생성

**[특이사항]**

**Reference**

1. `OAuth2Attributes`
    - [Kakao](https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#kakaoaccount)
    - [Naver](https://developers.naver.com/docs/login/devguide/devguide.md#3-4-5-접근-토큰을-이용하여-프로필-api-호출하기)
    - [Google](https://developers.google.com/identity/openid-connect/openid-connect?hl=ko#obtainuserinfo)

