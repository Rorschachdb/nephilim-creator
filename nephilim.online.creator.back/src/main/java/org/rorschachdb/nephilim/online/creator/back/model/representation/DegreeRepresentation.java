package org.rorschachdb.nephilim.online.creator.back.model.representation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.rorschachdb.nephilim.online.creator.back.model.enums.DegreeTypeEnum;

/**
 * A character's skill
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DegreeRepresentation {

    /**
     * Technical identifier (autogenerated)
     */
    private Long id;

    /**
     * Degree's designation - conceptual key
     */
    @NotBlank
    private String name;

    /**
     * Degree's definition
     */
    private String description;

    /**
     * Category of degrees which the Degree is part of
     */
    @NotNull
    private DegreeTypeEnum type;
}
