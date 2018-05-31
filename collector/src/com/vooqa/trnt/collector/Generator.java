package com.vooqa.trnt.collector;

import java.util.UUID;

public class Generator {

    public static String randomNodeId() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 20);
    }

}
