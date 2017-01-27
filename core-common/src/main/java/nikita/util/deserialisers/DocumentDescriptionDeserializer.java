package nikita.util.deserialisers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import nikita.util.exceptions.NikitaMalformedInputDataException;
import nikita.model.noark5.v4.DocumentDescription;
import nikita.model.noark5.v4.interfaces.entities.INoarkGeneralEntity;
import nikita.util.deserialisers.interfaces.ObligatoryPropertiesCheck;
import nikita.util.CommonUtils;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import static nikita.config.Constants.NOARK_DATE_FORMAT_PATTERN;
import static nikita.config.N5ResourceMappings.*;

/**
 * Created by tsodring on 1/6/17.
 *
 * Deserialise an incoming DocumentDescription JSON object.
 *
 * Having a own deserialiser is done to have more fine grained control over the input. This allows us to be less strict
 * with property names, allowing for both English and Norwegian property names
 *
 * Both English and Norwegian property names can be used in the incoming JSON as well as there being no requirement with
 * regards to small and large letters in property names.
 *
 * Note this implementation expects that the DocumentDescription object to deserialise is in compliance with the Noark standard where
 * certain properties i.e. createdBy and createdDate are set by the core, not the caller. This deserializer will not 
 * enforce this and will deserialize a documentDescription object correctly. This is because e.g the import interface will require
 * such functionality.
 *
 *  - Testing of compliance of properties is handled by the core, either in DocumentDescriptionController or DocumentDescriptionService
 * 
 * Note. Currently we do not include 'id' or 'deleted' properties. 'id' is a primary key and it is assumed this is 
 * taken care of by the DBMS and 'deleted' is a field internal to the core to handle soft delete. Importing soft deleted
 * objects is something we do not consider necessary.
 *
 * Note:
 *  - Unknown property values in the JSON will trigger an exception
 *  - Missing obligatory property values in the JSON will trigger an exception
 *  - DocumentDescription has no obligatory values required to be present at instantiation time
 */
