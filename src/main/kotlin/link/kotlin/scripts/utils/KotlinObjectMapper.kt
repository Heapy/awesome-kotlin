package link.kotlin.scripts.utils

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.kotlinModule

@Suppress("FunctionName")
fun KotlinObjectMapper(): ObjectMapper =
    JsonMapper.builder()
        .addModule(kotlinModule { })
        .addModule(JavaTimeModule())
        .build()
