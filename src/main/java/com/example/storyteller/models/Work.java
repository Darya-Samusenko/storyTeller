package com.example.storyteller.models;
import com.example.storyteller.models.enums.WorkTypes;
import jakarta.persistence.*;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "works")
@Data
public class Work {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_work;
    private String title;
    private String description;
    private String author_name;
    private WorkTypes type;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User author;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,
            mappedBy = "src")
    private List<Part> parts = new ArrayList<>();
    public List<Part> getParts() {
        if (parts.isEmpty())
            return null;
        return parts;
    }

    public Long getId() {
        return id_work;
    }

    public void setId(Long id) {
        this.id_work = id;
    }

    public void addPart(Part new_part){
        this.parts.add(new_part);
    }
    public boolean deletePart(Part prt_to_remove){
        return this.parts.remove(prt_to_remove);
    }
    public void setAuthor(User user){
        this.author = user;
        this.author_name = user.getName();
    }

    public User getAuthor(){
        return this.author;
    }

    public String getAuthorName(){
        return this.author_name;
    }
    @PrePersist
    private void onCreate() {  }
}
