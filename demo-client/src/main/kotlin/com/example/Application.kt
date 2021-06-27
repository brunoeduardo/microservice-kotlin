package com.example

import io.micronaut.runtime.Micronaut.*

suspend fun main(args: Array<String>) {

	println("Hello World!")
	val demosService = DemosService()
	demosService.saveUser

	build()
	    .args(*args)
		.packages("com.example")
		.start()
}

