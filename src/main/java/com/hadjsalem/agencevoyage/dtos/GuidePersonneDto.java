package com.hadjsalem.agencevoyage.dtos;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GuidePersonneDto {
    Long id;
    String firstName;
    String lastName;
    Integer numTel;
}
