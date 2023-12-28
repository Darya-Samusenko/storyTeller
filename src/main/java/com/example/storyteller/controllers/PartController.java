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
    /**
     * Возвращает ссылки на след.страницы:
     * 1)чтения части, если часть найдена
     * 2)ошибки, если часть не найдена
     *
     *  @param id_part искомая часть
     *  @param model модель страницы HTML
     *  @param principal пользователь
     *
     *  @return ссылка на следующую страницу*/
    @GetMapping("{id_work}/read/{id_part}")
    public String partContent(@PathVariable Long id_part,@PathVariable Long id_work, Model model, Principal principal) {
        Part curr_part = productService.getPartById(id_part);
        if(curr_part != null){
            model.addAttribute("user", principal);
            model.addAttribute("work", productService.getWorkById(id_work).getTitle());
            model.addAttribute("part_name", curr_part.getTitle_part());
            model.addAttribute("part_content", curr_part.getContent());
            model.addAttribute("back_ref", productService.getWorkById(id_work).getTitle());
            return "read_part";
        }
        return "error";
    }
}
