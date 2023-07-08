package com.eoxys.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlatformAuthRequest {
	
	private String evt ;
    private String gtp;
    private String lid ;
    private String pwd;
    private String scp ;
    private String tid;
    
    

}
