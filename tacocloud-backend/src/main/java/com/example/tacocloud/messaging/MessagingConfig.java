//package com.example.tacocloud.messaging;
//
//import com.example.tacocloud.Order;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//public class MessagingConfig {
//    @Bean
//    public MappingJackson2MessageConverter messageConverter(){
//        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
//        converter.setTypeIdPropertyName("_typeId");
//
//        Map<String, Class<?>> typeIdMap = new HashMap<>();
//        typeIdMap.put("order", Order.class);
//        converter.setTypeIdMappings(typeIdMap);
//        return converter;
//    }
//}
