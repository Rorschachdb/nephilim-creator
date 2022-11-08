package org.rorschachdb.nephilim.online.creator.back.repositories;

import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;
import org.rorschachdb.nephilim.online.creator.back.model.Degree;
import org.rorschachdb.nephilim.online.creator.back.model.IncarnationEpoch;
import org.rorschachdb.nephilim.online.creator.back.model.enums.DegreeTypeEnum;
import org.rorschachdb.nephilim.online.creator.back.model.enums.EraEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
@Transactional
public class DegreeRepositoryShould {

    @Autowired
    private DegreeRepository degreeRepository;

    @Autowired
    private EntityManager em;

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

    @Test
    public void failOnSameNamePersistence() {

        // ASSERT THAT
        assertThatThrownBy(() ->{
            // GIVEN
            Degree.DegreeBuilder degreeBuilder = Degree.builder();
            Degree degree1 = degreeBuilder
                    .name("Celestial Antlantis")
                    .description(null)
                    .type(DegreeTypeEnum.ESOTERIC_QUEST)
                    .build();
            Degree degree2 = degreeBuilder
                    .name("Celestial Antlantis")
                    .description(null)
                    .type(DegreeTypeEnum.ESOTERIC_QUEST)
                    .build();

            //WHEN
            degreeRepository.save(degree1);
            degreeRepository.save(degree2);
            em.flush();
        }).isInstanceOf(PersistenceException.class);
    }
    @Test
    public void failOnBlankNameValidation() {

        // ASSERT THAT
        assertThatThrownBy(() ->{
        // GIVEN
        Degree.DegreeBuilder degreeBuilder = Degree.builder();
        Degree degree1 = degreeBuilder
                .name("")
                .description(null)
                .type(DegreeTypeEnum.ESOTERIC_QUEST)
                .build();
        //WHEN
        degreeRepository.save(degree1);
        em.flush();
        }).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void failOnNullTypeValidation() {

        // ASSERT THAT
        assertThatThrownBy(() ->{
        // GIVEN
        Degree.DegreeBuilder degreeBuilder = Degree.builder();
        Degree degree1 = degreeBuilder
                .name("Celestial Atlantis")
                .description(null)
                .type(null)
                .build();
        //WHEN
        degreeRepository.save(degree1);
        em.flush();
        }).isInstanceOf(ConstraintViolationException.class);
    }
}