package com.training.project.service.infrastructure

import org.springframework.data.domain.AfterDomainEventPublication
import org.springframework.data.domain.DomainEvents
import org.springframework.util.Assert.notNull
import java.util.ArrayList
import java.util.Collections.unmodifiableList

/**
 * Aggregate Root representation extending {@link AbstractBaseEntity} and {@link AbstractAggregateRoot}.
 */
abstract class AggregateRoot<EventsType> {

    @Transient
    private val domainEvents = ArrayList<EventsType>()

    /**
     * All domain events currently captured by the aggregate.
     */
    @DomainEvents
    fun domainEvents(): List<EventsType> = unmodifiableList<EventsType>(domainEvents)

    /**
     * Clears all domain events currently held. Usually invoked by the infrastructure in place in Spring Data
     * repositories.
     */
    @AfterDomainEventPublication
    private fun clearDomainEvents() = domainEvents.clear()

    /**
     * Registers the given event object for publication on a call to a Spring Data repository's save methods.
     */
    protected fun publish(event: EventsType): EventsType = event.apply {
        notNull(this, "Null event type can not be registered")
        domainEvents.add(this)
    }
}