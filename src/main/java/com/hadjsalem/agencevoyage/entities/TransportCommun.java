package com.hadjsalem.agencevoyage.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@DiscriminatorValue("TransportCommun")
public class TransportCommun  extends CompagnieTransport{

}
