package org.rorschachdb.nephilim.online.creator.back.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.rorschachdb.nephilim.online.creator.back.mappers.IncarnationEpochDegreeValueMapper;
import org.rorschachdb.nephilim.online.creator.back.mappers.IncarnationEpochMapper;
import org.rorschachdb.nephilim.online.creator.back.mappers.ReferenceMapper;
import org.rorschachdb.nephilim.online.creator.back.model.embedded.IncarnationEpochDegreeValueId;
import org.rorschachdb.nephilim.online.creator.back.model.entities.Degree;
import org.rorschachdb.nephilim.online.creator.back.model.entities.IncarnationEpoch;
import org.rorschachdb.nephilim.online.creator.back.model.entities.IncarnationEpochDegreeValue;
import org.rorschachdb.nephilim.online.creator.back.model.representation.IncarnationEpochDegreeValueRepresentation;
import org.rorschachdb.nephilim.online.creator.back.model.representation.IncarnationEpochRepresentation;
import org.rorschachdb.nephilim.online.creator.back.repositories.DegreeRepository;
import org.rorschachdb.nephilim.online.creator.back.repositories.IncarnationEpochDegreeValueRepository;
import org.rorschachdb.nephilim.online.creator.back.repositories.IncarnationEpochRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.Set;

import static org.rorschachdb.nephilim.online.creator.back.controller.ControllerConstants.*;

/**
 * {@link IncarnationEpoch} Controller
 */
