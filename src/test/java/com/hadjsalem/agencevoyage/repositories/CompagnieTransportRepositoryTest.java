package com.hadjsalem.agencevoyage.repositories;

import com.hadjsalem.agencevoyage.entities.CompagnieTransport;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CompagnieTransportRepositoryTest {

    @Autowired
    private CompagnieTransportRepository compagnieTransportRepository;

    @BeforeEach
    public void SetUp(){
        compagnieTransportRepository.save(CompagnieTransport.builder().nom("TravelComp").numTel(75641256).build());
        compagnieTransportRepository.save(CompagnieTransport.builder().nom("TunisiaComp").numTel(75658956).build());
        compagnieTransportRepository.save(CompagnieTransport.builder().nom("VacanceComp").numTel(75655556).build());
    }

    @Test
    public void ShouldFindCompagnieTransportByNom(){
        String nom="TravelComp";
        Optional<CompagnieTransport> result= compagnieTransportRepository.findCompagnieTransportByNom(nom);

        AssertionsForClassTypes.assertThat(result).isPresent();
    }

    @Test
    public void ShouldNotFindCompagnieTransportByNom(){
        String nom="blabla";
        Optional<CompagnieTransport> result= compagnieTransportRepository.findCompagnieTransportByNom(nom);

        AssertionsForClassTypes.assertThat(result).isEmpty();
    }


}
