package com.zhezhu.commons.util;

import com.zhezhu.commons.lang.Throwables;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 字符处理包装器
 *
 * @author Liguiqing
 * @since V3.0
 */
@Slf4j
public class StringUtilWrapper {

    /**
     * 汉字转拼音
     *
     * @param chinese
     * @param upperCase 结果是否转换为大写
     * @return 汉字的拼音
     */
    public static String chineseTranslateToSpelling(String chinese, boolean upperCase){
        StringBuilder spelling = new StringBuilder();
        char[] chars = chinese.toCharArray();
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(upperCase?HanyuPinyinCaseType.UPPERCASE:HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for(int i=0;i<chars.length;i++){
            spelling.append(chineseCharToString(chars[i], format));
        }
        return spelling.toString();
    }

    private static String chineseCharToString(char c,HanyuPinyinOutputFormat format){
        String s = String.valueOf(c);
        if (s.matches("[\\u4e00-\\u9fa5]")) {
            try {
                String[] mPinyinArray = PinyinHelper.toHanyuPinyinStringArray(c,format);
                return mPinyinArray[0];
            } catch (BadHanyuPinyinOutputFormatCombination e) {
                log.error(Throwables.toString(e));
            }
        }
        return s;
    }
}