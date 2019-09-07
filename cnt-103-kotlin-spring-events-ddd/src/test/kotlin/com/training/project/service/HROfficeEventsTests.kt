package com.training.project.service

import com.nhaarman.mockitokotlin2.KArgumentCaptor
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.training.project.service.implementation.HROfficeImpl
import com.training.project.service.model.Employee
import com.training.project.service.model.EmployeeFired
import com.training.project.service.model.EmployeeHired
import com.training.project.service.model.EmployeeRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.verify
import org.springframework.context.ApplicationEventPublisher
import java.util.Optional

/**
 * HR Office Events Unit Tests.
 *
 * It verifies that events are triggered only when it is necessary and they are correct.
 *
 * Only the two events triggered by HROffice are being considered here since entities
 * events are already tested in its own unit tests.
 *
 * Note: Since these are unit tests, there are no Spring enabled and the dependencies
 * repository and publisher were mocked.
 */
class HROfficeEventsTests {

    lateinit var office: HROffice

    private val repository: EmployeeRepository = mock()
    private val publisher: ApplicationEventPublisher = mock()

    private val employee = Employee("John", "Finance", 40)

    @BeforeEach
    fun setUp() {
        office = HROfficeImpl(repository, publisher)
    }

    @Test
    fun `Check hiring an employee event`() {
        whenever(repository.save(any<Employee>())).thenReturn(employee)
        whenever(repository.findById(any())).thenReturn(Optional.of(employee))
        office.hireEmployee("John", "Software", 40)
        val expectedEvent = EmployeeHired(employee.id, "John", "Finance", 40)
        assertThat(argumentCaptor<EmployeeHired>().getIt()).isEqualTo(expectedEvent)
    }

    @Test
    fun `Check firing an employee event`() {
        whenever(repository.findById(any())).thenReturn(Optional.of(employee))
        office.fireEmployee(employee.id)
        val expectedEvent = EmployeeFired(employee.id)
        assertThat(argumentCaptor<EmployeeFired>().getIt()).isEqualTo(expectedEvent)
    }

    private inline fun <reified T : Any> KArgumentCaptor<T>.getIt() = verify(publisher).publishEvent(capture()).let { lastValue }
}
