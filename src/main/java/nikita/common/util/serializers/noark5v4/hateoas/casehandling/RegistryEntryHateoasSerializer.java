package nikita.common.util.serializers.noark5v4.hateoas.casehandling;

import com.fasterxml.jackson.core.JsonGenerator;
import nikita.common.model.noark5.v4.casehandling.RegistryEntry;
import nikita.common.model.noark5.v4.hateoas.HateoasNoarkObject;
import nikita.common.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.common.util.CommonUtils;
import nikita.common.util.serializers.noark5v4.hateoas.HateoasSerializer;
import nikita.common.util.serializers.noark5v4.hateoas.interfaces.IHateoasSerializer;

import java.io.IOException;

import static nikita.common.config.N5ResourceMappings.*;
import static nikita.common.util.CommonUtils.Hateoas.Serialize;

/**
 * Serialise an outgoing RegistryEntry object as JSON.
 * <p>
 * Having an own serializer is done to have more fine grained control over the output. We need to be able to especially
 * control the HATEOAS links and the actual format of the HATEOAS links might change over time with the standard. This
 * allows us to be able to easily adapt to any changes
 * <p>
 * Only Norwegian property names are used on the outgoing JSON property names and are in accordance with the Noark
 * standard.
 * <p>
 * Note. Only values that are part of the standard are included in the JSON. Properties like 'id' or 'deleted' are not
 * exported
 */

public class RegistryEntryHateoasSerializer extends HateoasSerializer implements IHateoasSerializer {

    @Override
    public void serializeNoarkEntity(INikitaEntity noarkSystemIdEntity,
                                     HateoasNoarkObject registryEntryHateoas, JsonGenerator jgen) throws IOException {

        RegistryEntry registryEntry = (RegistryEntry) noarkSystemIdEntity;

        jgen.writeStartObject();

        CommonUtils.Hateoas.Serialize.printSystemIdEntity(jgen, registryEntry);
        CommonUtils.Hateoas.Serialize.printCreateEntity(jgen, registryEntry);
        if (registryEntry.getArchivedDate() != null) {
            jgen.writeStringField(RECORD_ARCHIVED_DATE,
                    Serialize.formatDateTime(registryEntry.getArchivedDate()));
        }
        if (registryEntry.getArchivedBy() != null) {
            jgen.writeStringField(RECORD_ARCHIVED_BY, registryEntry.getArchivedBy());
        }
        CommonUtils.Hateoas.Serialize.printDisposal(jgen, registryEntry);
        CommonUtils.Hateoas.Serialize.printScreening(jgen, registryEntry);
        CommonUtils.Hateoas.Serialize.printClassified(jgen, registryEntry);
        // handle general registryEntry properties
        if (registryEntry.getTitle() != null) {
            jgen.writeStringField(TITLE, registryEntry.getTitle());
        }
        if (registryEntry.getOfficialTitle() != null) {
            jgen.writeStringField(FILE_PUBLIC_TITLE, registryEntry.getOfficialTitle());
        }
        if (registryEntry.getDescription() != null) {
            jgen.writeStringField(DESCRIPTION, registryEntry.getDescription());
        }
        CommonUtils.Hateoas.Serialize.printKeyword(jgen, registryEntry);
        CommonUtils.Hateoas.Serialize.printDocumentMedium(jgen, registryEntry);
        CommonUtils.Hateoas.Serialize.printStorageLocation(jgen, registryEntry);
        CommonUtils.Hateoas.Serialize.printComment(jgen, registryEntry);
        // TODO: FIX THIS CommonCommonUtils.Hateoas.Serialize.printCrossReference(jgen, registryEntry);
        if (registryEntry.getRecordYear() != null) {
            jgen.writeNumberField(REGISTRY_ENTRY_YEAR, registryEntry.getRecordYear().intValue());
        }
        if (registryEntry.getRecordSequenceNumber() != null) {
            jgen.writeNumberField(REGISTRY_ENTRY_SEQUENCE_NUMBER, registryEntry.getRecordSequenceNumber().intValue());
        }
        if (registryEntry.getRegistryEntryNumber() != null) {
            jgen.writeNumberField(REGISTRY_ENTRY_NUMBER, registryEntry.getRegistryEntryNumber().intValue());
        }
        if (registryEntry.getRegistryEntryType() != null) {
            jgen.writeStringField(REGISTRY_ENTRY_TYPE, registryEntry.getRegistryEntryType());
        }
        if (registryEntry.getRecordStatus() != null) {
            jgen.writeStringField(REGISTRY_ENTRY_STATUS, registryEntry.getRecordStatus());
        }
        if (registryEntry.getRecordDate() != null) {
            jgen.writeStringField(REGISTRY_ENTRY_DATE,
                    Serialize.formatDate(registryEntry.getRecordDate()));
        }
        if (registryEntry.getDocumentDate() != null) {
            jgen.writeStringField(REGISTRY_ENTRY_DOCUMENT_DATE,
                    Serialize.formatDate(registryEntry.getDocumentDate()));
        }
        if (registryEntry.getReceivedDate() != null) {
            jgen.writeStringField(REGISTRY_ENTRY_RECEIVED_DATE,
                    Serialize.formatDate(registryEntry.getReceivedDate()));
        }
        if (registryEntry.getSentDate() != null) {
            jgen.writeStringField(REGISTRY_ENTRY_SENT_DATE,
                    Serialize.formatDate(registryEntry.getSentDate()));
        }
        if (registryEntry.getDueDate() != null) {
            jgen.writeStringField(REGISTRY_ENTRY_DUE_DATE,
                    Serialize.formatDate(registryEntry.getDueDate()));
        }
        if (registryEntry.getFreedomAssessmentDate() != null) {
            jgen.writeStringField(REGISTRY_ENTRY_RECORD_FREEDOM_ASSESSMENT_DATE,
                    Serialize.formatDate(registryEntry.getFreedomAssessmentDate()));
        }
        if (registryEntry.getNumberOfAttachments() != null) {
            jgen.writeNumberField(REGISTRY_ENTRY_NUMBER_OF_ATTACHMENTS, registryEntry.getNumberOfAttachments().intValue());
        }
        if (registryEntry.getLoanedDate() != null) {
            jgen.writeStringField(CASE_LOANED_DATE,
                    Serialize.formatDate(registryEntry.getLoanedDate()));
        }
        if (registryEntry.getLoanedTo() != null) {
            jgen.writeStringField(CASE_LOANED_TO, registryEntry.getLoanedTo());
        }
        /*
        TODO: Temp disabled!
        CommonUtils.Hateoas.Serialize.printCorrespondencePartPersons(jgen, registryEntry);
        CommonUtils.Hateoas.Serialize.printCorrespondencePartUnits(jgen, registryEntry);
        CommonUtils.Hateoas.Serialize.printCorrespondencePartInternals(jgen, registryEntry);
        */
        CommonUtils.Hateoas.Serialize.printSignOff(jgen, registryEntry);
        CommonUtils.Hateoas.Serialize.printDocumentFlow(jgen, registryEntry);
        //CommonUtils.Hateoas.Serialize.printPrecedence(jgen, registryEntry);
        CommonUtils.Hateoas.Serialize.printElectronicSignature(jgen, registryEntry);
        CommonUtils.Hateoas.Serialize.printHateoasLinks(jgen, registryEntryHateoas.getLinks(registryEntry));
        jgen.writeEndObject();
    }
}
