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


    public void setPartNumber() {
        if (this.src != null && this.src.getParts() != null) {
            int maxPartNum = this.src.getParts().stream()
                    .mapToInt(Part::getPartNum)
                    .max()
                    .orElse(0);
            this.part_num = maxPartNum + 1;
        } else {
            this.part_num = 0; // Если Work или список Part пустые
        }
    }
    public Part(){}
    public Part(String name, String content, Work work){
        this.title_part = name;
        this.content = content;
        this.src = work;
        setPartNumber();
    }
    public int getPartNum() {
        return part_num;
    }


    private String getPartContent(){
        return this.content;
    }
    private String getPartTitle(){
        return this.title_part;
    }
    @PrePersist
    private void onCreate() {

    }
}