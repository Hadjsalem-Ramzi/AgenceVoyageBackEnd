package com.hadjsalem.agencevoyage.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Destination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @NotNull( message= "Cette Champ ne peut pas être null.")
    @NotEmpty(message = "Cette Champ ne peut pas être vide.")
    @NotBlank(message = "Cette Champ ne peut pas être composé uniquement d'espaces blancs.")
    private String pays;

    @NotNull( message= "Cette Champ ne peut pas être null.")
    @NotEmpty(message = "Cette Champ ne peut pas être vide.")
    @NotBlank(message = "Cette Champ ne peut pas être composé uniquement d'espaces blancs.")
    private String ville;

    @ManyToMany(mappedBy = "destinations")
    private List<Client> clients;
}
