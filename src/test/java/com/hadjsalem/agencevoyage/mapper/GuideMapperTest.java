package com.hadjsalem.agencevoyage.mapper;
import com.hadjsalem.agencevoyage.dtos.GuideDto;
import com.hadjsalem.agencevoyage.entities.Guide;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.AssertionsForClassTypes.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class GuideMapperTest {

    @Autowired
    GuideMapper underTest;

    @Test
    public  void ShouldMapGuideToGuideDto(){

        Guide givenGuide = Guide.builder().name("Ramzi").build();
        GuideDto expected = GuideDto.builder().name("Ramzi").build();

        GuideDto result = underTest.fromGuide(givenGuide);

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);

    }

    @Test
    public  void ShouldMapGuideDtoToGuide(){

        GuideDto givenGuide = GuideDto.builder().name("Ramzi").build();
        Guide expected = Guide.builder().name("Ramzi").build();

        Guide result = underTest.fromGuideDto(givenGuide);

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);

    }

    @Test
    public void ShouldNotMapNullGuideToGuideDto(){
        Guide givenGuide = null;

        assertThatThrownBy(()->underTest.fromGuide(givenGuide)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void ShouldNotMapNullGuideDtoToGuide(){
        GuideDto givenGuide = null;

        assertThatThrownBy(()->underTest.fromGuideDto(givenGuide)).isInstanceOf(IllegalArgumentException.class);
    }


}
