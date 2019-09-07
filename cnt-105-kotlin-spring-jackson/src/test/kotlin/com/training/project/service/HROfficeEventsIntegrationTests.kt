package com.training.project.service

import com.nhaarman.mockitokotlin2.argumentCaptor
import com.training.project.service.model.Employee
import com.training.project.service.model.EmployeeHired
import com.training.project.service.model.EmployeeRelocated
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.event.EventListener

/**
 * Event integration tests.
 *
 * It checks that the mechanism to publish events through ApplicationEventPublisher and @DomainEvents
 * are working as expected.
 *
 * As consequence, only two events are covered here:
 *   - One for the Service.
 *   - One for the Entity.
 *
 * Note: This integration test enables Spring Boot and JPA so we test the real mechanism.
 */
@SpringBootTest
class HROfficeEventsIntegrationTests(@Autowired val office: HROffice) {

    @MockBean
    lateinit var eventHandler: TestEventHandler

    private lateinit var employee: Employee

    @BeforeEach
    fun setUp() {
        employee = office.hireEmployee("John", "Software", 40)
    }

    @Test
    fun `Check hiring an employee event`() {
        val captor = argumentCaptor<EmployeeHired>()
        verify(eventHandler).handleEmployeeHired(captor.capture())
        val expected = EmployeeHired(employee.id, "John", "Software", 40)
        assertThat(captor.lastValue).isEqualTo(expected)
    }

    @Test
    fun `Check relocating an employee`() {
        office.relocateEmployee(employee.id, "Finance")
        val captor = argumentCaptor<EmployeeRelocated>()
        verify(eventHandler).handleEmployeeRelocated(captor.capture())
        val expected = EmployeeRelocated(employee.id, "Finance", "Software")
        assertThat(captor.lastValue).isEqualTo(expected)
    }
}

interface TestEventHandler {
    @EventListener
    fun handleEmployeeHired(event: EmployeeHired)

    @EventListener
    fun handleEmployeeRelocated(event: EmployeeRelocated)
}
