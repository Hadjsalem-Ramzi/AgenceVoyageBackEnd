package com.hadjsalem.agencevoyage.dtos;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class VehiculeDto {
private     Long id;
private   String immatricule;
private   String type;
private    Integer capacite;
}
