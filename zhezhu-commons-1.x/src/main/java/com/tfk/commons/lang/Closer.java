package com.tfk.commons.lang;

import lombok.extern.slf4j.Slf4j;

/**
 * 资源释放器
 * @author Liguiqing
 * @since V3.0
 */
@Slf4j
public class Closer {

    public static <T extends AutoCloseable> void close(T t){
        try{
            t.close();
        }catch (Exception e){
            log.error(Throwables.toString(e));
        }
    }

}