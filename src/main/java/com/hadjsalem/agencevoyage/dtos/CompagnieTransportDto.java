package com.hadjsalem.agencevoyage.dtos;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CompagnieTransportDto {

   private Long id;
    private  String nom;
    private  Integer numTel;
}
