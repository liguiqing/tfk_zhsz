package com.tfk.commons.util;

import com.alibaba.fastjson.JSON;
import com.tfk.commons.lang.Throwables;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author Liguiqing
 * @since V3.0
 */
@Slf4j
public class JsonUtillWrapper {


    public static String toJson(Object src) {
        String json = JSON.toJSONString(src);
        return json;
    }

    /**
     * 判断字符串是否是json格式
     *
     * @param s
     * @return
     */
    public static boolean isJson(String s) {
        boolean result = false;
        try {
            JsonUtillWrapper.from(s, Map.class);
            result = true;
        } catch (Exception e) {
            log.debug(Throwables.toString(e));
        }

        return result;
    }

    public static <T> T from(String json, Class<T> type) {
        T obj = JSON.parseObject(json, type);
        return obj;
    }

    public static Object to(String json) {
        Object obj = JSON.parse(json);
        return obj;
    }

}