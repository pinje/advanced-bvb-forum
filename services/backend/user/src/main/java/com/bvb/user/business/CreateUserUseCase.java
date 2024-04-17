package com.bvb.user.business;

import com.bvb.user.domain.CreateUserRequest;
import com.bvb.user.domain.CreateUserResponse;

public interface CreateUserUseCase {
    CreateUserResponse createUser(CreateUserRequest request);
}
