package com.hadjsalem.agencevoyage.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GuidePapier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;


    @NotNull( message= "Cette Champ ne peut pas être null.")
    @NotEmpty(message = "Cette Champ ne peut pas être vide.")
    @NotBlank(message = "Cette Champ ne peut pas être composé uniquement d'espaces blancs.")
    private String libelle;
}
