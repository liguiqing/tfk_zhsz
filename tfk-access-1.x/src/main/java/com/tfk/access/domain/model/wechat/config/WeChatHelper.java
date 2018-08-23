package com.tfk.access.domain.model.wechat.config;


import com.tfk.commons.crypto.SHA2;
import org.springframework.util.StringUtils;

import java.util.Arrays;

/**
 *
 * @author Liguiqing
 * @version v1.0
 * @since JDK 1.8+
 */
public class WeChatHelper {

    public static boolean checkSignature(String signature, String timestamp, String nonce,WeChatConfig config) {
        String token = config.getToken();
        if (StringUtils.isEmpty(token)) {
            return false;
        }
        String[] arr = new String[]{token, timestamp, nonce};
        Arrays.sort(arr);
        StringBuffer content = new StringBuffer();
        for (String a : arr) {
            content.append(a);
        }
        String newSignature = SHA2.encode(content.toString());
        return newSignature.equals(signature);
    }
}
