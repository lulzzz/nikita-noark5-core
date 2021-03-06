package nikita.webapp.web.controller.hateoas.metadata;

import com.codahale.metrics.annotation.Counted;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nikita.common.config.Constants;
import nikita.common.model.noark5.v4.hateoas.metadata.MetadataHateoas;
import nikita.common.model.noark5.v4.metadata.ScreeningDocument;
import nikita.common.util.CommonUtils;
import nikita.common.util.exceptions.NikitaException;
import nikita.webapp.service.interfaces.metadata.IScreeningDocumentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static nikita.common.config.Constants.*;
import static nikita.common.config.N5ResourceMappings.*;
import static org.springframework.http.HttpHeaders.ETAG;

/**
 * Created by tsodring on 16/03/18.
 */

@RestController
@RequestMapping(
        value = Constants.HATEOAS_API_PATH + SLASH + NOARK_METADATA_PATH + SLASH,
        produces = {NOARK5_V4_CONTENT_TYPE_JSON, NOARK5_V4_CONTENT_TYPE_JSON_XML})
@SuppressWarnings("unchecked")
public class ScreeningDocumentController {

    private IScreeningDocumentService postalCodeService;

    public ScreeningDocumentController(IScreeningDocumentService postalCodeService) {
        this.postalCodeService = postalCodeService;
    }

