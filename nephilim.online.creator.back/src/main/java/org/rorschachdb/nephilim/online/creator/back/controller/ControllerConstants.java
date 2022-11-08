package org.rorschachdb.nephilim.online.creator.back.controller;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Constants for controllers : URIs, URI Parts, Messages, ...
 **/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
class ControllerConstants {
    // Path variable Names
    static final String PATH_VAR_ID = "id";
    // Resource URIs
    static final String INCARNATION_EPOCH_URI = "/incarnationepoch";
    static final String DEGREE_URI = "/degree";
    // URI parts
    static final String ID_URI_PART = "/{id}";

    // Messages
    /**
     * HTTP 401 Resource does not match identifier
     */
    static final String H401_ID_DOES_NOT_MATCH = "401 : Id does not match identifier";
    /**
     * HTTP 401 Validation exception upon creation
     */
    static final String H401_CREATION_VALIDATION_ERROR = "401 : submitted data does not match validation rules";
    static final String H404_ON_DELETION_ERROR = "404 : resource to delete not found";
}
