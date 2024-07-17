package com.hadjsalem.agencevoyage.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue("SocieteLocation")
public class SocieteLocation extends CompagnieTransport {

    @OneToMany(mappedBy = "societeLocation")
    List<Vehicule> vehicules;

    public SocieteLocation(Integer id1, String nom1, Integer numTel1) {
        super();
        this.setId(id1);
        this.setNom(nom1);
        this.setNumTel(numTel1);
    }
}
