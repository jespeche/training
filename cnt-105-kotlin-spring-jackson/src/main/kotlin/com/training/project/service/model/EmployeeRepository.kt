package com.training.project.service.model

import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface EmployeeRepository : CrudRepository<Employee, UUID>