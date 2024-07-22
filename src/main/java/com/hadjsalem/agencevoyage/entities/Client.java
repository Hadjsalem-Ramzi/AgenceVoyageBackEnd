package com.hadjsalem.agencevoyage.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @Builder
public class Client {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    @NotNull( message= "Cette Champ ne peut pas être null.")
    @NotEmpty(message = "Cette Champ ne peut pas être vide.")
    @NotBlank(message = "Cette Champ ne peut pas être composé uniquement d'espaces blancs.")
    private String firstName;

    @NotNull( message= "Cette Champ ne peut pas être null.")
    @NotEmpty(message = "Cette Champ ne peut pas être vide.")
    @NotBlank(message = "Cette Champ ne peut pas être composé uniquement d'espaces blancs.")
    private String lastName;

    @NotNull(message = "Le mot de passe ne peut pas être null.")
    @Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caractères.")
    private String password;

    @NotNull(message = "L'adresse email ne peut pas être null.")
    @Email(message = "L'adresse email doit être valide.")
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
