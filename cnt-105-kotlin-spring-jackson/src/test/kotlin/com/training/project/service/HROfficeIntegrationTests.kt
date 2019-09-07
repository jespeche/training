package com.training.project.service

import com.training.project.service.model.Employee
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.NoSuchElementException

/**
 * Service integration tests.
 *
 * It verifies the interaction between the service real component subcomponents (entities, databases, etc) as
 * a whole. Cases covered by unit tests were omitted.
 *
 * Note: Spring is enabled and there are no mocks so we can confirm that the behaviour is the expected.
 */
@SpringBootTest
class HROfficeIntegrationTests(@Autowired val office: HROffice) {

    private lateinit var employee: Employee

    @BeforeEach
    fun setUp() {
        employee = office.hireEmployee("John", "Software", 40)
    }

    @Test
    fun `Check hiring an employee`() {
        assertThat(office.employee(employee.id)).isNotNull
    }

    @Test
    fun `Check hiring an employee and it can be retrieved`() {
        assertThat(office.employees()).contains(employee)
    }

    @Test
    fun `Check firing an employee and it can not be retrieved`() {
        office.fireEmployee(employee.id)
        assertThrows<NoSuchElementException> { office.employee(employee.id) }
    }

    @Test
    fun `Check hiring an employee and it exists in the list`() {
        office.fireEmployee(employee.id)
        assertThat(office.employees()).doesNotContain(employee)
    }
}