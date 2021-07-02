package com.example

import io.grpc.Channel
import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

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
    }

    suspend fun saveUserStream() {
        val demoServerStub = createStub()

        val requests = generateOutgoingRequests()

        demoServerStub.saveUserStream(requests).collect { response ->
            println("Response: " + response.id)
        }
    }

    private fun generateOutgoingRequests(): Flow<SaveUserRequest> = flow {

        val request1 = SaveUserRequest.newBuilder()
            .setName("John")
            .setLastName("Doe")
            .setDocument("05262438594")
            .build()

        val request2 = SaveUserRequest.newBuilder()
            .setName("John")
            .setLastName("Doe 2")
            .setDocument("07262438594")
            .build()

        val request3 = SaveUserRequest.newBuilder()
            .setName("Jane")
            .setLastName("Doe")
            .setDocument("09262438594")
            .build()

        val requests = listOf(request1, request2, request3)

        for (request in requests) {
            println("Request: " + request.name)
            emit(request)
            delay(5000)
        }
    }

    private fun createStub(): DemoServerServiceGrpcKt.DemoServerServiceCoroutineStub {
        val channel: Channel = ManagedChannelBuilder.forAddress("localhost", 50051)
            .usePlaintext()
            .build()

        return DemoServerServiceGrpcKt.DemoServerServiceCoroutineStub(channel)
    }
}