    // API - All POST Requests (CRUD - CREATE)
    // Creates a new skjermingdokument
    // POST [contextPath][api]/metadata/skjermingdokument/ny-skjermingdokument
    @ApiOperation(
            value = "Persists a new ScreeningDocument object",
            notes = "Returns the newly created ScreeningDocument object after it " +
                    "is persisted to the database",
            response = ScreeningDocument.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "ScreeningDocument " +
                            API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = ScreeningDocument.class),
            @ApiResponse(
                    code = 201,
                    message = "ScreeningDocument " +
                            API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = ScreeningDocument.class),
            @ApiResponse(code = 401,
                    message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(
                    code = 403,
                    message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(
                    code = 404,
                    message = API_MESSAGE_MALFORMED_PAYLOAD),
            @ApiResponse(
                    code = 409,
                    message = API_MESSAGE_CONFLICT),
            @ApiResponse(
                    code = 500,
                    message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted

    @RequestMapping(
            method = RequestMethod.POST,
            value = SCREENING_DOCUMENT + SLASH + NEW_SCREENING_DOCUMENT
    )
    public ResponseEntity<MetadataHateoas> createScreeningDocument(
            HttpServletRequest request,
            @RequestBody ScreeningDocument postalCode)
            throws NikitaException {

        MetadataHateoas metadataHateoas =
                postalCodeService.createNewScreeningDocument(postalCode);

        return ResponseEntity.status(HttpStatus.CREATED)
                .allow(CommonUtils.WebUtils.
                        getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(metadataHateoas.getEntityVersion().toString())
                .body(metadataHateoas);
    }

    // API - All GET Requests (CRUD - READ)
    // Retrieves all postalCode
    // GET [contextPath][api]/metadata/skjermingdokument/
    @ApiOperation(
            value = "Retrieves all ScreeningDocument ",
            response = ScreeningDocument.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "ScreeningDocument codes found",
                    response = ScreeningDocument.class),
            @ApiResponse(
                    code = 404,
                    message = "No ScreeningDocument found"),
            @ApiResponse(
                    code = 401,
                    message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(
                    code = 403,
                    message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(
                    code = 500,
                    message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted

    @RequestMapping(
            method = RequestMethod.GET,
            value = SCREENING_DOCUMENT
    )
    public ResponseEntity<MetadataHateoas> findAll(HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.
                        getMethodsForRequestOrThrow(request.getServletPath()))
                .body(postalCodeService.findAll());
    }

    // Retrieves a given postalCode identified by a systemId
    // GET [contextPath][api]/metadata/skjermingdokument/{systemId}/
    @ApiOperation(
            value = "Gets postalCode identified by its systemId",
            notes = "Returns the requested postalCode object",
            response = ScreeningDocument.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "ScreeningDocument " +
                            API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = ScreeningDocument.class),
            @ApiResponse(
                    code = 201,
                    message = "ScreeningDocument " +
                            API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = ScreeningDocument.class),
            @ApiResponse(
                    code = 401,
                    message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(
                    code = 403,
                    message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(
                    code = 404,
                    message = API_MESSAGE_MALFORMED_PAYLOAD),
            @ApiResponse(
                    code = 409,
                    message = API_MESSAGE_CONFLICT),
            @ApiResponse(
                    code = 500,
                    message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted

    @RequestMapping(
            value = SCREENING_DOCUMENT + SLASH + LEFT_PARENTHESIS + SYSTEM_ID +
                    RIGHT_PARENTHESIS + SLASH,
            method = RequestMethod.GET
    )
    public ResponseEntity<MetadataHateoas> findBySystemId(
            @PathVariable("systemID") final String systemId,
            HttpServletRequest request) {

        MetadataHateoas metadataHateoas = postalCodeService.find(systemId);

        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.
                        getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(metadataHateoas.getEntityVersion().toString())
                .body(metadataHateoas);
    }

    // Create a suggested postalCode(like a template) with default values
    // (nothing persisted)
    // GET [contextPath][api]/metadata/ny-skjermingdokument
    @ApiOperation(
            value = "Creates a suggested ScreeningDocument",
            response = ScreeningDocument.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "ScreeningDocument codes found",
                    response = ScreeningDocument.class),
            @ApiResponse(
                    code = 404,
                    message = "No ScreeningDocument found"),
            @ApiResponse(
                    code = 401,
                    message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(
                    code = 403,
                    message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(
                    code = 500,
                    message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted

    @RequestMapping(
            method = RequestMethod.GET,
            value = NEW_SCREENING_DOCUMENT
    )
    public ResponseEntity<MetadataHateoas>
    generateDefaultScreeningDocument(HttpServletRequest request) {

        MetadataHateoas metadataHateoas = new MetadataHateoas
                (postalCodeService.generateDefaultScreeningDocument());

        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.
                        getMethodsForRequestOrThrow(request.getServletPath()))
                .body(metadataHateoas);
    }

    // API - All PUT Requests (CRUD - UPDATE)
    // Update a skjermingdokument
    // PUT [contextPath][api]/metatdata/skjermingdokument/
    @ApiOperation(
            value = "Updates a ScreeningDocument object",
            notes = "Returns the newly updated ScreeningDocument object after it " +
                    "is persisted to the database",
            response = ScreeningDocument.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "ScreeningDocument " +
                            API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = ScreeningDocument.class),
            @ApiResponse(
                    code = 401,
                    message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(
                    code = 403,
                    message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(
                    code = 404,
                    message = API_MESSAGE_MALFORMED_PAYLOAD),
            @ApiResponse(
                    code = 409,
                    message = API_MESSAGE_CONFLICT),
            @ApiResponse(
                    code = 500,
                    message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted

    @RequestMapping(
            method = RequestMethod.PUT,
            value = SCREENING_DOCUMENT + SLASH + SCREENING_DOCUMENT
    )
    public ResponseEntity<MetadataHateoas> updateScreeningDocument(
            @ApiParam(name = "systemID",
                    value = "systemId of ScreeningDocument to update.",
                    required = true)
            @PathVariable("systemID") String systemID,
            @RequestBody ScreeningDocument postalCode,
            HttpServletRequest request) {

        MetadataHateoas metadataHateoas = postalCodeService.handleUpdate
                (systemID,
                        CommonUtils.Validation.parseETAG(
                                request.getHeader(ETAG)),
                        postalCode);

        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.
                        getMethodsForRequestOrThrow(request.getServletPath()))
                .body(metadataHateoas);
    }
}
