package com.mkdevlag.springsecurity.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mkdevlag.springsecurity.domain.UserInfo;
import com.mkdevlag.springsecurity.dto.UserInfoDto;
import com.mkdevlag.springsecurity.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {
	
	private final UserRepository userRepository;
	
	public Long save(UserInfoDto userInfoDto) {
				
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		userInfoDto.setPassword(encoder.encode(userInfoDto.getPassword()));
		
		return userRepository.save(UserInfo.builder()
				.email(userInfoDto.getEmail())
				.password(userInfoDto.getPassword())
				.auth(userInfoDto.getAuth()).build()
				).getCode();
	}

	@Override
	public UserInfo loadUserByUsername(String email) throws UsernameNotFoundException {

		return userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException(email));
	}

	
	
}
