package org.rorschachdb.nephilim.online.creator.back.repositories;

import org.junit.jupiter.api.Test;
import org.rorschachdb.nephilim.online.creator.back.model.IncarnationEpoch;
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
                .build();
        incarnationEpochRepository.save(epoch);
        IncarnationEpoch epoch2 = epochBuilder
                .name("Nowadays")
                .build();
        incarnationEpochRepository.save(epoch2);
        List<IncarnationEpoch> users = (List<IncarnationEpoch>) incarnationEpochRepository.findAll();

        assertThat(users)
                .isNotNull()
                .isNotEmpty()
                .hasSize(2)
                .extracting("name")
                .containsExactlyInAnyOrder("A long Time Ago ...", "Nowadays");
    }

}