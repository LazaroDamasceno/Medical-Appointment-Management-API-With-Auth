package com.api.v1.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EmptyResponse extends RepresentationModel<EmptyResponse> {

    public static EmptyResponse empty() {
        return new EmptyResponse();
    }

}
