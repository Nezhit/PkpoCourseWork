package com.coursework.mainservice.config

import com.coursework.logic.LogicServiceOuterClass
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean

class ObjectMapping {
}
@JsonIgnoreProperties("unknownFields")
abstract class ProtobufMixin

@Bean
fun objectMapper(): ObjectMapper {
    return ObjectMapper().addMixIn(LogicServiceOuterClass.DriverDTO::class.java, ProtobufMixin::class.java)
}