@RestController
@RequestMapping(value = INCARNATION_EPOCH_URI, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
public class IncarnationEpochController {

    private static final HttpServerErrorException ID_DEGREE_BAD_REQUEST = HttpServerErrorException
            .create(
                    HttpStatus.BAD_REQUEST,
                    ControllerConstants.H401_ID_DOES_NOT_MATCH,
                    HttpHeaders.EMPTY,
                    ControllerConstants.H401_ID_DOES_NOT_MATCH.getBytes(StandardCharsets.UTF_8),
                    StandardCharsets.UTF_8);
    private static final HttpServerErrorException ID_BAD_REQUEST = HttpServerErrorException
            .create(
                    HttpStatus.BAD_REQUEST,
                    ControllerConstants.H401_ID_DOES_NOT_MATCH_DEGREE,
                    HttpHeaders.EMPTY,
                    ControllerConstants.H401_ID_DOES_NOT_MATCH_DEGREE.getBytes(StandardCharsets.UTF_8),
                    StandardCharsets.UTF_8);
    private static final HttpServerErrorException ID_NOT_FOUND = HttpServerErrorException
            .create(
                    HttpStatus.NOT_FOUND,
                    ControllerConstants.H404_ON_DELETION_ERROR,
                    HttpHeaders.EMPTY,
                    ControllerConstants.H404_ON_DELETION_ERROR.getBytes(StandardCharsets.UTF_8),
                    StandardCharsets.UTF_8);
    private final IncarnationEpochRepository incarnationEpochRepository;
    private final IncarnationEpochDegreeValueRepository incarnationEpochDegreeValueRepository;
    private final DegreeRepository degreeRepository;
    private final IncarnationEpochMapper incarnationEpochMapper;
    private final IncarnationEpochDegreeValueMapper incarnationEpochDegreeValueMapper;
    private final ReferenceMapper referenceMapper;

    /**
     * GET {@link IncarnationEpoch} by its id
     *
     * @param id {@link IncarnationEpoch} id
     * @return 200 with {@link IncarnationEpoch} as body or 404 if not eist
     */
    @GetMapping(ID_URI_PART)
    public ResponseEntity<IncarnationEpochRepresentation> findOne(@PathVariable(PATH_VAR_ID) final Long id) {
        log.info("Fetching Incarnation Epoch by id (" + id + ")");
        return ResponseEntity.of(
                this.incarnationEpochRepository.findById(id).map(this.incarnationEpochMapper::toRepresentation)
        );
    }

    /**
     * GET Incarnation Epochs by Example
     * perform a find all
     *
     * @param representation example of Incarnation Epoch
     * @param pageable       Pagination data
     * @return Page of results
     */
    @GetMapping
    public Page<IncarnationEpochRepresentation> findByExample(final IncarnationEpochRepresentation representation,
                                                              @PageableDefault(Integer.MAX_VALUE) final Pageable pageable) {
        return this.incarnationEpochRepository
                .findAll(Example.of(this.incarnationEpochMapper.toEntity(representation)), pageable)
                .map(this.incarnationEpochMapper::toRepresentation);
    }

    /**
     * POST a new Incarnation Epoch to create
     *
     * @param representation incarnation epoch data
     * @return response entity with uri and body
     */
    @PostMapping
    @Transactional
    public ResponseEntity<IncarnationEpochRepresentation> save(@Valid @RequestBody final IncarnationEpochRepresentation representation) {
        final IncarnationEpoch created = this.incarnationEpochRepository
                .save(this.incarnationEpochMapper.toEntity(representation));
        return ResponseEntity
                .created(URI.create(INCARNATION_EPOCH_URI + "/" + created.getId()))
                .body(this.incarnationEpochMapper.toRepresentation(created));
    }

    /**
     * PUT an existing {@link IncarnationEpoch} to update
     *
     * @param id             unique identifier of {@link IncarnationEpoch}
     * @param representation data for update
     * @return updated representation of data
     * @throws HttpServerErrorException in case the id is incorrect
     */
    @PutMapping(ControllerConstants.ID_URI_PART)
    @Transactional
    public ResponseEntity<IncarnationEpochRepresentation> update(
            @PathVariable final Long id,
            @RequestBody @Valid final IncarnationEpochRepresentation representation)
            throws HttpServerErrorException {
        if (!id.equals(representation.getId())) {
            throw ID_BAD_REQUEST;
        }
        final IncarnationEpoch updated = this.incarnationEpochRepository
                .save(this.incarnationEpochMapper.toEntity(representation));
        return ResponseEntity.ok(this.incarnationEpochMapper.toRepresentation(updated));
    }

    /**
     * DELETE an {@link IncarnationEpoch}
     *
     * @param id unique identifier of {@link IncarnationEpoch}
     * @throws HttpServerErrorException in case the id is incorrect
     */
    @DeleteMapping(ControllerConstants.ID_URI_PART)
    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable final Long id) {
        if (!this.incarnationEpochRepository.existsById(id)) {
            throw ID_NOT_FOUND;
        }
        this.incarnationEpochRepository.deleteById(id);
    }

    //*********************
    //*** DEGREE VALUES ***
    //*********************

    /**
     * GET {@link IncarnationEpoch} by its id
     *
     * @param id {@link IncarnationEpoch} id
     * @return 200 with {@link IncarnationEpoch} as body or 404 if not eist
     */
    @GetMapping(ID_URI_PART + DEGREE_URI_VALUES)
    public ResponseEntity<Set<IncarnationEpochDegreeValueRepresentation>> findAllDegreeValues(@PathVariable(PATH_VAR_ID) final Long id) {
        log.info("Fetching Incarnation Epoch by id (" + id + ")");
        return ResponseEntity.of(
                this.incarnationEpochRepository
                        .findById(id)
                        .map(e -> e.getDegreeValues())
                        .map(this.incarnationEpochDegreeValueMapper::toRepresentations)
        );
    }

    @PostMapping(ID_URI_PART + DEGREE_URI_VALUES)
    @Transactional
    public ResponseEntity<IncarnationEpochDegreeValueRepresentation> createDegreeValue(
            @PathVariable(PATH_VAR_ID) final Long id,
            @RequestBody final IncarnationEpochDegreeValueRepresentation representation) {
        log.info("Testing Incarnation Epoch existence by id (" + id + ")");
        final IncarnationEpoch incarnationEpoch = this.incarnationEpochRepository.findById(id).orElse(null);
        if (incarnationEpoch == null) {
            throw ID_NOT_FOUND;
        }
        final Degree degree = this.referenceMapper.map(representation.getDegreeId(), Degree.class);
        if (degree == null) {
            throw ID_DEGREE_BAD_REQUEST;
        }
        final IncarnationEpochDegreeValue epochDegreeValue = incarnationEpoch.addDegree(degree, representation.getLevel());
        final IncarnationEpochDegreeValue epochDegreeValueSaved = this.incarnationEpochDegreeValueRepository.save(epochDegreeValue);
        return ResponseEntity.created(URI.create(INCARNATION_EPOCH_URI + "/" + id + DEGREE_URI_VALUES + "/" + degree.getId()))
                .body(this.incarnationEpochDegreeValueMapper.toRepresentation(epochDegreeValueSaved));
    }

    @PutMapping(ID_EPOCH_URI_PART + DEGREE_URI_VALUES + ID_DEGREE_URI_PART)
    @Transactional
    public ResponseEntity<IncarnationEpochDegreeValueRepresentation> updateDegreeValue(
            @PathVariable(PATH_VAR_ID_EPOCH) final Long idEpoch,
            @PathVariable(PATH_VAR_ID_DEGREE) final Long idDegree,
            @RequestBody final IncarnationEpochDegreeValueRepresentation representation) {
        log.info("Testing Incarnation Epoch existence by id (" + idEpoch + ")");
        log.info("Testing Degree existence by id (" + idDegree + ")");
        if (!this.incarnationEpochRepository.existsById(idEpoch) || !this.degreeRepository.existsById(idDegree)) {
            throw ID_NOT_FOUND;
        }
        log.info("Testingg relationship existence by id (" + idDegree + ")");
        final IncarnationEpochDegreeValueId incarnationEpochDegreeValueId = IncarnationEpochDegreeValueId.builder().fkIncarnationEpochId(idEpoch).fkDegreeId(idDegree).build();
        final Optional<IncarnationEpochDegreeValue> optIedv = this.incarnationEpochDegreeValueRepository.findById(incarnationEpochDegreeValueId);
        if (optIedv.isEmpty()) {
            throw ID_NOT_FOUND;
        }
        return ResponseEntity.of(optIedv.map(iedv -> {
            iedv.setLevel(representation.getLevel());
            return this.incarnationEpochDegreeValueMapper.toRepresentation(iedv);
        }));
    }

    @DeleteMapping(ID_EPOCH_URI_PART + DEGREE_URI_VALUES + ID_DEGREE_URI_PART)
    @Transactional
    public ResponseEntity<IncarnationEpochDegreeValueRepresentation> deleteDegreeValue(
            @PathVariable(PATH_VAR_ID_EPOCH) final Long idEpoch,
            @PathVariable(PATH_VAR_ID_DEGREE) final Long idDegree,
            @RequestBody final IncarnationEpochDegreeValueRepresentation representation) {
        log.info("Testingg Incarnation Epoch existence by id (" + idEpoch + ")");
        log.info("Testingg Degree existence by id (" + idDegree + ")");
        if (!this.incarnationEpochRepository.existsById(idEpoch) || !this.degreeRepository.existsById(idDegree)) {
            throw ID_NOT_FOUND;
        }
        log.info("Testingg relationship existence by id (" + idDegree + ")");
        final IncarnationEpochDegreeValueId incarnationEpochDegreeValueId = IncarnationEpochDegreeValueId.builder().fkIncarnationEpochId(idEpoch).fkDegreeId(idDegree).build();
        final Optional<IncarnationEpochDegreeValue> degreeValue = this.incarnationEpochDegreeValueRepository.findById(incarnationEpochDegreeValueId);
        if (this.incarnationEpochDegreeValueRepository.existsById(incarnationEpochDegreeValueId)) {
            throw ID_NOT_FOUND;
        }
        this.incarnationEpochDegreeValueRepository.deleteById(incarnationEpochDegreeValueId);
        return ResponseEntity.ok().build();
    }
}
