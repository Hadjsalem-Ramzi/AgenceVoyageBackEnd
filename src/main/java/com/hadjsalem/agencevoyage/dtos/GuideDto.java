package com.hadjsalem.agencevoyage.dtos;


import lombok.*;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class GuideDto {

    private Long id;
    private String name;

}
