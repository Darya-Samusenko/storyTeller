package com.example.storyteller.controllers;

import com.example.storyteller.models.Part;
import com.example.storyteller.models.User;
import com.example.storyteller.models.Work;
import com.example.storyteller.repositories.PartRepository;
import com.example.storyteller.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class PartController {
    private final ProductService productService;
    @GetMapping("/read/{id_part}")
    public String partContent(@PathVariable Long id_part, Model model, Principal principal) {
        Part curr_part = productService.getPartById(id_part);
        if(curr_part != null){
            model.addAttribute("user", principal);
            model.addAttribute("work", curr_part.getSrc().getTitle());
            model.addAttribute("part_name", curr_part.getTitle_part());
            model.addAttribute("part_content", curr_part.getContent());
            model.addAttribute("back_ref", curr_part.getSrc().getId());
            return "read_part";
        }
        return "error";
    }
}
