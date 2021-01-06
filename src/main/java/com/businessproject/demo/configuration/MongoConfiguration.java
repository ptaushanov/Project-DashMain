package com.businessproject.demo.configuration;

import com.businessproject.demo.converter.DateToZonedDateTimeConverter;
import com.businessproject.demo.converter.StringToZonedDateTimeConverter;
import com.businessproject.demo.converter.ZonedDateTimeToDateConverter;
import com.businessproject.demo.converter.ZonedDateTimeToStringConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MongoConfiguration {

    @Bean
    public MongoCustomConversions customConversions() {
        List<Converter<?, ?>> converters = new ArrayList<>();
        converters.add(new DateToZonedDateTimeConverter());
        converters.add(new ZonedDateTimeToDateConverter());
        converters.add(new ZonedDateTimeToStringConverter());
        converters.add(new StringToZonedDateTimeConverter());
        return new MongoCustomConversions(converters);
    }

}
