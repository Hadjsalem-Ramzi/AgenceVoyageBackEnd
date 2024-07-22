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
public class Facture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @NotNull( message= "Cette Champ ne peut pas être null.")
    @NotEmpty(message = "Cette Champ ne peut pas être vide.")
    @NotBlank(message = "Cette Champ ne peut pas être composé uniquement d'espaces blancs.")
    private String designation;

    @NotEmpty(message = "Cette Champ ne peut pas être vide.")
    @NotBlank(message = "Cette Champ ne peut pas être composé uniquement d'espaces blancs.")
    @Min(value = 1,message = "la Quantité doit être au minumum 1")
    private Long quantite;

    @NotNull(message = "Ce champ ne peut pas être null.")
    @Positive(message = "Ce Champ doit être positif.")
    private Double prixUnitaire;

    @NotNull(message = "Ce champ ne peut pas être null.")
    @Positive(message = "Ce Champ doit être positif.")
    private Double prixTotal;

    @NotNull(message = "Ce champ ne peut pas être null.")
    @Positive(message = "Ce Champ doit être positif.")
    private Double Total;

    @ManyToOne
    private Client client;

}
