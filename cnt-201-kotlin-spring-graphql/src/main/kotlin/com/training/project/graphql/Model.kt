package com.training.project.graphql

data class Person(val id: Int, val firstName: String, val lastName: String, val addresses: List<Address>)
data class Address(val street: String)
