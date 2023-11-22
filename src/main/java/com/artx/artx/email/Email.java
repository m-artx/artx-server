package com.artx.artx.email;

public enum Email {
	CREATE_USER_SUBJECT("ARTX 회원가입 완료를 위해 인증이 필요합니다."),
//	CREATE_USER_TEXT("링크를 클릭하시면 인증이 완료됩니다.<a href=" + usersApiAddress + userId.toString() + "/email-auth" + ">인증</a>"),

	FIND_USERNAME_SUBJECT(""),
	FIND_USERNAME_TEXT(""),

	INIT_PASSWORD_SUBJECT(""),
	INIT_PASSWORD_TEXT("");

	Email(String value) {
		this.value = value;
	}

	private String value;
}
