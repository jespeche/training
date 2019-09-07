package com.training.project.services

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.verify
import com.training.project.services.user.UserRegistered
import com.training.project.services.user.UserService
import com.training.project.services.user.UserUnregistered
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean

@SpringBootTest
class UserServiceEventTests(@Autowired val userService: UserService) {

    @MockBean
    lateinit var eventHandler: TestEventHandler

    @BeforeEach
    fun printSeparator() {
        println("------------------------------------")
    }

    @Test
    fun contextLoads() {
        assertThat(userService).isNotNull
    }

    @Test
    fun `Check event is triggered when a new user is registered with NO EXCEPTION triggered by friendship service`() {
        println("[ Test ] - Testing register")
        userService.register("John", "jdoe@gmail.com", "1234")
        println("[ Test ] - Method invoked")
        verify(eventHandler).handleUserRegistered(any())
        println("[ Test ] - Finished")
    }

    @Test
    fun `Check event triggered when a new user is registered - Data`() {
        println("[ Test ] - Testing register")
        userService.register("John", "jdoe@gmail.com", "1234")
        println("[ Test ] - Method invoked")
        val argumentCaptor = argumentCaptor<UserRegistered>()
        verify(eventHandler).handleUserRegistered(argumentCaptor.capture())
        argumentCaptor.lastValue.apply {
            assertThat(user).isEqualTo("John")
            assertThat(email).isEqualTo("jdoe@gmail.com")
        }
        println("[ Test ] - Finished")
    }

    @Test
    fun `Check event is triggered when a user is unregistered`() {
        println("[ Test ] - Testing unregister")
        userService.unregister("John")
        verify(eventHandler).handleUserUnregistered(any())
        println("[ Test ] - Finished")
    }


    @Test
    fun `Check event is triggered when a user is unregistered - Data`() {
        println("[ Test ] - Testing register")
        userService.unregister("John")
        println("[ Test ] - Method invoked")
        val argumentCaptor = argumentCaptor<UserUnregistered>()
        verify(eventHandler).handleUserUnregistered(argumentCaptor.capture())
        argumentCaptor.lastValue.apply {
            assertThat(user).isEqualTo("John")
        }
        println("[ Test ] - Finished")
    }
}
