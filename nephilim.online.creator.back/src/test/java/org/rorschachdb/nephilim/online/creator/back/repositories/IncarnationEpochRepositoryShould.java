package org.rorschachdb.nephilim.online.creator.back.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;
import org.rorschachdb.nephilim.online.creator.back.model.embedded.MagicEffect;
import org.rorschachdb.nephilim.online.creator.back.model.entities.Degree;
import org.rorschachdb.nephilim.online.creator.back.model.entities.IncarnationEpoch;
import org.rorschachdb.nephilim.online.creator.back.model.entities.IncarnationEpochDegreeValue;
import org.rorschachdb.nephilim.online.creator.back.model.enums.EraEnum;
import org.rorschachdb.nephilim.online.creator.back.model.enums.OccultScienceTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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
    void createAndRetrieveEpochs() {
        // GIVEN
        IncarnationEpoch.IncarnationEpochBuilder epochBuilder = IncarnationEpoch.builder();
        final IncarnationEpoch epoch = epochBuilder
                .name("A long Time Ago ...")
                .cost(2)
                .description("There was a king")
                .location("Here")
                .era(EraEnum.LOST_ERA)
                .magicEffect(MagicEffect.builder().occultScience(OccultScienceTypeEnum.MAGIC).quantity(2).build())
                .build();
        epochBuilder = IncarnationEpoch.builder();
        final IncarnationEpoch epoch2 = epochBuilder
                .name("Nowadays")
                .cost(0)
                .location("There")
                .era(EraEnum.GREAT_AWAKENING)
                .description("Pre-apo")
                .magicEffect(MagicEffect.builder().occultScience(OccultScienceTypeEnum.MAGIC).quantity(2).build())
                .build();
        // WHEN
        this.incarnationEpochRepository.save(epoch);
        this.incarnationEpochRepository.save(epoch2);
        final List<IncarnationEpoch> epochs = (List<IncarnationEpoch>) this.incarnationEpochRepository.findAll();

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
    void createAndRetrieveEpochsWithLocations() {
        // GIVEN
        IncarnationEpoch.IncarnationEpochBuilder epochBuilder = IncarnationEpoch.builder();
        final IncarnationEpoch epoch = epochBuilder
                .name("A long Time Ago ...")
                .cost(2)
                .location("Nowhere")
                .era(EraEnum.LOST_ERA)
                .magicEffect(MagicEffect.builder().occultScience(OccultScienceTypeEnum.MAGIC).quantity(2).build())
                .build();
        epochBuilder = IncarnationEpoch.builder();
        final IncarnationEpoch epoch2 = epochBuilder
                .name("Nowadays")
                .cost(1)
                .location("Here")
                .location("There")
                .era(EraEnum.GREAT_AWAKENING)
                .magicEffect(MagicEffect.builder().occultScience(OccultScienceTypeEnum.MAGIC).quantity(2).build())
                .build();
        this.incarnationEpochRepository.save(epoch);
        this.incarnationEpochRepository.save(epoch2);

        final List<IncarnationEpoch> epochs = (List<IncarnationEpoch>) this.incarnationEpochRepository.findAll();

        assertThat(epochs)
                .isNotNull()
                .isNotEmpty()
                .hasSize(4)
                .flatExtracting("locations")
                .contains("Nowhere", "Here", "There");
    }

    @Test
    void failOnLocationValidation() {
        // GIVEN
        final IncarnationEpoch.IncarnationEpochBuilder epochBuilder = IncarnationEpoch.builder();
        final IncarnationEpoch epoch = epochBuilder
                .name("A long Time Ago ...")
                .cost(2)
                .description("There was a king")
                .era(EraEnum.SECRET_COMPACTS)
                .magicEffect(MagicEffect.builder().occultScience(OccultScienceTypeEnum.MAGIC).quantity(2).build())
                .build();

        final ConstraintViolationException constraintViolationException = catchThrowableOfType(() -> {
            //WHEN
            this.incarnationEpochRepository.save(epoch);
            this.em.flush();
        }, ConstraintViolationException.class);

        // THEN
        final Set<ConstraintViolation<?>> violations = constraintViolationException.getConstraintViolations();
        assertThat(violations).isNotEmpty().hasSize(1);
        assertThat(violations).extracting("messageTemplate")
                .contains("{jakarta.validation.constraints.NotEmpty.message}");


    }

    @Test
    void failOnBLankNameValidation() {

        // ASSERT THAT
        assertThatThrownBy(() -> {
            // GIVEN
            final IncarnationEpoch.IncarnationEpochBuilder epochBuilder = IncarnationEpoch.builder();
            final IncarnationEpoch epoch = epochBuilder
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
    void failOnSameNamePersistence() {

        // ASSERT THAT
        assertThatThrownBy(() -> {
            // GIVEN
            final IncarnationEpoch.IncarnationEpochBuilder epochBuilder = IncarnationEpoch.builder();
            final IncarnationEpoch epoch = epochBuilder
                    .name("A Long Time Ago...")
                    .cost(2)
                    .description("There was a king")
                    .location("Nowhere")
                    .era(EraEnum.LOST_ERA)
                    .magicEffect(MagicEffect.builder().occultScience(OccultScienceTypeEnum.MAGIC).quantity(2).build())
                    .build();

            final IncarnationEpoch epoch2 = epochBuilder
                    .name("A Long Time Ago...")
                    .cost(0)
                    .description("Look an eagle!")
                    .location("Up here")
                    .era(EraEnum.ELEMENTARY_WARS)
                    .magicEffect(MagicEffect.builder().occultScience(OccultScienceTypeEnum.MAGIC).quantity(2).build())
                    .build();
            //WHEN
            this.incarnationEpochRepository.save(epoch);
            this.incarnationEpochRepository.save(epoch2);
            this.em.flush();
        }).isInstanceOf(PersistenceException.class);
    }

    @Test
    void failOnNegativeCostValidation() {

        // ASSERT THAT
        assertThatThrownBy(() -> {
            // GIVEN
            final IncarnationEpoch.IncarnationEpochBuilder epochBuilder = IncarnationEpoch.builder();
            final IncarnationEpoch epoch = epochBuilder
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
    void failOnCostGreaterThanTwoValidation() {

        // ASSERT THAT
        assertThatThrownBy(() -> {
            // GIVEN
            final IncarnationEpoch.IncarnationEpochBuilder epochBuilder = IncarnationEpoch.builder();
            final IncarnationEpoch epoch = epochBuilder
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
    void failOnNullEraValidation() {

        // ASSERT THAT
        assertThatThrownBy(() -> {
            // GIVEN
            final IncarnationEpoch.IncarnationEpochBuilder epochBuilder = IncarnationEpoch.builder();
            final IncarnationEpoch epoch = epochBuilder
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

    @Test
    void createCascadingDegreeValues() {
        // GIVEN
        final Degree alchimie = this.em.createQuery("select d from Degree d where d.id = 1203", Degree.class).getSingleResult();
//        final IncarnationEpoch atlantis = this.em.createQuery("select i from IncarnationEpoch i where i.id = 1", IncarnationEpoch.class).getSingleResult();
        final IncarnationEpoch.IncarnationEpochBuilder epochBuilder = IncarnationEpoch.builder();
        final IncarnationEpoch epoch = epochBuilder
                .name("A Long Time Ago...")
                .cost(0)
                .description("There was a king")
                .location("Nowhere")
                .era(EraEnum.LOST_ERA)
                .build();

        // WHEN
        final IncarnationEpochDegreeValue dv = epoch.addDegree(alchimie, 3);

        final IncarnationEpoch created = this.incarnationEpochRepository.save(epoch);
        final IncarnationEpoch fromDb = this.incarnationEpochRepository.findById(created.getId()).orElse(null);
        // THEN
        assertThat(fromDb).isNotNull();
        assertThat(fromDb.getDegreeValues()).hasSize(1).extracting("degree.name").containsExactly("Alchimie");
    }


}