package org.doit.watcha.work;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Integer> {

    List<Genre> findAllByOrderByGenreIdAsc();

}