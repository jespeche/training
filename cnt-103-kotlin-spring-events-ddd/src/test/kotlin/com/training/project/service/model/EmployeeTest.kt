package com.training.project.service.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * Employee Unit Tests.
 *
 * Perform behavioral tests over an Employee object.
 *
 * Note: No mock are required or Spring features are required at this time.
 */
class EmployeeTest {

    private lateinit var employee: Employee

    @BeforeEach
    private fun setUp() {
        employee = Employee("John", "Finance", 40)
    }

    @Test
    fun `Check values are retained`() {
        employee.apply {
            assertThat(this).isNotNull()
            assertThat(id).isNotNull()
            assertThat(name).isEqualTo("John")
            assertThat(department).isEqualTo("Finance")
            assertThat(age).isEqualTo(40)
        }
    }

    @Test
    fun `Check relocating an employee`() {
        employee.relocate("NEW_DEPARTMENT")
        assertThat(employee.department).isEqualTo("NEW_DEPARTMENT")
    }

    @Test
    fun `Check change age of an employee`() {
        employee.changeAge(41)
        assertThat(employee.age).isEqualTo(41)
    }
}