package ru.itis.terziyan.diplom.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.terziyan.diplom.entities.Poll;

import java.util.List;

/**
 * Created by unlim_000 on 03.06.2017.
 */
public interface PollsRepository extends JpaRepository<Poll, Long> {

    List<Poll> getAllByForWhom(String for_whom);
}
