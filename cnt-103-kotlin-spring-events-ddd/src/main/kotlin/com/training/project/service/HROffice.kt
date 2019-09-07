package com.training.project.service

import com.training.project.service.model.Employee
import java.util.UUID

interface HROffice {
    fun employee(employeeId: UUID): Employee
    fun employees(): List<Employee>
    fun hireEmployee(name: String, department: String, age: Int): Employee
    fun fireEmployee(employeeId: UUID)
    fun relocateEmployee(employeeId: UUID, department: String): Employee
    fun changeEmployeeAge(employeeId: UUID, age: Int): Employee
}