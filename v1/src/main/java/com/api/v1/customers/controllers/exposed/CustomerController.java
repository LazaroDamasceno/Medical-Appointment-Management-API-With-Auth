package com.api.v1.customers.controllers.exposed;

import com.api.v1.customers.services.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class CustomerController {

    protected CustomerRegistrationService registrationService;
    protected CustomerRetrievalService retrievalService;
    protected CustomerUpdatingService updatingService;

}
