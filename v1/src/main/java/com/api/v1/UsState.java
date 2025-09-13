package com.api.v1;

import java.util.stream.Stream;

public enum UsState {

    AL, AK, AZ, AR, CA,
    CO, CT, DE, FL, GA,
    HI, ID, IL, IN, IA,
    KS, KY, LA, ME, MD,
    MA, MI, MN, MT, NE,
    NV, NH, NJ, NM, NY,
    NC, ND, OH, OK, OR,
    PA, RI, SC, SD, TN,
    TX, UT, VT, VA, WA,
    WV, MS, WI, MO, WY;

    public static UsState getState(String stateCode) {
        return Stream.of(UsState.values())
                .filter(state -> state.toString().equals(stateCode))
                .findFirst()
                .orElseThrow(() -> new UnknowUsStateException(stateCode));
    }

}
