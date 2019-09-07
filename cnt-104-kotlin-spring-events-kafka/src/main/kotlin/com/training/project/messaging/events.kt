package com.training.project.messaging

import java.util.UUID

data class EmployeeHired(val employeeId: UUID = UUID.randomUUID(), val employeeName: String = "")