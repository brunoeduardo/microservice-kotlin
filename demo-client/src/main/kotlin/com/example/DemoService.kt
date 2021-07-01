package com.example

import io.grpc.Channel
import io.grpc.ManagedChannelBuilder

class DemoService {

    suspend fun saveUser() {
        val demoServerStub = createStub()

        val saveUserRequest = SaveUserRequest.newBuilder()
            .setName("Bruno")
            .setLastName("Eduardo")
            .setDocument("12345678900")
            .build()

        val saveUserResponse = demoServerStub.saveUser(saveUserRequest)

        println("Registered user with id = " + saveUserResponse.id)
        println("Registered user with id = " + saveUserResponse.id)
    }

    private fun createStub(): DemoServerServiceGrpcKt.DemoServerServiceCoroutineStub {
        val channel: Channel = ManagedChannelBuilder.forAddress("localhost", 50051)
            .usePlaintext()
            .build()

        return DemoServerServiceGrpcKt.DemoServerServiceCoroutineStub(channel)
    }
}