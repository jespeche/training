package com.training.project.services.friendship

import org.springframework.stereotype.Service
import java.lang.Thread.sleep

@Service
class FriendshipService {
    fun lookForFriends(email: String) {
        println("[ Friendship Service ] - Friendship calculation started.")
        sleep(5000)
        println("[ Friendship Service ] - Friendship completed.")
    }
}
