package com.vooqa.trnt.collector.krpc;

import java.util.HashMap;
import java.util.Map;

public class DHTQuery {
    public static Map<String, Object> pingRequest(String senderId) {
        Map<String, String> args = new HashMap<>();
        args.put("id", senderId);
        return getAbstractQueryMap("ping", args);
    }

    public static Map<String, Object> findNodeRequest(String senderId, String targetId) {
        Map<String, String> args = new HashMap<>();
        args.put("id", senderId);
        args.put("target", targetId);
        return getAbstractQueryMap("find_node", args);
    }

    private static Map<String, Object> getAbstractQueryMap(String q, Map<String, String> args) {
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("y", "q");     // type: query
        queryMap.put("t", "aa");    // transaction ID
        queryMap.put("q", q);       // query name
        queryMap.put("a", args);    // arguments
        return queryMap;
    }


}
