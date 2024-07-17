package com.hadjsalem.agencevoyage.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Facture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String designation;
    private Long quantite;
    private Double prixUnitaire;
    private Double prixTotal;

    @ManyToOne
    private Client client;

}
