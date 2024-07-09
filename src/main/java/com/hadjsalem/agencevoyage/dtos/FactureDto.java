package com.hadjsalem.agencevoyage.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public record FactureDto(Long id,String designation,Long quantite,Double prixUnitaire,Double prixTotal,Double Total) {
}
