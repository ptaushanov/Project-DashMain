package com.businessproject.demo.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
@WritingConverter
public class ZonedDateTimeToStringConverter implements Converter<ZonedDateTime, String> {

    @Override
    public String convert(ZonedDateTime zonedDateTime) {
        return zonedDateTime.toString();
    }

}

