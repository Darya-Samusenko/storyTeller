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

    public void saveProduct(Principal principal, Work product) throws IOException {
        product.setAuthor(getUserByPrincipal(principal));
        log.info("Saving new Work. Title: {}; Author email: {}", product.getTitle(), product.getAuthor().getEmail());
        productRepository.save(product);
    }

    public User getUserByPrincipal(Principal principal) {
        if (principal == null) return new User();
        return userRepository.findByEmail(principal.getName());
    }

    public void addPartToWork(Principal principal, Part added_part, Work src_work)throws IOException{
        Long part_id = src_work.getIdForNewPart();
        added_part.setId(part_id);
        src_work.addPart(added_part);
        log.info("Saving new Part. Work Title: {}; Part Title: {}; Author Email: {}", src_work.getTitle(), added_part.getTitle_part(),getUserByPrincipal(principal).getEmail());
        partrepository.save(added_part);
    }

    public void deleteAll(){

        partrepository.deleteAll();
        productRepository.deleteAll();
        userRepository.deleteAll();


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
                        partrepository.delete(part);
                        log.info("Part of work with id = {} was deleted", part.getId_part());
                    }
                    else
                        log.info("Part with id = {} was not found", part.getId_part());
                }
                productRepository.delete(product);
                if(productRepository.findById(id).orElse(null)!=null)
                    log.info("Suddenly wasn't deleted");
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

    public Part getPartById(Long id) {
        return partrepository.findById(id).orElse(null);
    }
}