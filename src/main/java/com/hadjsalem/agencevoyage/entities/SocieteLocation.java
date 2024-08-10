package com.hadjsalem.agencevoyage.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@DiscriminatorValue("SL")
public class SocieteLocation extends CompagnieTransport {

    @OneToMany(mappedBy = "societeLocation")
    List<Vehicule> vehicules;


    public SocieteLocation(Long id, String nom, Integer numTel) {
        super();
        this.setId(id);
        this.setNom(nom);
        this.setNumTel(numTel);
    }
}
