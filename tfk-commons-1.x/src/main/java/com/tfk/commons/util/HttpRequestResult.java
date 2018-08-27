package com.tfk.commons.util;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Liguiqing
 * @since V3.0
 */
@NoArgsConstructor
@Data
public class HttpRequestResult {
    private String content;

    private String sessionId;

}