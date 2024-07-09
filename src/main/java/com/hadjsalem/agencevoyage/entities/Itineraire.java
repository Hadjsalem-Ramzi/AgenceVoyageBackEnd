package com.hadjsalem.agencevoyage.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Itineraire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String libelle;
    private String caracteristique;

    @OneToMany(mappedBy = "itineraire")
    private List<Client> clients;

}
