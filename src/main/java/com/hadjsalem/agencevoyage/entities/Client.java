package com.hadjsalem.agencevoyage.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @Builder
public class Client {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String firstName;
    private String lastName;
    private String password;
    private String email;

  @ManyToMany
  @JoinTable(
          name = "client_destination", // Name of the join table
          joinColumns = @JoinColumn(name = "client_id"), // Foreign key to Client table
          inverseJoinColumns = @JoinColumn(name = "destination_id") // Foreign key to Destination table
  )
    List<Destination> destinations;

    @ManyToMany
    @JoinTable(
            name = "client_hotel", // Name of the join table
            joinColumns = @JoinColumn(name = "client_id"), // Foreign key to Client table
            inverseJoinColumns = @JoinColumn(name = "hotel_id") // Foreign key to Destination table
    )
    List<Hotel> hotels;

    @ManyToOne
    private Guide guide;

  @OneToMany(mappedBy = "client")
    private List<Reservation> reservations;

  @ManyToOne
    private Itineraire itineraire;

  @OneToMany(mappedBy = "client")
    private List<Facture> factures;

  @ManyToOne
    private MoyenTransport moyenTransport;

}
