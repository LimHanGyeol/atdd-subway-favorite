package nextstep.subway.member;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.subway.AcceptanceTest;
import nextstep.subway.auth.dto.TokenResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static nextstep.subway.member.MemberRequestSteps.*;
import static nextstep.subway.member.MemberVerificationSteps.*;

@DisplayName("회원 정보 관리 관련 기능 인수 테스트")
public class MemberAcceptanceTest extends AcceptanceTest {

    public static final String EMAIL = "email@email.com";
    public static final String PASSWORD = "password";
    public static final int AGE = 20;

    public static final String NEW_EMAIL = "newemail@email.com";
    public static final String NEW_PASSWORD = "newpassword";
    public static final int NEW_AGE = 21;

    @Test
    @DisplayName("회원가입을 한다.")
    void createMember() {
        // when
        ExtractableResponse<Response> response = 회원_생성_요청(EMAIL, PASSWORD, AGE);

        // then
        회원_생성됨(response);
    }

    @Test
    @DisplayName("회원 정보를 조회한다.")
    void getMember() {
        // given
        ExtractableResponse<Response> createResponse = 회원_생성_요청(EMAIL, PASSWORD, AGE);

        // when
        ExtractableResponse<Response> response = 회원_정보_조회_요청(createResponse);

        // then
        회원_정보_조회됨(response, EMAIL, AGE);

    }

    @Test
    @DisplayName("회원 정보를 수정한다.")
    void updateMember() {
        // given
        ExtractableResponse<Response> createResponse = 회원_생성_요청(EMAIL, PASSWORD, AGE);

        // when
        ExtractableResponse<Response> response = 회원_정보_수정_요청(createResponse, NEW_EMAIL, NEW_PASSWORD, NEW_AGE);

        // then
        회원_정보_수정됨(response);
    }

    @Test
    @DisplayName("회원 정보를 삭제한다.")
    void deleteMember() {
        // given
        ExtractableResponse<Response> createResponse = 회원_생성_요청(EMAIL, PASSWORD, AGE);

        // when
        ExtractableResponse<Response> response = 회원_삭제_요청(createResponse);

        // then
        회원_삭제됨(response);
    }

    @Test
    @DisplayName("회원 정보를 관리한다.")
    void manageMember() {
        // when
        ExtractableResponse<Response> createdMemberResponse = 회원_생성_요청(EMAIL, PASSWORD, AGE);

        // then
        회원_생성됨(createdMemberResponse);

        // when
        ExtractableResponse<Response> foundMemberResponse = 회원_정보_조회_요청(createdMemberResponse);

        // then
        회원_정보_조회됨(foundMemberResponse, EMAIL, AGE);

        // when
        ExtractableResponse<Response> updatedMemberResponse = 회원_정보_수정_요청(createdMemberResponse, NEW_EMAIL, NEW_PASSWORD, NEW_AGE);

        // then
        회원_정보_수정됨(updatedMemberResponse);

        // when
        ExtractableResponse<Response> deletedMemberResponse = 회원_삭제_요청(createdMemberResponse);

        // then
        회원_삭제됨(deletedMemberResponse);
    }

    @Test
    @DisplayName("나의 정보를 조회한다.")
    void findMemberOfMine() {
        // given
        회원_생성_요청(EMAIL, PASSWORD, AGE);
        TokenResponse tokenResponse = 로그인_되어_있음(EMAIL, PASSWORD);

        // when
        ExtractableResponse<Response> response = 내_회원_정보_조회_요청(tokenResponse);

        // then
        회원_정보_조회됨(response, EMAIL, AGE);
    }

    @Test
    @DisplayName("나의 정보를 수정한다.")
    void updateMemberOfMine() {
        // given
        회원_생성_요청(EMAIL, PASSWORD, AGE);
        TokenResponse tokenResponse = 로그인_되어_있음(EMAIL, PASSWORD);

        // when
        ExtractableResponse<Response> response = 내_회원_정보_수정_요청(tokenResponse, NEW_EMAIL, NEW_PASSWORD, NEW_AGE);

        // then
        회원_정보_수정됨(response);
        회원_정보_조회됨(response, NEW_EMAIL, NEW_AGE);
    }

    @Test
    @DisplayName("나의 정보를 삭제한다.")
    void deleteMemberOfMine() {
        // given
        회원_생성_요청(EMAIL, PASSWORD, AGE);
        TokenResponse tokenResponse = 로그인_되어_있음(EMAIL, PASSWORD);

        // when
        ExtractableResponse<Response> response = 내_회원_정보_삭제_요청(tokenResponse);

        // then
        회원_삭제됨(response);
    }

    @Test
    @DisplayName("나의 정보를 관리한다.")
    void manageMyInfo() {
        // when
        ExtractableResponse<Response> createdMemberResponse = 회원_생성_요청(EMAIL, PASSWORD, AGE);

        // then
        회원_생성됨(createdMemberResponse);

        // when
        TokenResponse tokenResponse = 로그인_되어_있음(EMAIL, PASSWORD);
        ExtractableResponse<Response> foundMemberResponse = 내_회원_정보_조회_요청(tokenResponse);

        // then
        회원_정보_조회됨(foundMemberResponse, EMAIL, AGE);

        // when
        ExtractableResponse<Response> updatedMemberResponse = 내_회원_정보_수정_요청(tokenResponse, NEW_EMAIL, NEW_PASSWORD, NEW_AGE);

        // then
        회원_정보_수정됨(updatedMemberResponse);

        // when
        ExtractableResponse<Response> deletedMemberResponse = 내_회원_정보_삭제_요청(tokenResponse);

        // then
        회원_삭제됨(deletedMemberResponse);
    }
}
