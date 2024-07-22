package com.hadjsalem.agencevoyage.dtos;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ChauffeurDto {

    private Long id ;

    private String firstName;


    private String lastName;


    private Long numTelephone;

}
