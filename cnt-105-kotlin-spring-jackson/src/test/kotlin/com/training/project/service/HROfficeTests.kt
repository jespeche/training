package com.training.project.service

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.training.project.service.implementation.HROfficeImpl
import com.training.project.service.model.Employee
import com.training.project.service.model.EmployeeRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.AdditionalAnswers.returnsFirstArg
import org.springframework.context.ApplicationEventPublisher
import java.util.NoSuchElementException
import java.util.Optional
import java.util.UUID

/**
 * HR Office Unit Tests.
 *
 * Perform behavioral tests over the service.
 *
 * Note: Since these are unit tests, there are no Spring enabled and the dependencies
 * repository and publisher were mocked.
 */
class HROfficeTests {

    private lateinit var office: HROffice

    private val repository: EmployeeRepository = mock()
    private val publisher: ApplicationEventPublisher = mock()

    private val expected = Employee("John", "Finance", 40)

    @BeforeEach
    fun setUp() {
        office = HROfficeImpl(repository, publisher)
    }

    @Test
    fun `Check employee retrieval`() {
        whenever(repository.findById(any())).thenReturn(Optional.of(expected))
        val employee = office.employee(expected.id)
        assertThat(employee).isNotNull
        assertThat(employee).isEqualTo(expected)
    }

    @Test
    fun `Check employee retrieval throws exception for unknown id`() {
        whenever(repository.findById(any())).thenThrow(NoSuchElementException())
        assertThrows<NoSuchElementException> { office.employee(UUID.randomUUID()) }
    }

    @Test
    fun `Check employees retrieval`() {
        whenever(repository.findAll()).thenReturn(listOf(expected))
        val employees = office.employees()
        assertThat(employees).isNotEmpty
        assertThat(employees).contains(expected)
    }

    @Test
    fun `Check hiring an employee`() {
        whenever(repository.save(any<Employee>())).then(returnsFirstArg<Employee>())
        val employee = office.hireEmployee("John", "Finance", 40)
        employee.apply {
            assertThat(name).isEqualTo("John")
            assertThat(department).isEqualTo("Finance")
            assertThat(age).isEqualTo(40)
        }
    }

    @Test
    fun `Check firing an unknown employee`() {
        whenever(repository.deleteById(any())).thenThrow(IllegalArgumentException())
        assertThrows<IllegalArgumentException> { office.fireEmployee(UUID.randomUUID()) }
    }

    @Test
    fun `Check relocating an employee`() {
        whenever(repository.findById(any())).thenReturn(Optional.of(expected))
        whenever(repository.save(any<Employee>())).then(returnsFirstArg<Employee>())

        office.relocateEmployee(expected.id, "Finance")
        assertThat(office.employee(expected.id).department).isEqualTo("Finance")
    }

    @Test
    fun `Check change age of an employee`() {
        whenever(repository.findById(any())).thenReturn(Optional.of(expected))
        whenever(repository.save(any<Employee>())).then(returnsFirstArg<Employee>())

        office.changeEmployeeAge(expected.id, 41)
        assertThat(office.employee(expected.id).age).isEqualTo(41)
    }
}