package com.hadjsalem.agencevoyage.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@Inheritance(strategy =InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="Type_Transport")
public class CompagnieTransport  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    @NotNull( message= "Cette Champ ne peut pas être null.")
    @NotEmpty(message = "Cette Champ ne peut pas être vide.")
    @NotBlank(message = "Cette Champ ne peut pas être composé uniquement d'espaces blancs.")
    private String name;

    @NotNull(message = "Le numéro de téléphone ne peut pas être null.")
    @Positive(message = "Le numéro de téléphone doit être positif.")
    @Digits(integer = 10, fraction = 0, message = "Le numéro de téléphone doit contenir exactement 10 chiffres.")
    private Integer numTel;


    @OneToMany(mappedBy = "compagnieTransport")
    List<MoyenTransport> moyenTransportList;
}
