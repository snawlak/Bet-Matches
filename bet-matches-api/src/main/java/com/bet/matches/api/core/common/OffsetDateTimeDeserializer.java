package com.bet.matches.api.core.common;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;

import java.io.IOException;
import java.time.OffsetDateTime;

public class OffsetDateTimeDeserializer extends JsonDeserializer<OffsetDateTime> {

    @Override
    public OffsetDateTime deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
        String date = parser.getText();
        if (date != null) {
            date = date.replace("%2B", "+");
            return OffsetDateTime.parse(date);
        }
        return null;
    }
}
