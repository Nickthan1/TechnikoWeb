package nikos.technikoweb.serializer;

import com.fasterxml.jackson.databind.JsonDeserializer;
import jakarta.json.bind.serializer.DeserializationContext;
import jakarta.json.stream.JsonParser;

import java.io.IOException;
import java.time.LocalDateTime;

public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
    @Override
    public LocalDateTime deserialize(JsonParser arg0, DeserializationContext arg1) throws IOException {
       String tt = arg0.getText();
       LocalDateTime ll=   LocalDateTime.parse(tt);
       // example 2022-12-06T01:48:06.332389200
        //  LocalDateTime ll= LocalDateTime.now();
        return ll;
    }
}