package com.hadjsalem.agencevoyage.dtos;



public record FactureDto(Long id,String designation,Long quantite,Double prixUnitaire,Double prixTotal,Double Total) {
}
