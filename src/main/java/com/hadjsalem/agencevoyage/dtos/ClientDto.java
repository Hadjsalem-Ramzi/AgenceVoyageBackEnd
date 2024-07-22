package com.hadjsalem.agencevoyage.dtos;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ClientDto{

    private  Long id ;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
}
