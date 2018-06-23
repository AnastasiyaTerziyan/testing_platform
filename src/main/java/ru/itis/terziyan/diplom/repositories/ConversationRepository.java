package ru.itis.terziyan.diplom.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.itis.terziyan.diplom.entities.Conversation;

import java.util.List;

/**
 * Created by Vladislav Ulyanov on 30.05.17.
 * vk.com/etovladislav
 */
public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    @Query(value = "select c from Conversation c where c.user1.id = :u1 and c.user2.id = :u2 or c.user1.id = :u2 and c.user2.id = :u1 ")
    Conversation findConversation(@Param("u1") Long u1, @Param("u2") Long u2);

    @Query(value = "select c from Conversation c where c.user1.id = :u1 or c.user2.id = :u1")
    List<Conversation> findConversationByUserId(@Param("u1") Long u1);
}
