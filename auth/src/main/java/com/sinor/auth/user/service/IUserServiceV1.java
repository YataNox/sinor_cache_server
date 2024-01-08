package com.sinor.auth.user.service;

import com.sinor.auth.auth.dto.AuthDto;

public interface IUserServiceV1 {

	void registerUser(AuthDto.SignupDto signupDto);
}
