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
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private Work src;

    public void setPartNumber(int number) {
        this.part_num = number;
    }
    public Part(){}
    public Part(String name, String content, Work work){
        this.title_part = name;
        this.content = content;
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
    public Long getId() {
        return this.id_part;
    }

    public void setId(Long id) {
        this.id_part = id;
    }

    public void setSrc(Work work){
        this.src = work;
    }
    @PrePersist
    private void onCreate() {

    }
}