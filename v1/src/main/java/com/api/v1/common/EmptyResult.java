package com.api.v1.common;

import org.springframework.hateoas.RepresentationModel;

public final class EmptyResult extends RepresentationModel<EmptyResult> {

    private EmptyResult() {
    }

    public static EmptyResult empty() {
        return new EmptyResult();
    }
}
