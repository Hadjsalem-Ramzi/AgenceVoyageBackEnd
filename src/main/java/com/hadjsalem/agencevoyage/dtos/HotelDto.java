package com.hadjsalem.agencevoyage.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class HotelDto {
    private Long id;
    private String libelle;
    private String caracteristique;
    private Integer numberBed;
}
