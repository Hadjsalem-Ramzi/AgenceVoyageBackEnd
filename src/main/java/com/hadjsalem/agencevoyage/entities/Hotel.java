package com.hadjsalem.agencevoyage.entities;

import jakarta.persistence.*;
import lombok.*;

import javax.print.attribute.IntegerSyntax;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private  String libelle;
    private String caracteristique;
    private Integer numberBed;

    @ManyToMany(mappedBy = "hotels")
    private List<Client> clients;
}
