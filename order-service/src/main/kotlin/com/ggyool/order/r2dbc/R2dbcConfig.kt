package com.ggyool.order.r2dbc

import com.ggyool.order.r2dbc.converter.ByteBufferToUuidReadingConverter
import com.ggyool.order.r2dbc.converter.UuidToByteBufferWritingConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions
import org.springframework.data.r2dbc.dialect.MySqlDialect

@Configuration
class R2dbcConfig {

    @Bean
    fun r2dbcCustomConversions(): R2dbcCustomConversions {
        return R2dbcCustomConversions.of(
            MySqlDialect.INSTANCE,
            listOf(
                UuidToByteBufferWritingConverter(),
                ByteBufferToUuidReadingConverter()
            )
        )
    }
}