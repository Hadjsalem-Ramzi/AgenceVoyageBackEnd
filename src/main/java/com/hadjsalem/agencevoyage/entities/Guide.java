package com.hadjsalem.agencevoyage.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Guide {
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
    private Integer numTel;

    @NotNull( message= "Cette Champ ne peut pas être null.")
    @NotEmpty(message = "Cette Champ ne peut pas être vide.")
    @NotBlank(message = "Cette Champ ne peut pas être composé uniquement d'espaces blancs.")
    private String specialite;

    @OneToMany(mappedBy = "guide")
    private List<Client> clients;
}
