package com.ggyool.order.r2dbc.converter

import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.WritingConverter
import java.nio.ByteBuffer
import java.util.UUID

@WritingConverter
class UuidToByteBufferWritingConverter : Converter<UUID, ByteArray> {
    override fun convert(source: UUID): ByteArray {
        val buffer = ByteBuffer.allocate(16)
        buffer.putLong(source.mostSignificantBits)
        buffer.putLong(source.leastSignificantBits)
        return buffer.array()
    }
}