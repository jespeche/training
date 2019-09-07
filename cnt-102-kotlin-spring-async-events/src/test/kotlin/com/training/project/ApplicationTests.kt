package com.training.project

import com.training.project.services.friendship.FriendshipService
import com.training.project.services.mailing.MailingService
import com.training.project.services.user.UserService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ApplicationTests(
    @Autowired val userService: UserService,
    @Autowired val mailingService: MailingService,
    @Autowired val friendshipService: FriendshipService
) {

    @Test
    fun contextLoads() {
        assertThat(userService).isNotNull
        assertThat(mailingService).isNotNull
        assertThat(friendshipService).isNotNull
    }
}
