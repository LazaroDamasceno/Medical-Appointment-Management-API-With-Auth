package com.api.v1.common;

import java.util.Arrays;

public enum States {

    AL, AK, AZ, AR, CA, CO, CT, DE, FL, GA,
    HI, ID, IL, IN, IA, KS, KY, LA, ME, MD,
    MA, MI, MN, MS, MO, MT, NE, NV, NH, NJ,
    NM, NY, NC, ND, OH, OK, OR, PA, RI, SC,
    SD, TN, TX, UT, VT, VA, WA, WV, WI, WY;

    public static States from(String state) {
        States parsedValue = States.valueOf(state);
        return Arrays
                .stream(States.values())
                .filter(e -> e.equals(parsedValue))
                .findAny()
                .orElseThrow(NonExistentStateNotFound::new);
    }

}