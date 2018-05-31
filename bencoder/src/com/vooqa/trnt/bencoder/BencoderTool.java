package com.vooqa.trnt.bencoder;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BencoderTool {

    private final static char INTEGER = 'i';
    private final static char LIST = 'l';
    private final static char DICTIONARY = 'd';
    private final static char END = 'e';
    private final static char DIVEDIR = ':';


    public static Map<String, Object> decode(InputStream inputStream) throws Exception {
        InputStreamReader reader = new InputStreamReader(inputStream);
        Object torrentModel = readObject(reader);
        if (torrentModel instanceof Map) {
            return (Map<String, Object>) torrentModel;
        } else {
            throw new Exception("Wrong file format");
        }
    }

    public static byte[] encode(Map<String, Object> objectMap) {
        return decodeObject(objectMap).getBytes();
    }

    private static String decodeObject(Object object) {
        if (object instanceof String) {
            String _s = (String) object;
            return _s.length() + ":" + _s;
        } else if (object instanceof Integer) {
            Integer _i = (Integer) object;
            return "" + INTEGER + _i + END;
        } else if (object instanceof List) {
            List _l = (List) object;
            String _lResilt = String.valueOf(LIST);
            for (Object o : _l) {
                _lResilt += decodeObject(o);
            }
            _lResilt += END;
            return _lResilt;
        } else if (object instanceof Map) {
            Map _m = (Map) object;
            String _mResult = String.valueOf(DICTIONARY);
            for (Object key : _m.keySet()) {
                _mResult += decodeObject(key);
                _mResult += decodeObject(_m.get(key));
            }
            _mResult += END;
            return _mResult;
        } else {
            return null;
        }

    }


    private static Object readObject(InputStreamReader reader) throws Exception {
        char[] BUFFER = new char[1];
        char CC;
        while (reader.read(BUFFER) != -1) {
            char _ch = BUFFER[0];
            switch (_ch) {
                case DICTIONARY:
                    HashMap<String, Object> map = new HashMap<>();
                    String key;
                    while ((key = (String) readObject(reader)) != null) {
                        Object value = readObject(reader);
                        map.put(key, value);
                    }
                    return map;
                case LIST:
                    List<Object> list = new ArrayList<>();
                    Object listObject;
                    while ((listObject = readObject(reader)) != null) {
                        list.add(listObject);
                    }
                    return list;
                case INTEGER:
                    String sInt = "";
                    while ((CC = (char) reader.read()) != END) {
                        sInt += CC;
                    }
                    return Integer.valueOf(sInt);
                case END:
                    return null;
                default:    // byte string
                    String sLen = String.valueOf(_ch);
                    while ((CC = (char) reader.read()) != DIVEDIR) {
                        sLen += CC;
                    }
                    int len = Integer.valueOf(sLen);
                    char[] varBuffer = new char[len];
                    reader.read(varBuffer);
                    return new String(varBuffer);
            }
        }
        return null;
    }

}
