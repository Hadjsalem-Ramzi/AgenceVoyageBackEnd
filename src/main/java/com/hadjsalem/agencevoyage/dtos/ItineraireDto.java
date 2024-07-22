package com.hadjsalem.agencevoyage.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ItineraireDto {
    private Long id;
    private    String libelle;
    private String caracteristique;
}
