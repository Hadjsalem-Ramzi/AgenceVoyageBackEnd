package com.hadjsalem.agencevoyage.dtos;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TransportCommunDto {
  private   Integer id;
  private   String nom;
  private   Integer numTel;
  private   Integer capacite;
}
