package com.hadjsalem.agencevoyage.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import javax.print.attribute.IntegerSyntax;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @NotNull( message= "Cette Champ ne peut pas être null.")
    @NotEmpty(message = "Cette Champ ne peut pas être vide.")
    @NotBlank(message = "Cette Champ ne peut pas être composé uniquement d'espaces blancs.")
    private  String libelle;

    @NotNull( message= "Cette Champ ne peut pas être null.")
    @NotEmpty(message = "Cette Champ ne peut pas être vide.")
    @NotBlank(message = "Cette Champ ne peut pas être composé uniquement d'espaces blancs.")
    private String caracteristique;

    @NotNull( message= "Cette Champ ne peut pas être null.")
    @NotEmpty(message = "Cette Champ ne peut pas être vide.")
    @NotBlank(message = "Cette Champ ne peut pas être composé uniquement d'espaces blancs.")
    @Positive(message = "Cette champ doit être positive")
    private Integer numberBed;

    @ManyToMany(mappedBy = "hotels")
    private List<Client> clients;
}
