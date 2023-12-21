package com.example.storyteller.models;

import jakarta.persistence.*;
import lombok.Data;
@Entity
@Table(name = "parts")
@Data
public class Part {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_part;
    private String title_part;
    private String content;
    private int part_num;
    @ManyToOne
    @JoinColumn(name = "id_work")
    private Work src;

    public int getPartNum() {
        return part_num;
    }
    public void setPartNumber() {
        if (src != null && src.getParts() != null) {
            int maxPartNum = src.getParts().stream()
                    .mapToInt(Part::getPartNum)
                    .max()
                    .orElse(0);
            part_num = maxPartNum + 1;
        } else {
            part_num = 0; // Если Work или список Part пустые
        }
    }

    private String getPartContent(){
        return this.content;
    }
    private String getPartTitle(){
        return this.title_part;
    }
    @PrePersist
    private void onCreate() {
        setPartNumber();
    }
}