package com.hadjsalem.agencevoyage.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public record IteneraireDto(Long id,String libelle,String caracteristique) {
}
