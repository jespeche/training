package com.training.project.service.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * Employee Events Unit Tests.
 *
 * It verifies that events are triggered only when it is necessary and they are correct.
 *
 * Note: No mock are required or Spring features are required at this time.
 */
class EmployeeEventsTests {

    private lateinit var employee: Employee

    @BeforeEach
    private fun setUp() {
        employee = Employee("John", "Finance", 40)
    }

    @Test
    fun `Check employee is not published when there is no actual change`() {
        employee.relocate("Finance")
        assertThat(employee.domainEvents()).isEmpty()
    }

    @Test
    fun `Check employee relocation event is being published`() {
        employee.relocate("Development")
        assertThat(employee.domainEvents()).isNotEmpty
    }

    @Test
    fun `Check employee relocation event is published with the right content`() {
        employee.relocate("Development")
        val expectedEvent = EmployeeRelocated(employee.id, "Development", "Finance")
        assertThat(employee.domainEvents().first()).isEqualTo(expectedEvent)
    }

    @Test
    fun `Check employee age changed event is not thrown when there is not actual change`() {
        employee.changeAge(40)
        assertThat(employee.domainEvents()).isEmpty()
    }

    @Test
    fun `Check age changed event is being published`() {
        employee.changeAge(41)
        assertThat(employee.domainEvents()).isNotEmpty
    }

    @Test
    fun `Check age changed throw the appropriate event`() {
        employee.changeAge(41)
        val expectedEvent = EmployeeAgeChanged(employee.id, 41, 40)
        assertThat(employee.domainEvents().first()).isEqualTo(expectedEvent)
    }
}