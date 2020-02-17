package test.stacktrace;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Test;

import java.io.StringWriter;

public class HowToConvertStackTraceToJson {
    @Test
    public void convertStackTraceToJson() throws Exception {
        try {
            throw new NullPointerException();
        } catch (Exception e) {
            System.out.println(asJson(e));
        }
    }

    private String asJson(Object obj) throws Exception {
        StringWriter w = new StringWriter();
        new ObjectMapper().configure(SerializationFeature.INDENT_OUTPUT, true).writeValue(w, obj);
        String result = w.toString();
        return result;  }
}