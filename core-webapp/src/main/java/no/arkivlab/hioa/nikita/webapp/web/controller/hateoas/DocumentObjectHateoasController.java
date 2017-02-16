package no.arkivlab.hioa.nikita.webapp.web.controller.hateoas;

import com.codahale.metrics.annotation.Counted;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nikita.model.noark5.v4.DocumentObject;
import nikita.model.noark5.v4.hateoas.DocumentObjectHateoas;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.IDocumentObjectHateoasHandler;
import no.arkivlab.hioa.nikita.webapp.security.Authorisation;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.IDocumentObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

import static nikita.config.Constants.*;
import static nikita.config.N5ResourceMappings.DOCUMENT_OBJECT;
import static nikita.config.N5ResourceMappings.SYSTEM_ID;

@RestController
@RequestMapping(value = HATEOAS_API_PATH + SLASH + NOARK_FONDS_STRUCTURE_PATH + SLASH + DOCUMENT_OBJECT,
        produces = {NOARK5_V4_CONTENT_TYPE})
public class DocumentObjectHateoasController {

    @Autowired
    IDocumentObjectService documentObjectService;

    @Autowired
    IDocumentObjectHateoasHandler documentObjectHateoasHandler;

    // API - All GET Requests (CRUD - READ)

    @ApiOperation(value = "Retrieves a single DocumentObject entity given a systemId", response = DocumentObject.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "DocumentObject returned", response = DocumentObject.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS, method = RequestMethod.GET)
    public ResponseEntity<DocumentObjectHateoas> findOneDocumentObjectBySystemId(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "systemID",
                    value = "systemID of the documentObject to retrieve",
                    required = true)
            @PathVariable("systemID") final String documentObjectSystemId) {
        DocumentObjectHateoas documentObjectHateoas = new
                DocumentObjectHateoas(documentObjectService.findBySystemId(documentObjectSystemId));
        documentObjectHateoasHandler.addLinks(documentObjectHateoas, request, new Authorisation());
        return new ResponseEntity<>(documentObjectHateoas, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Retrieves multiple DocumentObject entities limited by ownership rights", notes = "The field skip" +
            "tells how many DocumentObject rows of the result set to ignore (starting at 0), while  top tells how many rows" +
            " after skip to return. Note if the value of top is greater than system value " +
            " nikita-noark5-core.pagination.maxPageSize, then nikita-noark5-core.pagination.maxPageSize is used. ",
            response = DocumentObjectHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "DocumentObject list found",
                    response = DocumentObjectHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<DocumentObjectHateoas> findAllDocumentObject(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @RequestParam(name = "top", required = false) Integer top,
            @RequestParam(name = "skip", required = false) Integer skip) {

        DocumentObjectHateoas documentObjectHateoas = new
                DocumentObjectHateoas((ArrayList<INoarkSystemIdEntity>) (ArrayList)
                documentObjectService.findDocumentObjectByOwnerPaginated(top, skip));
        documentObjectHateoasHandler.addLinks(documentObjectHateoas, request, new Authorisation());
        return new ResponseEntity<>(documentObjectHateoas, HttpStatus.OK);
    }
}
