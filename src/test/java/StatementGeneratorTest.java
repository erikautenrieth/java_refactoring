package src.test.java;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import main.java.generators.StatementGenerator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class StatementGeneratorTest {

    @Test
    public void testGenerateStatement() throws IOException {
        JsonParser parser = new JsonParser();

        String basePath = "src/test/resources/json";
        JsonArray invoices = parser.parse(Files.readString(Path.of(basePath, "invoices.json"))).getAsJsonArray();
        JsonObject plays = parser.parse(Files.readString(Path.of(basePath, "plays.json"))).getAsJsonObject();

        StatementGenerator generator = new StatementGenerator();
        String actualStatement = generator.generateStatement(invoices, plays);

        String expectedStatement = ""
                + "Statement for BigCo\n"
                + "    Hamlet: $650.00 (55 seats)\r\n"
                + "    As You Like It: $580.00 (35 seats)\r\n"
                + "    Othello: $500.00 (40 seats)\r\n"
                + "  Amount owed is $1,730.00\r\n"
                + "  You earned 47 credits\r\n";

        assertEquals(expectedStatement.trim(), actualStatement.trim());
    }
}
