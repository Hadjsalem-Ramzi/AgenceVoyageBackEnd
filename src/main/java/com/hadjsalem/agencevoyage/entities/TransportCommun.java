package com.hadjsalem.agencevoyage.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@DiscriminatorValue("TransportCommun")
public class TransportCommun  extends CompagnieTransport{

    private  Integer capacite;

    public TransportCommun(Long id, String nom, Integer numTel, Integer capacite) {
        super();
        this.setNom(nom);
        this.setId(id);
        this.setNumTel(numTel);
        this.capacite = capacite;
    }


}
