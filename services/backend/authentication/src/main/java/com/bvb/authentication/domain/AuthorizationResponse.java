package com.bvb.authentication.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthorizationResponse {
    private String userInfo;
}
