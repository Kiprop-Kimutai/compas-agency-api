package compas.models;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.security.Timestamp;
import java.time.LocalDateTime;

/**
 * Created by CLLSDJACKT013 on 05/06/2018.
 */
@Converter(autoApply = true)
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Timestamp> {
    @Override
    public Timestamp convertToDatabaseColumn(LocalDateTime attribute) {
        return null;
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Timestamp dbData) {
        return null;
    }

}
