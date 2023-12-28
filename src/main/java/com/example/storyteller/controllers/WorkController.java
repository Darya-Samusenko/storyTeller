package com.example.storyteller.controllers;
import com.example.storyteller.models.Part;
import com.example.storyteller.models.Work;
import com.example.storyteller.models.User;
import com.example.storyteller.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class WorkController {
    private final ProductService productService;
    private final PartController partcontroller;
    @GetMapping("/")
    public String products(@RequestParam(name = "searchWord", required = false) String title, Principal principal, Model model) {
        model.addAttribute("works", productService.listProducts(title));
        model.addAttribute("user", productService.getUserByPrincipal(principal));
        model.addAttribute("searchWord", title);
        //productService.deleteAll();//если надо снести всю БД
        return "works";
    }

    @GetMapping("/work/{id}")
    public String productInfo(@PathVariable Long id, Model model, Principal principal) {
        Work product = productService.getWorkById(id);
        model.addAttribute("user", productService.getUserByPrincipal(principal));
        model.addAttribute("work", product);
        model.addAttribute("parts", product.getParts());
        model.addAttribute("authorWork", product.getAuthor().getName());
        return "work-info";
    }

    @PostMapping("/work/create")
    public String createProduct(Work product, Principal principal) throws IOException {
        productService.saveProduct(principal, product);
        return "redirect:/my/works";
    }

    @PostMapping("/work/{id_work}/add_part")
    public String createPart(@PathVariable Long id_work, Part part, Principal principal) throws IOException {//так работает
        Work product = productService.getWorkById(id_work);
        productService.addPartToWork(principal, part, product);
        return "redirect:/my/works";
    }

    @PostMapping("/work/add_part/{id_work}")
    public String redirectToCreatePart(@PathVariable Long id_work, Principal principal, Model model) throws IOException {//так работает
        Work product = productService.getWorkById(id_work);
        model.addAttribute("user", productService.getUserByPrincipal(principal));
        model.addAttribute("destWork",product);
        return "add_part";
    }

    @PostMapping("/work/delete/{id}")
    public String deleteProduct(@PathVariable Long id, Principal principal) {
        productService.deleteProduct(productService.getUserByPrincipal(principal), id);
        return "redirect:/my/works";
    }

    @GetMapping("/my/works")
    public String userProducts(Principal principal, Model model) {
        User user = productService.getUserByPrincipal(principal);
        model.addAttribute("user", user);
        model.addAttribute("works", user.getWorks());
        return "my-works";
    }
}
