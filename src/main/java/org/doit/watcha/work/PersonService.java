package org.doit.watcha.work;

import java.util.List;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public List<Person> searchByName(String keyword) {
        return personRepository.searchRelatedPersons(keyword);
    }
}