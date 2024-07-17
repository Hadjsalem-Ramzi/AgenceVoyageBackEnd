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
public class Vehicule  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String immatricule;
    private String type;
    private Integer capacite;

    @ManyToOne
    private SocieteLocation societeLocation;

    @OneToMany(mappedBy = "vehicule")
    List<Chauffeur>chauffeurs;
}
