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
public class MoyenTransport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String nom;
    private String Type;
    private Long capacite;

    @OneToMany(mappedBy = "moyenTransport")
    private List<Client> clients;

    @ManyToOne
    private CompagnieTransport compagnieTransport;
}
