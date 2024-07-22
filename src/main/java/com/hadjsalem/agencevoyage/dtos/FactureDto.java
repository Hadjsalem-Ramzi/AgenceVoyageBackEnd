package com.hadjsalem.agencevoyage.dtos;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class FactureDto {
    private  Long id;
    private   String designation;
    private Long quantite;
    private Double prixUnitaire;
    private   Double prixTotal;
    private Double Total;
}
