package com.hadjsalem.agencevoyage.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Destination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String pays;
    private String ville;

    @ManyToMany(mappedBy = "destinations")
    private List<Client> clients;
}