public class DocumentDescriptionDeserializer extends JsonDeserializer implements ObligatoryPropertiesCheck {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public DocumentDescription deserialize(JsonParser jsonParser, DeserializationContext dc)
            throws IOException {
        DocumentDescription documentDescription = new DocumentDescription();

        ObjectNode objectNode = mapper.readTree(jsonParser);

        // Deserialise general record properties
        CommonUtils.Hateoas.Deserialize.deserialiseNoarkSystemIdEntity (documentDescription, objectNode);
        CommonUtils.Hateoas.Deserialize.deserialiseNoarkCreateEntity(documentDescription, objectNode);
        CommonUtils.Hateoas.Deserialize.deserialiseNoarkTitleDescriptionEntity(documentDescription, objectNode);

        // Deserialize documentType
        JsonNode currentNode = objectNode.get(DOCUMENT_DESCRIPTION_DOCUMENT_TYPE);
        String key = DOCUMENT_DESCRIPTION_DOCUMENT_TYPE;
        if (currentNode == null) {
            currentNode = objectNode.get(DOCUMENT_DESCRIPTION_DOCUMENT_TYPE_EN);
            key = DOCUMENT_DESCRIPTION_DOCUMENT_TYPE_EN;
        }
        if (currentNode != null) {
            documentDescription.setDocumentType(currentNode.textValue());
            objectNode.remove(key);
        }

        // Deserialize documentStatus
        currentNode = objectNode.get(DOCUMENT_DESCRIPTION_);
        key = DOCUMENT_DESCRIPTION_;
        if (currentNode == null) {
            currentNode = objectNode.get(DOCUMENT_DESCRIPTION_DOCUMENT_STATUS_EN);
            key = DOCUMENT_DESCRIPTION_DOCUMENT_STATUS_EN;
        }
        if (currentNode != null) {
            documentDescription.setDocumentStatus(currentNode.textValue());
            objectNode.remove(key);
        }

        // Deserialize associatedWithRecordAs
        currentNode = objectNode.get(DOCUMENT_DESCRIPTION_ASSOCIATED_WITH_RECORD_AS);
        key = DOCUMENT_DESCRIPTION_ASSOCIATED_WITH_RECORD_AS;
        if (currentNode == null) {
            currentNode = objectNode.get(DOCUMENT_DESCRIPTION_ASSOCIATED_WITH_RECORD_AS_EN);
            key = DOCUMENT_DESCRIPTION_ASSOCIATED_WITH_RECORD_AS_EN;
        }
        if (currentNode != null) {
            documentDescription.setAssociatedWithRecordAs(currentNode.textValue());
            objectNode.remove(key);
        }
        // Deserialize documentNumber
        currentNode = objectNode.get(DOCUMENT_DESCRIPTION_DOCUMENT_NUMBER);
        key = DOCUMENT_DESCRIPTION_DOCUMENT_NUMBER;
        if (currentNode == null) {
            currentNode = objectNode.get(DOCUMENT_DESCRIPTION_DOCUMENT_NUMBER_EN);
            key = DOCUMENT_DESCRIPTION_DOCUMENT_NUMBER_EN;
        }
        if (currentNode != null) {
            documentDescription.setDocumentNumber(Integer.getInteger(currentNode.textValue()));
            objectNode.remove(key);
        }
        // Deserialize associationDate
        currentNode = objectNode.get(DOCUMENT_DESCRIPTION_ASSOCIATION_DATE);
        key = DOCUMENT_DESCRIPTION_ASSOCIATION_DATE;
        if (currentNode == null) {
            currentNode = objectNode.get(DOCUMENT_DESCRIPTION_ASSOCIATION_DATE_EN);
            key = DOCUMENT_DESCRIPTION_ASSOCIATION_DATE_EN;
        }
        if (currentNode != null) {
            try {
                Date parsedDate = CommonUtils.Hateoas.Deserialize.dateTimeFormat.parse(currentNode.textValue());
                documentDescription.setAssociationDate(parsedDate);
            }
            catch (ParseException e) {
                throw new NikitaMalformedInputDataException("The DocumentDescription object you tried to create " +
                        "has a malformed tilknyttetDato/associationDate. Make sure format is " +
                        NOARK_DATE_FORMAT_PATTERN);
            }
            objectNode.remove(key);
        }

        // Deserialize associatedBy
        currentNode = objectNode.get(DOCUMENT_DESCRIPTION_ASSOCIATED_BY);
        key = DOCUMENT_DESCRIPTION_ASSOCIATED_BY;
        if (currentNode == null) {
            currentNode = objectNode.get(DOCUMENT_DESCRIPTION_ASSOCIATED_BY_EN);
            key = DOCUMENT_DESCRIPTION_ASSOCIATED_BY_EN;
        }
        if (currentNode != null) {
            documentDescription.setAssociatedBy(currentNode.textValue());
            objectNode.remove(key);
        }

        // Deserialize general documentDescription properties
        CommonUtils.Hateoas.Deserialize.deserialiseDocumentMedium(documentDescription, objectNode);

        // Check that there are no additional values left after processing the tree
        // If there are additional throw a malformed input exception
        if (objectNode.size() != 0) {
            throw new NikitaMalformedInputDataException("The DocumentDescription object you tried to create is malformed. The "
                    + "following objects are not recognised as DocumentDescription properties [" +
                    CommonUtils.Hateoas.Deserialize.checkNodeObjectEmpty(objectNode) + "]");
        }
        return documentDescription;
    }

    @Override
    /**
     *
     *  DocumentDescription is not a INoarkGeneralEntity
     */
    public void checkForObligatoryNoarkValues(INoarkGeneralEntity noarkEntity) {
    }

    public void checkForObligatoryDocumentDescriptionValues(DocumentDescription documentDescription) {
        if (documentDescription.getDocumentStatus() == null) {
            throw new NikitaMalformedInputDataException("The DocumentDescription object you tried to create is " +
                    "malformed. The documentStatus field is mandatory, and you have submitted an empty value.");
        }
        if (documentDescription.getDocumentType() == null) {
            throw new NikitaMalformedInputDataException("The DocumentDescription object you tried to create is " +
                    "malformed. The documentType field is mandatory, and you have submitted an empty value.");
        }
        if (documentDescription.getTitle() == null) {
            throw new NikitaMalformedInputDataException("The DocumentDescription object you tried to create is " +
                    "malformed. The title field is mandatory, and you have submitted an empty value.");
        }
        if (documentDescription.getAssociatedWithRecordAs() == null) {
            throw new NikitaMalformedInputDataException("The DocumentDescription object you tried to create is " +
                    "malformed. The associatedWithRecordAs field is mandatory, and you have submitted an empty value.");
        }
    }

}
