package nikita.common.model.noark5.v4.metadata;

import nikita.common.config.N5ResourceMappings;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

// Noark 5v4 hendelsetype
@Entity
@Table(name = "event_type")
// Enable soft delete
// @SQLDelete(sql = "UPDATE event_type SET deleted = true WHERE pk_event_type_id = ? and version = ?")
// @Where(clause = "deleted <> true")
@AttributeOverride(name = "id", column = @Column(name = "pk_event_type_id"))
public class EventType extends MetadataSuperClass {
    private static final long serialVersionUID = 1L;

    @Override
    public String getBaseTypeName() {
        return N5ResourceMappings.EVENT_TYPE;
    }
}
