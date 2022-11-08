package org.rorschachdb.nephilim.online.creator.back.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.rorschachdb.nephilim.online.creator.back.mappers.IncarnationEpochMapper;
import org.rorschachdb.nephilim.online.creator.back.model.entities.IncarnationEpoch;
import org.rorschachdb.nephilim.online.creator.back.model.representation.IncarnationEpochRepresentation;
import org.rorschachdb.nephilim.online.creator.back.repositories.IncarnationEpochRepository;
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

/**
 * {@link IncarnationEpoch} Controller
 */
@RestController
@RequestMapping(INCARNATION_EPOCH_URI)
@RequiredArgsConstructor
@Slf4j
public class IncarnationEpochController {

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
    private final IncarnationEpochRepository incarnationEpochRepository;
    private final IncarnationEpochMapper incarnationEpochMapper;

    /**
     * GET {@link IncarnationEpoch} by its id
     *
     * @param id {@link IncarnationEpoch} id
     * @return 200 with {@link IncarnationEpoch} as body or 404 if not eist
     */
    @GetMapping(ID_URI_PART)
    public ResponseEntity<IncarnationEpochRepresentation> findOne(@PathVariable(PATH_VAR_ID) Long id) {
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
    public Page<IncarnationEpochRepresentation> findByExample(IncarnationEpochRepresentation representation,
                                                              @PageableDefault(Integer.MAX_VALUE) Pageable pageable) {
        return this.incarnationEpochRepository
                .findAll(Example.of(this.incarnationEpochMapper.toEntity(representation)), pageable)
                .map(this.incarnationEpochMapper::toRepresentation);
    }

    /**
     * POST a new Incarnation epoch to create
     *
     * @param representation incarnation epoch data
     * @return response entity with uri and body
     */
    @PostMapping
    @Transactional
    public ResponseEntity<IncarnationEpochRepresentation> save(@Valid @RequestBody IncarnationEpochRepresentation representation) {
        IncarnationEpoch created = this.incarnationEpochRepository
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
            @PathVariable Long id,
            @RequestBody @Valid IncarnationEpochRepresentation representation)
            throws HttpServerErrorException {
        if (!id.equals(representation.getId())) {
            throw ID_BAD_REQUEST;
        }
        IncarnationEpoch updated = this.incarnationEpochRepository
                .save(this.incarnationEpochMapper.toEntity(representation));
        return ResponseEntity.ok(this.incarnationEpochMapper.toRepresentation(updated));
    }

    @DeleteMapping(ControllerConstants.ID_URI_PART)
    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (!this.incarnationEpochRepository.existsById(id)) {
            throw ID_NOT_FOUND;
        }
        this.incarnationEpochRepository.deleteById(id);
    }


}
