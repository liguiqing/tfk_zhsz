package com.zhezhu.access.infrastructure;

/**
 * @author Liguiqing
 * @since V3.0
 */

public interface SchoolService {

    String getSchoolName(String schoolId);

    String getClazzName(String clazzId);

    String getSchoolIdBy(String clazzId);
}