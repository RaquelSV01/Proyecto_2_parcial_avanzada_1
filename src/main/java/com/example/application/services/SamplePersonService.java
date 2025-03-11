package com.example.application.services;

import com.example.application.data.SamplePerson;
import com.example.application.data.SamplePersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SamplePersonService {
    @Autowired
    private SamplePersonRepository repository;

    public void save(SamplePerson person) {
        repository.save(person);
    }

    public List<SamplePerson> list(Pageable pageable) {
        return repository.findAll(pageable).getContent();
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}



