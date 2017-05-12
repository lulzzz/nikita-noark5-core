package no.arkivlab.hioa.nikita.webapp.web.controller.hateoas.sakarkiv;

import com.codahale.metrics.annotation.Counted;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nikita.config.Constants;
import nikita.model.noark5.v4.DocumentFlow;
import nikita.model.noark5.v4.DocumentObject;
import nikita.model.noark5.v4.RegistryEntry;
import nikita.model.noark5.v4.SignOff;
import nikita.model.noark5.v4.hateoas.DocumentObjectHateoas;
import nikita.model.noark5.v4.hateoas.RegistryEntryHateoas;
import nikita.model.noark5.v4.hateoas.secondary.CorrespondencePartHateoas;
import nikita.model.noark5.v4.hateoas.secondary.PrecedenceHateoas;
import nikita.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.model.noark5.v4.metadata.CorrespondencePartType;
import nikita.model.noark5.v4.secondary.CorrespondencePart;
import nikita.model.noark5.v4.secondary.Precedence;
import nikita.util.CommonUtils;
import nikita.util.exceptions.NikitaException;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.IRegistryEntryHateoasHandler;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.secondary.ICorrespondencePartHateoasHandler;
import no.arkivlab.hioa.nikita.webapp.security.Authorisation;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.IRegistryEntryService;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.metadata.ICorrespondencePartTypeService;
import no.arkivlab.hioa.nikita.webapp.web.controller.hateoas.NoarkController;
import no.arkivlab.hioa.nikita.webapp.web.events.AfterNoarkEntityCreatedEvent;
import no.arkivlab.hioa.nikita.webapp.web.events.AfterNoarkEntityDeletedEvent;
import no.arkivlab.hioa.nikita.webapp.web.events.AfterNoarkEntityUpdatedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static nikita.config.Constants.*;
import static nikita.config.MetadataConstants.CORRESPONDENCE_PART_CODE_EA;
import static nikita.config.N5ResourceMappings.*;
import static org.springframework.http.HttpHeaders.ETAG;

@RestController
@RequestMapping(value = Constants.HATEOAS_API_PATH + SLASH + NOARK_CASE_HANDLING_PATH + SLASH + REGISTRY_ENTRY,
        produces = {NOARK5_V4_CONTENT_TYPE_JSON, NOARK5_V4_CONTENT_TYPE_JSON_XML})
public class RegistryEntryHateoasController extends NoarkController {

    private IRegistryEntryService registryEntryService;
    private IRegistryEntryHateoasHandler registryEntryHateoasHandler;
    private ApplicationEventPublisher applicationEventPublisher;
    private ICorrespondencePartHateoasHandler correspondencePartHateoasHandler;
    ICorrespondencePartTypeService correspondencePartTypeService;

    public RegistryEntryHateoasController(IRegistryEntryService registryEntryService,
                                          IRegistryEntryHateoasHandler registryEntryHateoasHandler,
                                          ApplicationEventPublisher applicationEventPublisher,
                                          ICorrespondencePartHateoasHandler correspondencePartHateoasHandler,
                                          ICorrespondencePartTypeService correspondencePartTypeService) {
        this.registryEntryService = registryEntryService;
        this.registryEntryHateoasHandler = registryEntryHateoasHandler;
        this.applicationEventPublisher = applicationEventPublisher;
        this.correspondencePartHateoasHandler = correspondencePartHateoasHandler;
        this.correspondencePartTypeService = correspondencePartTypeService;
    }

    // API - All POST Requests (CRUD - CREATE)

