package com.training.project.services.friendship

import com.training.project.services.user.UserRegistered
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class UsersEventService(@Autowired val service: FriendshipService) {

    @EventListener
    fun handleUserRegistered(event: UserRegistered) {
        println("[ Friendship Service ] - <UserRegistered> event received for ${event.user}")
        service.lookForFriends(event.email)
        println("[ Friendship Service ] - <UserRegistered> event processed.")
    }
}