package org.rorschachdb.nephilim.online.creator.back.repositories;

import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;
import org.rorschachdb.nephilim.online.creator.back.model.Degree;
import org.rorschachdb.nephilim.online.creator.back.model.enums.DegreeTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class DegreeRepositoryShould {

    @Autowired
    private DegreeRepository degreeRepository;

    @Test
    public void createAndRetrieveDegrees(){
        Degree.DegreeBuilder degreeBuilder = Degree.builder();
        Degree degree1 = degreeBuilder
                .name("Babylonian Baker")
                .description("Hmmm, fresh bread")
                .type(DegreeTypeEnum.SIMULACRUM)
                .build();
        degreeRepository.save(degree1);

        Degree degree2 = degreeBuilder
                .name("Celestial Antlantis")
                .description(null)
                .type(DegreeTypeEnum.ESOTERIC_QUEST)
                .build();
        degreeRepository.save(degree2);

        List<Degree> degrees = (List<Degree>) degreeRepository.findAll();

        assertThat(degrees)
                .hasSize(2)
                .extracting("name", "description", "type")
                .containsExactlyInAnyOrder(new Tuple("Babylonian Baker", "Hmmm, fresh bread", DegreeTypeEnum.SIMULACRUM), new Tuple("Celestial Antlantis", null, DegreeTypeEnum.ESOTERIC_QUEST));

    }
}