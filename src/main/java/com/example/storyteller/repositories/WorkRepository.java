package com.example.storyteller.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.storyteller.models.Work;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkRepository extends JpaRepository<Work, Long> {
    List<Work> findByTitle(String title);
    List<Work> findByAuthor_Nickname(String nickname);
}
