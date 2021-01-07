package com.businessproject.demo.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

import java.time.*;
import java.time.format.DateTimeFormatter;

@Component
@ReadingConverter
public class StringToZonedDateTimeConverter implements Converter<String, ZonedDateTime> {

    @Override
    public ZonedDateTime convert(String date) {
        //System.out.println("String -> ZonedDateTime");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return ZonedDateTime.ofInstant(
                LocalDateTime.of(LocalDate.parse(date, dateTimeFormatter),
                        LocalTime.ofSecondOfDay(0)), ZoneOffset.UTC, ZoneId.systemDefault()
        );
    }

}
