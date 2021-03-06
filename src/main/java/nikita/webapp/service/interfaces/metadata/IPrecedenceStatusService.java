package nikita.webapp.service.interfaces.metadata;

import nikita.common.model.noark5.v4.hateoas.metadata.MetadataHateoas;
import nikita.common.model.noark5.v4.metadata.PrecedenceStatus;

/**
 * Created by tsodring on 19/02/18.
 */

public interface IPrecedenceStatusService {

    MetadataHateoas createNewPrecedenceStatus(PrecedenceStatus
                                                      precedenceStatus);

    MetadataHateoas find(String systemId);

    MetadataHateoas findAll();

    MetadataHateoas findByDescription(String description);

    MetadataHateoas findByCode(String code);

    MetadataHateoas handleUpdate(String systemId, Long version,
                                 PrecedenceStatus precedenceStatus);

    PrecedenceStatus generateDefaultPrecedenceStatus();
}
