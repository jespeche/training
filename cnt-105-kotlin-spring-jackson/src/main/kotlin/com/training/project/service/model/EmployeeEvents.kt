package com.training.project.service.model

import java.util.UUID

interface EmployeeEvent
data class EmployeeHired(val employeeId: UUID, val employeeName: String, val employeeDepartment: String, val employeeAge: Int) : EmployeeEvent
data class EmployeeFired(val employeeId: UUID) : EmployeeEvent
data class EmployeeRelocated(val employeeId: UUID, val employeeDepartment: String, val oldEmployeeDepartment: String) : EmployeeEvent
data class EmployeeAgeChanged(val employeeId: UUID, val employeeAge: Int, val oldEmployeeAge: Int) : EmployeeEvent