package nikita.common.repository.n5v4;

import nikita.common.model.noark5.v4.File;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IFileRepository extends
        PagingAndSortingRepository<File, Long> {

    // -- All SAVE operations
    @Override
    File save(File file);

    // -- All READ operations
    @Override
    List<File> findAll();

    // id
    Optional<File> findById(Long id);

    // systemId
    File findBySystemId(String systemId);

    // ownedBy
    List<File> findByOwnedBy(String ownedBy);
}
