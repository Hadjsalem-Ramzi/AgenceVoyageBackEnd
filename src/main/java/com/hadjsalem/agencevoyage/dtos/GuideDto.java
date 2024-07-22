package com.hadjsalem.agencevoyage.dtos;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GuideDto {

    private Long id;
    private String firstName;
    private String lastName;
    private Integer numTel;
    private String specialite;
}
