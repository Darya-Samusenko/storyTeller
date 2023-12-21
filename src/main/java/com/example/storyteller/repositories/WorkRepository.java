package com.example.storyteller.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.storyteller.models.Work;
import java.util.List;

public interface WorkRepository extends JpaRepository<Work, Long> {
    List<Work> findByTitle(String title);
}
