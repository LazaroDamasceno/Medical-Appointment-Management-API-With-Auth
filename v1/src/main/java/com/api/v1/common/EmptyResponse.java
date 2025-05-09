package com.api.v1.common;

import org.springframework.hateoas.RepresentationModel;

public class EmptyResponse extends RepresentationModel<EmptyResponse> {

    public static EmptyResponse empty() {
        return new EmptyResponse();
    }

    private EmptyResponse() {
    }
}
