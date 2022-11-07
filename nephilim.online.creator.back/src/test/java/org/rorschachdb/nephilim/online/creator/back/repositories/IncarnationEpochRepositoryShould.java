package org.rorschachdb.nephilim.online.creator.back.repositories;

import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;
import org.rorschachdb.nephilim.online.creator.back.model.IncarnationEpoch;
import org.rorschachdb.nephilim.online.creator.back.model.enums.EraEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class IncarnationEpochRepositoryShould {
    @Autowired
    private IncarnationEpochRepository incarnationEpochRepository;

    @Test
    public void createAndRetrieveEpochs() {
        IncarnationEpoch.IncarnationEpochBuilder epochBuilder = IncarnationEpoch.builder();
        IncarnationEpoch epoch = epochBuilder
                .name("A long Time Ago ...")
                .cost(2)
                .description("There was a king")
                .era(EraEnum.SECRET_COMPACTS)
                .build();
        incarnationEpochRepository.save(epoch);
        epochBuilder = IncarnationEpoch.builder();
        IncarnationEpoch epoch2 = epochBuilder
                .name("Nowadays")
                .cost(1)
                .era(EraEnum.GREAT_AWAKENING)
                .description("Pre-apo")
                .build();
        incarnationEpochRepository.save(epoch2);
        List<IncarnationEpoch> epochs = (List<IncarnationEpoch>) incarnationEpochRepository.findAll();

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

}