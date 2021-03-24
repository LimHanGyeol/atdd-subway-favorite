package nextstep.subway.member.domain;

import nextstep.subway.auth.domain.UserDetails;

public class LoginMember implements UserDetails {

    private Long id;
    private String email;
    private String password;
    private Integer age;

    public LoginMember(Long id, String email, String password, Integer age) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.age = age;
    }

    public static LoginMember of(Member member) {
        return new LoginMember(member.getId(), member.getEmail(), member.getPassword(), member.getAge());
    }

    @Override
    public boolean validatePassword(String password) {
        return this.password.equals(password);
    }

    public Long getId() {
        return id;
    }

    @Override
    public String getEmail() {
        return email;
    }

    public Integer getAge() {
        return age;
    }

    @Override
    public String getPassword() {
        return password;
    }
}
