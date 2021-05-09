package com.example.iwwof.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Business {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private String image;

    private String brandName;

    private String description;

    private String category;

    private String location;

    private String websites;

    private String additionalOffer;

    private boolean isPublished;

}
