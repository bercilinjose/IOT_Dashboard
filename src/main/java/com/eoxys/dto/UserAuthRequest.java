package com.eoxys.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthRequest {
	
	private String access_token ;
    private String name;
    private String userUid ;
    private String scope;
    private String refresh_token ;
    private String username;
    private String group;
    private String phone;
    private String code_challenge;
    private String auth_code;
    private String code_verifier;

}
