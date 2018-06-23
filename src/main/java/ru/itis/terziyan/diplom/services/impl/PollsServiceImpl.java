package ru.itis.terziyan.diplom.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.terziyan.diplom.entities.Poll;
import ru.itis.terziyan.diplom.repositories.PollsRepository;
import ru.itis.terziyan.diplom.services.interfaces.PollsService;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by unlim_000 on 03.06.2017.
 */

@Service
public class PollsServiceImpl implements PollsService {

    private PollsRepository pollsRepository;

    @Autowired
    public PollsServiceImpl(PollsRepository pollsRepository) {
        this.pollsRepository = pollsRepository;
    }

    @Override
    @Transactional
    public List<Poll> getAll() {
        return pollsRepository.findAll();
    }

    @Override
    @Transactional
    public List<Poll> getByForWhom(String forWhom) {
        return pollsRepository.getAllByForWhom(forWhom);
    }
}
