package com.juhuifu.quartz.base.serializer;

import java.io.IOException;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

public class DateSerializer extends JsonSerializer<Date> {

    @Override
    public void serialize(Date date, JsonGenerator jGen, SerializerProvider provider)
            throws IOException, JsonProcessingException {
        String value = null;
        if (date != null) {
            value = DateFormatUtils.format(date, "yyyy-MM-dd");
        }
        jGen.writeString(value);
    }
}
