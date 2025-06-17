package com.ggyool.order.r2dbc.converter

import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import java.nio.ByteBuffer
import java.util.UUID

@ReadingConverter
class ByteBufferToUuidReadingConverter : Converter<ByteBuffer, UUID> {
    override fun convert(source: ByteBuffer): UUID {
        val firstLong = source.getLong()
        val secondLong = source.getLong()
        return UUID(firstLong, secondLong)
    }
}



