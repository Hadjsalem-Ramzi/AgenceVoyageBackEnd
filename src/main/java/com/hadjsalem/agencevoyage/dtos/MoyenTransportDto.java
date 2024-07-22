package com.hadjsalem.agencevoyage.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MoyenTransportDto {

private Long id;
private   String nom;
private   String Type;
private Long capacite;
}
