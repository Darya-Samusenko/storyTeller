package com.example.storyteller.controllers;

import com.example.storyteller.repositories.PartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class PartController {
    private final PartRepository partrepository;
}
