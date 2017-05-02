package no.arkivlab.hioa.nikita.webapp.service.impl.metadata;

import nikita.model.noark5.v4.metadata.CorrespondencePartType;
import nikita.repository.n5v4.metadata.ICorrespondencePartTypeRepository;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.metadata.ICorrespondencePartTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class CorrespondencePartTypeService implements ICorrespondencePartTypeService {

    private static final Logger logger = LoggerFactory.getLogger(CorrespondencePartTypeService.class);
    private ICorrespondencePartTypeRepository correspondencePartTypeRepository;

    public CorrespondencePartTypeService(ICorrespondencePartTypeRepository correspondencePartTypeRepository) {
        this.correspondencePartTypeRepository = correspondencePartTypeRepository;
    }

    // All CREATE operations

    /**
     * Persists a new correspondencePartType object to the database.
     *
     * @param correspondencePartType correspondencePartType object with values set
     * @return the newly persisted correspondencePartType object
     */
    @Override
    public CorrespondencePartType createNewCorrespondencePartType(CorrespondencePartType correspondencePartType) {
        correspondencePartType.setDeleted(false);
        correspondencePartType.setOwnedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        return correspondencePartTypeRepository.save(correspondencePartType);
    }

    // All READ operations

    /**
     * retrieve all correspondencePartType
     *
     * @return
     */
    @Override
    public Iterable<CorrespondencePartType> findAll() {
        return correspondencePartTypeRepository.findAll();
    }

    // find by systemId

    /**
     * retrieve a single correspondencePartType identified by systemId
     *
     * @param systemId
     * @return
     */
    @Override
    public CorrespondencePartType findBySystemId(String systemId) {
        return correspondencePartTypeRepository.findBySystemId(systemId);
    }

    /**
     * retrieve all correspondencePartType that have a particular description. <br>
     * This will be replaced by OData search.
     *
     * @param description
     * @return
     */
    @Override
    public List<CorrespondencePartType> findByDescription(String description) {
        return correspondencePartTypeRepository.findByDescription(description);
    }

    /**
     * retrieve all correspondencePartType that have a particular code. <br>
     * This will be replaced by OData search.
     *
     * @param code
     * @return
     */
    @Override
    public List<CorrespondencePartType> findByCode(String code) {
        return correspondencePartTypeRepository.findByCode(code);
    }

    /**
     * update a particular correspondencePartType. <br>
     *
     * @param correspondencePartType
     * @return the updated correspondencePartType
     */
    @Override
    public CorrespondencePartType update(CorrespondencePartType correspondencePartType) {
        return correspondencePartTypeRepository.save(correspondencePartType);
    }

    @Override
    public List<CorrespondencePartType> findAllAsList() {
        return correspondencePartTypeRepository.findAll();
    }
}