package com.example.storyteller.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.storyteller.models.Work;
import com.example.storyteller.models.Part;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartRepository extends JpaRepository<Part, Long> {
    List<Part> findByWork(Work src_work);
}
