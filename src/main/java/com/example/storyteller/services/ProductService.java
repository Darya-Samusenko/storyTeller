package com.example.storyteller.services;
import com.example.storyteller.models.Part;
import com.example.storyteller.models.Work;
import com.example.storyteller.models.User;
import com.example.storyteller.repositories.PartRepository;
import com.example.storyteller.repositories.WorkRepository;
import com.example.storyteller.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    private final WorkRepository productRepository;
    private final PartRepository partrepository;
    private final UserRepository userRepository;

    public List<Work> listProducts(String title) {
        if (title != null) return productRepository.findByTitle(title);
        return productRepository.findAll();
    }

    public void saveProduct(Principal principal, Work product, MultipartFile file1, MultipartFile file2, MultipartFile file3) throws IOException {
        product.setAuthor(getUserByPrincipal(principal));
        log.info("Saving new Work. Title: {}; Author email: {}", product.getTitle(), product.getAuthor().getEmail());
        Work productFromDb = productRepository.save(product);
        productRepository.save(product);
    }

    public User getUserByPrincipal(Principal principal) {
        if (principal == null) return new User();
        return userRepository.findByEmail(principal.getName());
    }

    public void deleteProduct(User user, Long id) {
        Work product = productRepository.findById(id)
                .orElse(null);
        if (product != null) {
            if (product.getAuthor().getId().equals(user.getId())) {
                List<Part> work_parts = product.getParts();
                for (Part part:work_parts
                     ) {
                    Part rmv_part = partrepository.findById(part.getId_part())
                            .orElse(null);
                    if(rmv_part != null){
                        log.info("Part of work with id = {} was deleted", part.getId_part());
                        partrepository.delete(part);
                    }
                    else
                        log.info("Part with id = {} was not found", part.getId_part());
                }
                productRepository.delete(product);
                log.info("Work with id = {} was deleted", id);
            } else {
                log.error("User: {} isn't author of work with id = {}", user.getEmail(), id);
            }
        } else {
            log.error("Work with id = {} is not found", id);
        }    }

    public Work getWorkById(Long id) {
        return productRepository.findById(id).orElse(null);
    }
}