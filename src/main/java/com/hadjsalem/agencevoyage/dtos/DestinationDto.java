package com.hadjsalem.agencevoyage.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public record DestinationDto(Long id,String pays,String ville) {
}
