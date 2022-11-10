package org.rorschachdb.nephilim.online.creator.back.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.rorschachdb.nephilim.online.creator.back.mappers.DegreeMapper;
import org.rorschachdb.nephilim.online.creator.back.model.entities.Degree;
import org.rorschachdb.nephilim.online.creator.back.model.entities.IncarnationEpoch;
import org.rorschachdb.nephilim.online.creator.back.model.representation.DegreeRepresentation;
import org.rorschachdb.nephilim.online.creator.back.model.representation.IncarnationEpochRepresentation;
import org.rorschachdb.nephilim.online.creator.back.repositories.DegreeRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import javax.validation.Valid;
import java.net.URI;
import java.nio.charset.StandardCharsets;

import static org.rorschachdb.nephilim.online.creator.back.controller.ControllerConstants.*;

@RestController
@RequestMapping(DEGREE_URI)
@RequiredArgsConstructor
@Slf4j
public class DegreeController {

    private static final HttpServerErrorException ID_BAD_REQUEST = HttpServerErrorException
            .create(
                    HttpStatus.BAD_REQUEST,
                    ControllerConstants.H401_ID_DOES_NOT_MATCH,
                    HttpHeaders.EMPTY,
                    ControllerConstants.H401_ID_DOES_NOT_MATCH.getBytes(StandardCharsets.UTF_8),
                    StandardCharsets.UTF_8);
    private static final HttpServerErrorException ID_NOT_FOUND = HttpServerErrorException
            .create(
                    HttpStatus.NOT_FOUND,
                    ControllerConstants.H404_ON_DELETION_ERROR,
                    HttpHeaders.EMPTY,
                    ControllerConstants.H404_ON_DELETION_ERROR.getBytes(StandardCharsets.UTF_8),
                    StandardCharsets.UTF_8);

    private final DegreeRepository degreeRepository;

    private final DegreeMapper degreeMapper;

    /**
     * GET {@link Degree} by its id
     *
     * @param id {@link Degree} id
     * @return 200 with {@link Degree} as body or 404 if not eist
     */
    @GetMapping(ID_URI_PART)
    public ResponseEntity<DegreeRepresentation> findOne(@PathVariable(PATH_VAR_ID) Long id) {
        log.info("Fetching Degree by id (" + id + ")");
        return ResponseEntity.of(
                this.degreeRepository.findById(id).map(this.degreeMapper::toRepresentation)
        );
    }

    /**
     * GET Degree by Example
     * perform a find all
     *
     * @param representation example of Degree
     * @param pageable       Pagination data
     * @return Page of results
     */
    @GetMapping
    public Page<DegreeRepresentation> findByExample(DegreeRepresentation representation,
                                                              @PageableDefault(Integer.MAX_VALUE) Pageable pageable) {
        return this.degreeRepository
                .findAll(Example.of(this.degreeMapper.toEntity(representation)), pageable)
                .map(this.degreeMapper::toRepresentation);
    }

    /**
     * POST a new Degree to create
     *
     * @param representation degree data
     * @return response entity with uri and body
     */
    @PostMapping
    @Transactional
    public ResponseEntity<DegreeRepresentation> save(@Valid @RequestBody DegreeRepresentation representation) {
        Degree created = this.degreeRepository
                .save(this.degreeMapper.toEntity(representation));
        return ResponseEntity
                .created(URI.create(DEGREE_URI + "/" + created.getId()))
                .body(this.degreeMapper.toRepresentation(created));
    }

    /**
     * PUT an existing {@link Degree} to update
     *
     * @param id             unique identifier of {@link Degree}
     * @param representation data for update
     * @return updated representation of data
     * @throws HttpServerErrorException in case the id is incorrect
     */
    @PutMapping(ControllerConstants.ID_URI_PART)
    @Transactional
    public ResponseEntity<DegreeRepresentation> update(
            @PathVariable Long id,
            @RequestBody @Valid DegreeRepresentation representation)
            throws HttpServerErrorException {
        if (!id.equals(representation.getId())) {
            throw ID_BAD_REQUEST;
        }
        Degree updated = this.degreeRepository
                .save(this.degreeMapper.toEntity(representation));
        return ResponseEntity.ok(this.degreeMapper.toRepresentation(updated));
    }

    /**
     * DELETE an {@link Degree}
     * @param id  unique identifier of {@link Degree}
     * @throws HttpServerErrorException in case the id is incorrect
     */
    @DeleteMapping(ControllerConstants.ID_URI_PART)
    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (!this.degreeRepository.existsById(id)) {
            throw ID_NOT_FOUND;
        }
        this.degreeRepository.deleteById(id);
    }

}
