package src.main.java;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import src.main.java.generators.StatementGenerator;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        try {
            JsonParser parser = new JsonParser();
            JsonArray invoices = parser.parse(Files.readString(Path.of("./data/invoices.json"))).getAsJsonArray();
            JsonObject plays = parser.parse(Files.readString(Path.of("./data/plays.json"))).getAsJsonObject();
            StatementGenerator generator = new StatementGenerator();
            String statement = generator.generateStatement(invoices, plays);
            System.out.println(statement);

        } catch (java.io.IOException e) {
            System.err.println("Error reading JSON files: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
        }
    }
}
