package ru.itis.terziyan.diplom.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.terziyan.diplom.entities.Message;

/**
 * Created by Vladislav Ulyanov on 23.05.17.
 * vk.com/etovladislav
 */
public interface MessageRepository extends JpaRepository<Message, Long> {


}

