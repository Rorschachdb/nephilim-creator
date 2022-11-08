package org.rorschachdb.nephilim.online.creator.back.repositories;

import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;
import org.rorschachdb.nephilim.online.creator.back.model.entities.IncarnationEpoch;
import org.rorschachdb.nephilim.online.creator.back.model.enums.EraEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

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
                .location("Here")
                .era(EraEnum.LOST_ERA)
                .build();
        epochBuilder = IncarnationEpoch.builder();
        IncarnationEpoch epoch2 = epochBuilder
                .name("Nowadays")
                .cost(0)
                .location("There")
                .era(EraEnum.GREAT_AWAKENING)
                .description("Pre-apo")
                .build();
        // WHEN
        this.incarnationEpochRepository.save(epoch);
        this.incarnationEpochRepository.save(epoch2);
        List<IncarnationEpoch> epochs = (List<IncarnationEpoch>) this.incarnationEpochRepository.findAll();

        // THEN
        assertThat(epochs)
                .isNotNull()
                .isNotEmpty()
                .hasSize(4)
                .extracting("name", "cost", "description", "era")
                .contains(new Tuple("A long Time Ago ...", 2, "There was a king", EraEnum.LOST_ERA),
                        new Tuple("Nowadays", 0, "Pre-apo", EraEnum.GREAT_AWAKENING));
    }

    @Test
    public void createAndRetrieveEpochsWithLocations() {
        // GIVEN
        IncarnationEpoch.IncarnationEpochBuilder epochBuilder = IncarnationEpoch.builder();
        IncarnationEpoch epoch = epochBuilder
                .name("A long Time Ago ...")
                .cost(2)
                .location("Nowhere")
                .era(EraEnum.LOST_ERA)
                .build();
        epochBuilder = IncarnationEpoch.builder();
        IncarnationEpoch epoch2 = epochBuilder
                .name("Nowadays")
                .cost(1)
                .location("Here")
                .location("There")
                .era(EraEnum.GREAT_AWAKENING)
                .build();
        this.incarnationEpochRepository.save(epoch);
        this.incarnationEpochRepository.save(epoch2);

        List<IncarnationEpoch> epochs = (List<IncarnationEpoch>) this.incarnationEpochRepository.findAll();

        assertThat(epochs)
                .isNotNull()
                .isNotEmpty()
                .hasSize(4)
                .flatExtracting("locations")
                .contains("Nowhere", "Here", "There");
    }

    @Test
    public void failOnLocationValidation() {
        // GIVEN
        IncarnationEpoch.IncarnationEpochBuilder epochBuilder = IncarnationEpoch.builder();
        IncarnationEpoch epoch = epochBuilder
                .name("A long Time Ago ...")
                .cost(2)
                .description("There was a king")
                .era(EraEnum.SECRET_COMPACTS)
                .build();

        ConstraintViolationException constraintViolationException = catchThrowableOfType(() -> {
            //WHEN
            this.incarnationEpochRepository.save(epoch);
            this.em.flush();
        }, ConstraintViolationException.class);

        // THEN
        Set<ConstraintViolation<?>> violations = constraintViolationException.getConstraintViolations();
        assertThat(violations).isNotEmpty().hasSize(1);
        assertThat(violations).extracting("messageTemplate")
                .contains("{javax.validation.constraints.NotEmpty.message}");


    }

    @Test
    public void failOnBLankNameValidation() {

        // ASSERT THAT
        assertThatThrownBy(() -> {
            // GIVEN
            IncarnationEpoch.IncarnationEpochBuilder epochBuilder = IncarnationEpoch.builder();
            IncarnationEpoch epoch = epochBuilder
                    .name("")
                    .cost(2)
                    .description("There was a king")
                    .location("Nowhere")
                    .era(EraEnum.LOST_ERA)
                    .build();

            //WHEN
            this.incarnationEpochRepository.save(epoch);
            this.em.flush();
        }).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void failOnSameNamePersistence() {

        // ASSERT THAT
        assertThatThrownBy(() -> {
            // GIVEN
            IncarnationEpoch.IncarnationEpochBuilder epochBuilder = IncarnationEpoch.builder();
            IncarnationEpoch epoch = epochBuilder
                    .name("A Long Time Ago...")
                    .cost(2)
                    .description("There was a king")
                    .location("Nowhere")
                    .era(EraEnum.LOST_ERA)
                    .build();

            IncarnationEpoch epoch2 = epochBuilder
                    .name("A Long Time Ago...")
                    .cost(0)
                    .description("Look an eagle!")
                    .location("Up here")
                    .era(EraEnum.ELEMENTARY_WARS)
                    .build();
            //WHEN
            this.incarnationEpochRepository.save(epoch);
            this.incarnationEpochRepository.save(epoch2);
            this.em.flush();
        }).isInstanceOf(PersistenceException.class);
    }

    @Test
    public void failOnNegativeCostValidation() {

        // ASSERT THAT
        assertThatThrownBy(() -> {
            // GIVEN
            IncarnationEpoch.IncarnationEpochBuilder epochBuilder = IncarnationEpoch.builder();
            IncarnationEpoch epoch = epochBuilder
                    .name("A Long Time Ago...")
                    .cost(-1)
                    .description("There was a king")
                    .location("Nowhere")
                    .era(EraEnum.LOST_ERA)
                    .build();

            //WHEN
            this.incarnationEpochRepository.save(epoch);
            this.em.flush();
        }).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void failOnCostGreaterThanTwoValidation() {

        // ASSERT THAT
        assertThatThrownBy(() -> {
            // GIVEN
            IncarnationEpoch.IncarnationEpochBuilder epochBuilder = IncarnationEpoch.builder();
            IncarnationEpoch epoch = epochBuilder
                    .name("A Long Time Ago...")
                    .cost(3)
                    .description("There was a king")
                    .location("Nowhere")
                    .era(EraEnum.LOST_ERA)
                    .build();

            //WHEN
            this.incarnationEpochRepository.save(epoch);
            this.em.flush();
        }).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void failOnNullEraValidation() {

        // ASSERT THAT
        assertThatThrownBy(() -> {
            // GIVEN
            IncarnationEpoch.IncarnationEpochBuilder epochBuilder = IncarnationEpoch.builder();
            IncarnationEpoch epoch = epochBuilder
                    .name("A Long Time Ago...")
                    .cost(0)
                    .description("There was a king")
                    .location("Nowhere")
                    .era(null)
                    .build();

            //WHEN
            this.incarnationEpochRepository.save(epoch);
            this.em.flush();
        }).isInstanceOf(ConstraintViolationException.class);
    }
}