package com.hadjsalem.agencevoyage.dtos;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SocieteLocationDto {
  private  Long id;
   private String nom;
   private Integer numTel;

}
