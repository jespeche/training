package com.training.project.service.implementation

import com.training.project.service.HROffice
import com.training.project.service.model.Employee
import com.training.project.service.model.EmployeeEvent
import com.training.project.service.model.EmployeeFired
import com.training.project.service.model.EmployeeHired
import com.training.project.service.model.EmployeeRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import java.util.UUID
import javax.transaction.Transactional

@Service
@Transactional
class HROfficeImpl(private val repository: EmployeeRepository, private val publisher: ApplicationEventPublisher) : HROffice {

    override fun employee(employeeId: UUID): Employee = repository.findById(employeeId).get()
    override fun employees(): List<Employee> = repository.findAll().toList()
    override fun hireEmployee(name: String, department: String, age: Int): Employee =
        repository.save(Employee(name, department, age)).also {
            publish(EmployeeHired(it.id, it.name, it.department, it.age))
        }

    override fun fireEmployee(employeeId: UUID) = repository.deleteById(employeeId).also { publish(EmployeeFired(employeeId)) }
    override fun relocateEmployee(employeeId: UUID, department: String): Employee = mutate(employeeId) { it.relocate(department) }
    override fun changeEmployeeAge(employeeId: UUID, age: Int): Employee = mutate(employeeId) { it.changeAge(age) }

    private fun mutate(employeeId: UUID, mutation: (Employee) -> Unit): Employee {
        val employee = employee(employeeId)
        mutation(employee)
        return repository.save(employee)
    }

    private fun publish(event: EmployeeEvent) = publisher.publishEvent(event)
}