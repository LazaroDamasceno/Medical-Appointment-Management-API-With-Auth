package com.api.v2

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.modulith.core.ApplicationModules

@SpringBootApplication
class V2Application

fun main(args: Array<String>) {
	val modules = ApplicationModules.of(V2Application::class.java)
	modules.verify()
	runApplication<V2Application>(*args)
}
