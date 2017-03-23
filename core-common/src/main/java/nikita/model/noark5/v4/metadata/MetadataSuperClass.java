package nikita.model.noark5.v4.metadata;

import nikita.model.noark5.v4.interfaces.entities.IMetadataEntity;
import nikita.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import org.hibernate.envers.Audited;
import org.hibernate.search.annotations.Field;

import javax.persistence.*;

/**
 * Created by tsodring on 3/23/17.
 */
@MappedSuperclass
public class MetadataSuperClass implements INikitaEntity, INoarkSystemIdEntity, IMetadataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_id", nullable = false, insertable = true, updatable = false)
    protected Long id;

    /**
     * M001 - systemID (xs:string)
     */
    @Column(name = "system_id", unique = true)
    @Audited
    @Field
    protected String systemId;

    /**
     * M -  (xs:string)
     */
    @Column(name = "code")
    @Audited
    protected String code;

    /**
     * M021 - beskrivelse (xs:string)
     */
    @Column(name = "description")
    @Audited
    protected String description;
    @Column(name = "owned_by")
    @Audited
    protected String ownedBy;
    // Used for soft delete.
    @Column(name = "deleted")
    @Audited
    private Boolean deleted;

    @Override
    public String getSystemId() {
        return systemId;
    }

    @Override
    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Boolean getDeleted() {
        return deleted;
    }

    @Override
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String getOwnedBy() {
        return ownedBy;
    }

    @Override
    public void setOwnedBy(String ownedBy) {
        this.ownedBy = ownedBy;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + " {" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", deleted=" + deleted +
                ", ownedBy='" + ownedBy + '\'' +
                '}';
    }
}
