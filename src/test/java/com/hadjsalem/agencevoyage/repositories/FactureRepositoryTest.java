package com.hadjsalem.agencevoyage.repositories;


import com.hadjsalem.agencevoyage.entities.Facture;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FactureRepositoryTest {
    @Autowired
    private FactureRepository factureRepository;

    @BeforeEach
    public void SetUp(){
        factureRepository.save(Facture.builder().designation("Facture1").prixUnitaire(150.00).prixTotal(1500.00).build());
        factureRepository.save(Facture.builder().designation("Facture2").prixUnitaire(200.00).prixTotal(3000.00).build());
    }

    @Test
    public void SouldFindFactureByDesignation(){
        String designation="Facture1";

        Optional<Facture> result =factureRepository.findFactureByDesignation(designation);

        AssertionsForClassTypes.assertThat(result).isPresent();
    }
    @Test
    public void SouldNotFindFactureByDesignation(){
        String designation="Fhdgdjkjdkkd";

        Optional<Facture> result =factureRepository.findFactureByDesignation(designation);

        AssertionsForClassTypes.assertThat(result).isEmpty();
    }

}
