package com.hadjsalem.agencevoyage.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Chauffeur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    @NotNull( message= "Cette Champ ne peut pas être null.")
    @NotEmpty(message = "Cette Champ ne peut pas être vide.")
    @NotBlank(message = "Cette Champ ne peut pas être composé uniquement d'espaces blancs.")
    private String firstName;

    @NotNull( message= "Cette Champ ne peut pas être null.")
    @NotEmpty(message = "Cette Champ ne peut pas être vide.")
    @NotBlank(message = "Cette Champ ne peut pas être composé uniquement d'espaces blancs.")
    private String lastName;

    @NotNull(message = "Le numéro de téléphone ne peut pas être null.")
    @Positive(message = "Le numéro de téléphone doit être positif.")
    @Digits(integer = 10, fraction = 0, message = "Le numéro de téléphone doit contenir exactement 10 chiffres.")
    private Long numTelephone;

    public Chauffeur(Long id, String firstName, String lastName, Long numTelephone) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.numTelephone = numTelephone;
    }

    @ManyToOne
    private Vehicule vehicule;
}
