package com.juhuifu.quartz.base.serializer;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.juhuifu.quartz.base.enums.SuperEnum;

/** serialize */
public class SuperEnumSerialize extends JsonSerializer<SuperEnum> {
    @Override
    public void serialize(SuperEnum value, JsonGenerator jGen, SerializerProvider provider)
            throws IOException, JsonProcessingException {
        jGen.writeObject(value.getMap());
    }

}
