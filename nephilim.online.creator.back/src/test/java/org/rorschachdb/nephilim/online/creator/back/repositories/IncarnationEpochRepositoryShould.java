package org.rorschachdb.nephilim.online.creator.back.repositories;

import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;
import org.rorschachdb.nephilim.online.creator.back.model.IncarnationEpoch;
import org.rorschachdb.nephilim.online.creator.back.model.enums.EraEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class IncarnationEpochRepositoryShould {

    @Autowired
    private EntityManager em;

    @Autowired
    private IncarnationEpochRepository incarnationEpochRepository;

    @Test
    public void createAndRetrieveEpochs() {
        // GIVEN
        IncarnationEpoch.IncarnationEpochBuilder epochBuilder = IncarnationEpoch.builder();
        IncarnationEpoch epoch = epochBuilder
                .name("A long Time Ago ...")
                .cost(2)
                .description("There was a king")
                .location("one")
                .era(EraEnum.SECRET_COMPACTS)
                .build();
        epochBuilder = IncarnationEpoch.builder();
        IncarnationEpoch epoch2 = epochBuilder
                .name("Nowadays")
                .cost(1)
                .era(EraEnum.GREAT_AWAKENING)
                .location("two")
                .description("Pre-apo")
                .build();
        // WHEN
        incarnationEpochRepository.save(epoch);
        incarnationEpochRepository.save(epoch2);
        List<IncarnationEpoch> epochs = (List<IncarnationEpoch>) incarnationEpochRepository.findAll();

        // THEN
        assertThat(epochs)
                .isNotNull()
                .isNotEmpty()
                .hasSize(2)
                .extracting("name", "cost", "description", "era")
                .containsExactlyInAnyOrder(new Tuple("A long Time Ago ...", 2, "There was a king", EraEnum.SECRET_COMPACTS),
                                                    new Tuple("Nowadays", 1, "Pre-apo", EraEnum.GREAT_AWAKENING));
    }

    @Test
    public void createAndRetrieveEpochsWithLocations() {
        // GIVEN
        IncarnationEpoch.IncarnationEpochBuilder epochBuilder = IncarnationEpoch.builder();
        IncarnationEpoch epoch = epochBuilder
                .name("A long Time Ago ...")
                .cost(2)
                .location("Hyboria")
                .era(EraEnum.SECRET_COMPACTS)
                .build();
        epochBuilder = IncarnationEpoch.builder();
        IncarnationEpoch epoch2 = epochBuilder
                .name("Nowadays")
                .cost(1)
                .location("Lyon")
                .location("Brittany")
                .era(EraEnum.GREAT_AWAKENING)
                .build();
            incarnationEpochRepository.save(epoch);
            incarnationEpochRepository.save(epoch2);

        List<IncarnationEpoch> epochs = (List<IncarnationEpoch>) incarnationEpochRepository.findAll();

        assertThat(epochs)
                .isNotNull()
                .isNotEmpty()
                .hasSize(2)
                .flatExtracting("locations")
                .containsExactlyInAnyOrder("Hyboria", "Lyon", "Brittany");
    }

    @Test
    public void failOnLocationValidation() {

        // ASSERT THAT
        assertThatThrownBy(() ->{
            // GIVEN
            IncarnationEpoch.IncarnationEpochBuilder epochBuilder = IncarnationEpoch.builder();
            IncarnationEpoch epoch = epochBuilder
                    .name("A long Time Ago ...")
                    .cost(2)
                    .description("There was a king")
                    .era(EraEnum.SECRET_COMPACTS)
                    .build();

            //WHEN
            incarnationEpochRepository.save(epoch);
            em.flush();
            }).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void failOnBLankNameValidation() {

        // ASSERT THAT
        assertThatThrownBy(() ->{
            // GIVEN
            IncarnationEpoch.IncarnationEpochBuilder epochBuilder = IncarnationEpoch.builder();
            IncarnationEpoch epoch = epochBuilder
                    .name("")
                    .cost(2)
                    .description("There was a king")
                    .location("Atlantis")
                    .era(EraEnum.SECRET_COMPACTS)
                    .build();

            //WHEN
            incarnationEpochRepository.save(epoch);
            em.flush();
        }).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void failOnSameNamePersistence() {

        // ASSERT THAT
       assertThatThrownBy(() ->{
            // GIVEN
            IncarnationEpoch.IncarnationEpochBuilder epochBuilder = IncarnationEpoch.builder();
            IncarnationEpoch epoch = epochBuilder
                    .name("A Long Time Ago...")
                    .cost(2)
                    .description("There was a king")
                    .location("Atlantis")
                    .era(EraEnum.SECRET_COMPACTS)
                    .build();

            IncarnationEpoch epoch2 = epochBuilder
                    .name("A Long Time Ago...")
                    .cost(2)
                    .description("There was a king")
                    .location("Atlantis")
                    .era(EraEnum.SECRET_COMPACTS)
                    .build();
            //WHEN
            incarnationEpochRepository.save(epoch);
            incarnationEpochRepository.save(epoch2);
            em.flush();
        }).isInstanceOf(PersistenceException.class);
    }

    @Test
    public void failOnNegativeCostValidation() {

        // ASSERT THAT
        assertThatThrownBy(() ->{
        // GIVEN
        IncarnationEpoch.IncarnationEpochBuilder epochBuilder = IncarnationEpoch.builder();
        IncarnationEpoch epoch = epochBuilder
                .name("A Long Time Ago...")
                .cost(-1)
                .description("There was a king")
                .location("Atlantis")
                .era(EraEnum.SECRET_COMPACTS)
                .build();

        //WHEN
        incarnationEpochRepository.save(epoch);
        em.flush();
        }).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void failOnCostGreaterThanTwoValidation() {

        // ASSERT THAT
        assertThatThrownBy(() ->{
        // GIVEN
        IncarnationEpoch.IncarnationEpochBuilder epochBuilder = IncarnationEpoch.builder();
        IncarnationEpoch epoch = epochBuilder
                .name("A Long Time Ago...")
                .cost(3)
                .description("There was a king")
                .location("Atlantis")
                .era(EraEnum.SECRET_COMPACTS)
                .build();

        //WHEN
        incarnationEpochRepository.save(epoch);
        em.flush();
        }).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void failOnNullEraValidation() {

        // ASSERT THAT
        assertThatThrownBy(() ->{
            // GIVEN
            IncarnationEpoch.IncarnationEpochBuilder epochBuilder = IncarnationEpoch.builder();
            IncarnationEpoch epoch = epochBuilder
                    .name("A Long Time Ago...")
                    .cost(0)
                    .description("There was a king")
                    .location("Atlantis")
                    .era(null)
                    .build();

            //WHEN
            incarnationEpochRepository.save(epoch);
            em.flush();
        }).isInstanceOf(ConstraintViolationException.class);
    }
}