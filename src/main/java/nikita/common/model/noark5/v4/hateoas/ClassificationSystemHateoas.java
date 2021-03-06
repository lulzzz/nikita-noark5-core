package nikita.common.model.noark5.v4.hateoas;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nikita.common.config.N5ResourceMappings;
import nikita.common.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.common.util.serializers.noark5v4.hateoas.ClassificationSystemHateoasSerializer;

import java.util.List;

@JsonSerialize(using = ClassificationSystemHateoasSerializer.class)
public class ClassificationSystemHateoas extends HateoasNoarkObject implements IHateoasNoarkObject {

    public ClassificationSystemHateoas(INikitaEntity entity) {
        super(entity);
    }

    public ClassificationSystemHateoas(List<INikitaEntity> entityList) {
        super(entityList, N5ResourceMappings.CLASSIFICATION_SYSTEM);
    }
}
