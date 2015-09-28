package com.juhuifu.quartz.base.converter;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.core.convert.converter.Converter;

public class DateConverter implements Converter<String, Date> {

    /** date format 格式 */
    private static final String[] PATTERNS = { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "HH:mm:ss" };

    @Override
    public Date convert(String source) {
        if (StringUtils.isNotBlank(source)) {
            try {
                return DateUtils.parseDateStrictly(source, PATTERNS);
            } catch (ParseException e) {
                // 异常时参数绑定失败
            }
        }
        return null;
    }

}
