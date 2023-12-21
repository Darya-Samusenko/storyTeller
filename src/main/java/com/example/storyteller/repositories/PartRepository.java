package com.example.storyteller.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.storyteller.models.Work;
import com.example.storyteller.models.Part;
import java.util.List;

public interface PartRepository extends JpaRepository<Part, Long> {
    List<Part> findByWork(Work src_work);
}
