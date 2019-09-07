package com.training.project.graphql

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import com.coxautodev.graphql.tools.GraphQLQueryResolver
import com.coxautodev.graphql.tools.GraphQLResolver
import org.springframework.stereotype.Component

@Component
class PersonMutationResolver : GraphQLMutationResolver {
    fun createPerson(name: String) = person
}

@Component
class PersonQueryResolver : GraphQLQueryResolver {
    fun person(id: String) = person
}

@Component
class AddressResolver : GraphQLResolver<Person> {
    fun addresses(person: Person, type: String?) = when (type) {
        "one" -> listOf(person.addresses[0])
        else -> person.addresses
    }
}

@Component
class FirstNameResolver : GraphQLResolver<Person> {
    fun firstName(person: Person, type: String?) = when (type) {
        "uppercase" -> person.firstName.toUpperCase()
        else -> person.firstName
    }
}

val address1 = Address("Oracle Parkway 500")
val address2 = Address("Oracle Parkway 300")
val person = Person(1, "Craig", "Larman", listOf(address1, address2))
