package com.hadjsalem.agencevoyage.dtos;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DestinationDto {
    private Long id;
    private String pays;
    private  String ville;

}
