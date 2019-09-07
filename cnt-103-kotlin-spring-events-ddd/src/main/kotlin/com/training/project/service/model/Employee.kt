package com.training.project.service.model

import com.training.project.service.infrastructure.AggregateRoot
import java.util.UUID
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

/**
 * Represent a domain entity which is the aggregate root.
 *
 * Notice that PUBLISH method is a protected method from AggregateRoot class that
 * only buffer events until repository.save() is executed.
 *
 * Check Spring documentation for @DomainEvents and @AfterDomainEventPublication.
 */
@Entity
data class Employee(
    var name: String = "",
    var department: String = "",
    var age: Int = 0,
    @Id @GeneratedValue
    val id: UUID = UUID.randomUUID()
) : AggregateRoot<EmployeeEvent>() {

    fun relocate(department: String) {
        if (this.department != department) {
            publish(EmployeeRelocated(id, department, this.department))
            this.department = department
        }
    }

    fun changeAge(age: Int) {
        if (this.age != age) {
            publish(EmployeeAgeChanged(id, age, this.age))
            this.age = age
        }
    }
}