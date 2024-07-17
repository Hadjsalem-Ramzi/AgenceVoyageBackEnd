package com.hadjsalem.agencevoyage.repositories;
import com.hadjsalem.agencevoyage.entities.Chauffeur;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ChauffeurRepositoryTest {

    @Autowired
    private ChauffeurRepository chauffeurRepository;

    @BeforeEach
    public void setUp(){
        chauffeurRepository.save(Chauffeur.builder().firstName("Ramzi").lastName("Hadjsalem").numTelephone(54604022L).build());
        chauffeurRepository.save(Chauffeur.builder().firstName("Mohsen").lastName("Dabdaba").numTelephone(54658022L).build());
        chauffeurRepository.save(Chauffeur.builder().firstName("Aymen").lastName("HadjAli").numTelephone(54656222L).build());
    }

    @Test
    public void ShouldFindChauffeurByNumTelephone(){
            Long numTel= 54604022L;
            Optional<Chauffeur> result = chauffeurRepository.findChauffeurByNumTelephone(numTel);

        AssertionsForClassTypes.assertThat(result).isPresent();

    }

    @Test
    public void ShouldNotFindChauffeurByNumTelephone(){
        Long numTel= 54621111L;
        Optional<Chauffeur> result = chauffeurRepository.findChauffeurByNumTelephone(numTel);
       AssertionsForClassTypes.assertThat(result).isEmpty();

    }

}