    // Create a new CorrespondencePart and associate it with the given journalpost
    // POST [contextPath][api]/sakarkiv/journalpost/{systemId}/ny-presedens
    // http://rel.kxml.no/noark5/v4/api/sakarkiv/ny-presedens/
    @ApiOperation(value = "Persists a CorrespondencePart object associated with the given Record systemId",
            notes = "Returns the newly created CorrespondencePart object after it was associated with a " +
                    "Record object and persisted to the database", response = CorrespondencePartHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "CorrespondencePart " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = CorrespondencePartHateoas.class),
            @ApiResponse(code = 201, message = "CorrespondencePart " + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = CorrespondencePartHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_PARENT_DOES_NOT_EXIST + " of type CorrespondencePart"),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.POST, value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS +
            SLASH + NEW_CORRESPONDENCE_PART, consumes = {NOARK5_V4_CONTENT_TYPE_JSON})
    public ResponseEntity<CorrespondencePartHateoas> createCorrespondencePartAssociatedWithRecord(
            HttpServletRequest request,
            @ApiParam(name = "systemID",
                    value = "systemId of record to associate the CorrespondencePart with.",
                    required = true)
            @PathVariable("systemID") String systemID,
            @ApiParam(name = "CorrespondencePart",
                    value = "Incoming CorrespondencePart object",
                    required = true)
            @RequestBody CorrespondencePart CorrespondencePart)
            throws NikitaException {

        CorrespondencePart createdCorrespondencePart =
                registryEntryService.createCorrespondencePartAssociatedWithRegistryEntry(systemID, CorrespondencePart);
        CorrespondencePartHateoas correspondencePartHateoas =
                new CorrespondencePartHateoas(createdCorrespondencePart);
        correspondencePartHateoasHandler.addLinks(correspondencePartHateoas, request, new Authorisation());
        applicationEventPublisher.publishEvent(new AfterNoarkEntityCreatedEvent(this, createdCorrespondencePart));
        return ResponseEntity.status(HttpStatus.CREATED)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(createdCorrespondencePart.getVersion().toString())
                .body(correspondencePartHateoas);
    }

    /*
    // Create a new Precedence and associate it with the given journalpost
    // POST [contextPath][api]/sakarkiv/journalpost/{systemId}/ny-presedens
    // http://rel.kxml.no/noark5/v4/api/sakarkiv/ny-presedens/
    @ApiOperation(value = "Persists a Precedence object associated with the given Record systemId",
            notes = "Returns the newly created Precedence object after it was associated with a " +
                    "Record object and persisted to the database", response = PrecedenceHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Precedence " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = PrecedenceHateoas.class),
            @ApiResponse(code = 201, message = "Precedence " + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = PrecedenceHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_PARENT_DOES_NOT_EXIST + " of type Precedence"),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.POST, value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS +
            SLASH + NEW_PRECEDENCE, consumes = {NOARK5_V4_CONTENT_TYPE_JSON})
    public ResponseEntity<PrecedenceHateoas> createPrecedenceAssociatedWithRecord(
            HttpServletRequest request,
            @ApiParam(name = "systemID",
                    value = "systemId of record to associate the Precedence with.",
                    required = true)
            @PathVariable("systemID") String systemID,
            @ApiParam(name = "Precedence",
                    value = "Incoming Precedence object",
                    required = true)
            @RequestBody Precedence Precedence)
            throws NikitaException {

        Precedence createdPrecedence =
                registryEntryService.createPrecedenceAssociatedWithRecord(systemID, Precedence);
        PrecedenceHateoas precedenceHateoas =
                new PrecedenceHateoas(createdPrecedence);
        precedenceHateoasHandler.addLinks(PrecedenceHateoas, request, new Authorisation());
        applicationEventPublisher.publishEvent(new AfterNoarkEntityCreatedEvent(this, createdPrecedence));
        return ResponseEntity.status(HttpStatus.CREATED)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
            .header(ETAG, .getVersion().toString())
                .body(precedenceHateoas);
    }


    // Create a new SignOff and associate it with the given journalpost
    // POST [contextPath][api]/sakarkiv/journalpost/{systemId}/ny-avskrivning
    //  http://rel.kxml.no/noark5/v4/api/sakarkiv/ny-avskrivning/
    @ApiOperation(value = "Persists a SignOff object associated with the given Record systemId",
            notes = "Returns the newly created SignOff object after it was associated with a " +
                    "Record object and persisted to the database", response = SignOffHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SignOff " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = SignOffHateoas.class),
            @ApiResponse(code = 201, message = "SignOff " + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = SignOffHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_PARENT_DOES_NOT_EXIST + " of type SignOff"),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.POST, value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS +
            SLASH + NEW_DOCUMENT_DESCRIPTION, consumes = {NOARK5_V4_CONTENT_TYPE_JSON})
    public ResponseEntity<SignOffHateoas>
    createSignOffAssociatedWithRecord(
            HttpServletRequest request,
            @ApiParam(name = "systemID",
                    value = "systemId of record to associate the signOff with.",
                    required = true)
            @PathVariable("systemID") String systemID,
            @ApiParam(name = "signOff",
                    value = "Incoming signOff object",
                    required = true)
            @RequestBody SignOff signOff)
            throws NikitaException {

        SignOff createdSignOff =
                recordService.createSignOffAssociatedWithRecord(systemID, signOff);
        SignOffHateoas signOffHateoas =
                new SignOffHateoas(createdSignOff);
        signOffHateoasHandler.addLinks(signOffHateoas, request, new Authorisation());
        applicationEventPublisher.publishEvent(new AfterNoarkEntityCreatedEvent(this, createdSignOff));
        return ResponseEntity.status(HttpStatus.CREATED)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
             .header(ETAG, .getVersion().toString())
                .body(signOffHateoas);
    }
   */

    // Create a new CorrespondencePart and associate it with the given RegistryEntry
    // POST [contextPath][api]/sakarkiv/journalpost/{systemId}/ny-korrespondansepart
    // http://rel.kxml.no/noark5/v4/api/sakarkiv/ny-korrespondansepart/
    @ApiOperation(value = "Persists a DocumentObject associated with the given Record systemId",
            notes = "Returns the newly created DocumentObject after it was associated with a " +
                    "Record and persisted to the database. A DocumentObject should not be associated with both a " +
                    "Record and a DocumentDescription. Choose one!", response = DocumentObjectHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "DocumentObject " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = DocumentObjectHateoas.class),
            @ApiResponse(code = 201, message = "DocumentObject " + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = DocumentObjectHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_PARENT_DOES_NOT_EXIST + " of type DocumentObject"),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.POST, value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS
            + SLASH + NEW_DOCUMENT_OBJECT, consumes = {NOARK5_V4_CONTENT_TYPE_JSON})
    public ResponseEntity<String> createDocumentObjectAssociatedWithRecord(
            HttpServletRequest request,
            @ApiParam(name = "systemID",
                    value = "systemID of record to associate the documentObject with.",
                    required = true)
            @PathVariable String systemID,
            @ApiParam(name = "documentObject",
                    value = "Incoming documentObject object",
                    required = true)
            @RequestBody DocumentObject documentObject)
            throws NikitaException {
        /*
        DocumentObjectHateoas documentObjectHateoas =
                new DocumentObjectHateoas(
                        recordService.createDocumentObjectAssociatedWithRecord(systemID,
                                documentObject));
        documentObjectHateoasHandler.addLinks(documentObjectHateoas, request, new Authorisation());
        applicationEventPublisher.publishEvent(new AfterNoarkEntityCreatedEvent(this, ));
           return ResponseEntity.status(HttpStatus.CREATED)
                .header(ETAG, .getVersion().toString())
                .body(documentObjectHateoas);
        */
        return new ResponseEntity<>(API_MESSAGE_NOT_IMPLEMENTED, HttpStatus.NOT_IMPLEMENTED);
    }

    // Retrieve all CorrespondencePart associated with a RegistryEntry identified by systemId
    // GET [contextPath][api]/sakarkiv/journalpost/{systemId}/korrespondansepart
    // http://rel.kxml.no/noark5/v4/api/sakarkiv/korrespondansepart/
    @ApiOperation(value = "Retrieves a list of CorrespondenceParts associated with a RegistryEntry",
            response = CorrespondencePart.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "CorrespondencePart returned", response = CorrespondencePart.class), //CorrespondencePartHateoas
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS + SLASH +
            CORRESPONDENCE_PART, method = RequestMethod.GET)
    public ResponseEntity<String> findAllCorrespondencePartAssociatedWithRecord(
            HttpServletRequest request,
            @ApiParam(name = "systemID",
                    value = "systemID of the file to retrieve associated Record",
                    required = true)
            @PathVariable("systemID") final String systemID) {
        /*  Record record = recordService.findBySystemId(systemID);
            if (record == null) {
            throw new NoarkEntityNotFoundException("Could not find File object with systemID " + systemID);
        }
        CorrespondencePartHateoas documentDescriptionHateoas = new
                CorrespondencePartHateoas(new ArrayList<>(record.getReferenceCorrespondencePart()));
        documentDescriptionHateoasHandler.addLinks(documentDescriptionHateoas, request, new Authorisation());
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .body(documentDescriptionHateoas);
                */
        return new ResponseEntity<>(API_MESSAGE_NOT_IMPLEMENTED, HttpStatus.NOT_IMPLEMENTED);
    }


    // Create a suggested CorrespondencePart (like a template) object with default values (nothing persisted)
    // GET [contextPath][api]/sakarkiv/journalpost/{systemId}/ny-korrespondansepart
    @ApiOperation(value = "Suggests the contents of a new CorrespondencePart object", notes = "Returns a pre-filled CorrespondencePart object" +
            " with values relevant for the logged-in user", response = CorrespondencePartHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "CorrespondencePart " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = CorrespondencePartHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.GET, value = {SLASH + LEFT_PARENTHESIS +
            SYSTEM_ID + RIGHT_PARENTHESIS + SLASH + NEW_CORRESPONDENCE_PART})
    public ResponseEntity<CorrespondencePartHateoas> getCorrespondencePartTemplate(
            HttpServletRequest request
    ) throws NikitaException {
        CorrespondencePart suggestedCorrespondencePart = new CorrespondencePart();
        // TODO: This should be replaced with configurable data based on whoever is logged in
        //       Currently just returns the test values

        List<CorrespondencePartType> correspondencePartTypes = correspondencePartTypeService.findByCode(CORRESPONDENCE_PART_CODE_EA);
        if (correspondencePartTypes.get(0) == null) {
            throw new NikitaException("Internal error, metadata missing. [" + CORRESPONDENCE_PART_CODE_EA + "] returns no value");
        }
        CorrespondencePartType correspondencePartType = correspondencePartTypes.get(0);
        suggestedCorrespondencePart.setCorrespondencePartType(correspondencePartType);
        suggestedCorrespondencePart.setCorrespondencePartName("Frank Grimes");
        CorrespondencePartHateoas correspondencePartHateoas = new CorrespondencePartHateoas(suggestedCorrespondencePart);
        correspondencePartHateoasHandler.addLinksOnTemplate(correspondencePartHateoas, request, new Authorisation());
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .body(correspondencePartHateoas);
    }
    
    // Retrieve all SignOff associated with a RegistryEntry identified by systemId
    // GET [contextPath][api]/sakarkiv/journalpost/{systemId}/avskrivning
    // http://rel.kxml.no/noark5/v4/api/sakarkiv/avskrivning/
    @ApiOperation(value = "Retrieves a list of SignOffs associated with a RegistryEntry",
            response = SignOff.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SignOff returned", response = SignOff.class), //SignOffHateoas
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS + SLASH +
            SIGN_OFF, method = RequestMethod.GET)
    public ResponseEntity<String> findAllSignOffAssociatedWithRecord(
            HttpServletRequest request,
            @ApiParam(name = "systemID",
                    value = "systemID of the file to retrieve associated Record",
                    required = true)
            @PathVariable("systemID") final String systemID) {
        /*  Record record = recordService.findBySystemId(systemID);
            if (record == null) {
            throw new NoarkEntityNotFoundException("Could not find File object with systemID " + systemID);
        }
        SignOffHateoas documentDescriptionHateoas = new
                SignOffHateoas(new ArrayList<>(record.getReferenceSignOff()));
        documentDescriptionHateoasHandler.addLinks(documentDescriptionHateoas, request, new Authorisation());
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .body(documentDescriptionHateoas);
                */
        return new ResponseEntity<>(API_MESSAGE_NOT_IMPLEMENTED, HttpStatus.NOT_IMPLEMENTED);
    }

    // Retrieve all Precedence associated with a RegistryEntry identified by systemId
    // GET [contextPath][api]/sakarkiv/journalpost/{systemId}/presedens
    // http://rel.kxml.no/noark5/v4/api/sakarkiv/presedens/
    @ApiOperation(value = "Retrieves a list of Precedences associated with a RegistryEntry",
            response = Precedence.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Precedence returned", response = PrecedenceHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS + SLASH +
            PRECEDENCE, method = RequestMethod.GET)
    public ResponseEntity<String> findAllPrecedenceAssociatedWithRecord(
            HttpServletRequest request,
            @ApiParam(name = "systemID",
                    value = "systemID of the registryEntry to retrieve associated Precedence",
                    required = true)
            @PathVariable("systemID") final String systemID) {
        /*   Record record = recordService.findBySystemId(systemID);
        if (record == null) {
            throw new NoarkEntityNotFoundException("Could not find File object with systemID " + systemID);
        }
        PrecedenceHateoas documentDescriptionHateoas = new
                PrecedenceHateoas(new ArrayList<>(record.getReferencePrecedence()));
        documentDescriptionHateoasHandler.addLinks(documentDescriptionHateoas, request, new Authorisation());
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .body(documentDescriptionHateoas);
                */
        return new ResponseEntity<>(API_MESSAGE_NOT_IMPLEMENTED, HttpStatus.NOT_IMPLEMENTED);
    }

    // Retrieve all DocumentFlow associated with a RegistryEntry identified by systemId
    // GET [contextPath][api]/sakarkiv/journalpost/{systemId}/dokumentflyt
    // http://rel.kxml.no/noark5/v4/api/sakarkiv/dokumentflyt/
    @ApiOperation(value = "Retrieves a list of DocumentFlows associated with a RegistryEntry",
            response = DocumentFlow.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "DocumentFlow returned", response = DocumentFlow.class), //DocumentFlowHateoas
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS + SLASH +
            DOCUMENT_FLOW, method = RequestMethod.GET)
    public ResponseEntity<String> findAllDocumentFlowAssociatedWithRecord(
            HttpServletRequest request,
            @ApiParam(name = "systemID",
                    value = "systemID of the file to retrieve associated Record",
                    required = true)
            @PathVariable("systemID") final String systemID) {
        /*  Record record = recordService.findBySystemId(systemID);
            if (record == null) {
            throw new NoarkEntityNotFoundException("Could not find File object with systemID " + systemID);
        }
        DocumentFlowHateoas documentDescriptionHateoas = new
                DocumentFlowHateoas(new ArrayList<>(record.getReferenceDocumentFlow()));
        documentDescriptionHateoasHandler.addLinks(documentDescriptionHateoas, request, new Authorisation());
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .body(documentDescriptionHateoas);
                */
        return new ResponseEntity<>(API_MESSAGE_NOT_IMPLEMENTED, HttpStatus.NOT_IMPLEMENTED);
    }


    // Retrieve a single registryEntry identified by systemId
    // GET [contextPath][api]/sakarkiv/journalpost/{systemID}
    @ApiOperation(value = "Retrieves a single RegistryEntry entity given a systemId", response = RegistryEntry.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "RegistryEntry returned", response = RegistryEntry.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS, method = RequestMethod.GET)
    public ResponseEntity<RegistryEntryHateoas> findOneRegistryEntrybySystemId(
            HttpServletRequest request,
            @ApiParam(name = "systemID",
                    value = "systemID of the registryEntry to retrieve",
                    required = true)
            @PathVariable("systemID") final String registryEntrySystemId) {
        RegistryEntry registryEntry = registryEntryService.findBySystemId(registryEntrySystemId);

        RegistryEntryHateoas registryEntryHateoas = new
                RegistryEntryHateoas(registryEntry);
        registryEntryHateoasHandler.addLinks(registryEntryHateoas, request, new Authorisation());
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(registryEntry.getVersion().toString())
                .body(registryEntryHateoas);
    }

    // Get all registryEntry
    // GET [contextPath][api]/sakarkiv/journalpost/
    // http://rel.kxml.no/noark5/v4/api/sakarkiv/journalpost/
    @ApiOperation(value = "Retrieves multiple RegistryEntry entities limited by ownership rights", notes = "The field skip" +
            "tells how many RegistryEntry rows of the result set to ignore (starting at 0), while  top tells how many rows" +
            " after skip to return. Note if the value of top is greater than system value " +
            " nikita-noark5-core.pagination.maxPageSize, then nikita-noark5-core.pagination.maxPageSize is used. ",
            response = RegistryEntryHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "RegistryEntry found",
                    response = RegistryEntryHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<RegistryEntryHateoas> findAllRegistryEntry(
            HttpServletRequest request,
            @RequestParam(name = "top", required = false) Integer top,
            @RequestParam(name = "skip", required = false) Integer skip) {
        RegistryEntryHateoas registryEntryHateoas = new
                RegistryEntryHateoas((ArrayList<INikitaEntity>) (ArrayList)
                registryEntryService.findRegistryEntryByOwnerPaginated(top, skip));
        registryEntryHateoasHandler.addLinks(registryEntryHateoas, request, new Authorisation());
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .body(registryEntryHateoas);
    }

    // Delete a Record identified by systemID
    // DELETE [contextPath][api]/sakarkiv/journalpost/{systemId}/
    @ApiOperation(value = "Deletes a single RegistryEntry entity identified by systemID", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Parent entity (DocumentDescription or Record) returned", response = String.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS,
            method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteRecordBySystemId(HttpServletRequest request,
                                                         @ApiParam(name = "systemID",
                    value = "systemID of the record to delete",
                    required = true)
            @PathVariable("systemID") final String systemID) {

        RegistryEntry registryEntry = registryEntryService.findBySystemId(systemID);
        registryEntryService.deleteEntity(systemID);
        applicationEventPublisher.publishEvent(new AfterNoarkEntityDeletedEvent(this, registryEntry));
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .body(CommonUtils.WebUtils.getSuccessStatusStringForDelete());
    }


    // Update a RegistryEntry with given values
    // PUT [contextPath][api]/sakarkiv/journalpost/{systemId}
    @ApiOperation(value = "Updates a RegistryEntry identified by a given systemId", notes = "Returns the newly updated registryEntry",
            response = RegistryEntryHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "RegistryEntry " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = RegistryEntryHateoas.class),
            @ApiResponse(code = 201, message = "RegistryEntry " + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = RegistryEntryHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_PARENT_DOES_NOT_EXIST + " of type RegistryEntry"),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS, method = RequestMethod.PUT, consumes = {NOARK5_V4_CONTENT_TYPE_JSON})
    public ResponseEntity<RegistryEntryHateoas> updateRegistryEntry(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "systemID",
                    value = "systemId of registryEntry to update",
                    required = true)
            @PathVariable("systemID") final String systemID,
            @ApiParam(name = "RegistryEntry",
                    value = "Incoming registryEntry object",
                    required = true)
            @RequestBody RegistryEntry registryEntry) throws NikitaException {
        validateForUpdate(registryEntry);

        RegistryEntry updatedRegistryEntry = registryEntryService.handleUpdate(systemID, parseETAG(request.getHeader(ETAG)), registryEntry);
        RegistryEntryHateoas registryEntryHateoas = new RegistryEntryHateoas(updatedRegistryEntry);
        registryEntryHateoasHandler.addLinks(registryEntryHateoas, request, new Authorisation());
        applicationEventPublisher.publishEvent(new AfterNoarkEntityUpdatedEvent(this, updatedRegistryEntry));
        return ResponseEntity.status(HttpStatus.CREATED)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(updatedRegistryEntry.getVersion().toString())
                .body(registryEntryHateoas);
    }
}
