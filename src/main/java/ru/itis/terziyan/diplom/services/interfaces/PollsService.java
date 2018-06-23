package ru.itis.terziyan.diplom.services.interfaces;

import ru.itis.terziyan.diplom.entities.Poll;

import java.util.List;

/**
 * Created by unlim_000 on 03.06.2017.
 */
public interface PollsService {

    List<Poll> getAll();

    List<Poll> getByForWhom(String forWhom);
}
