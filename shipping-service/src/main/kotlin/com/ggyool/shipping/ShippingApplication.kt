package com.ggyool.shipping

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ShippingApplication

fun main(args: Array<String>) {
    runApplication<ShippingApplication>(*args)
}