package main.java.generators;

import main.java.calculators.CalculatorFactory;
import main.java.calculators.PerformanceCalculator;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.Locale;

public class StatementGenerator {

    /**
     * Erzeugt ein Statement aus den übergebenen JSON-Daten.
     *
     * @param invoices JSON-Array mit Rechnungen.
     * @param plays    JSON-Objekt mit Spieldefinitionen.
     * @return Formatiertes Statement als String.
     */
    public String generateStatement(JsonArray invoices, JsonObject plays) {
        int totalCost = 0;
        int totalVolumeCredits = 0;
        StringBuilder resultBuilder = new StringBuilder();

        JsonObject invoice = invoices.get(0).getAsJsonObject();
        String customerName = invoice.get("customer").getAsString();
        resultBuilder.append("Statement for ").append(customerName).append("\n");

        JsonArray performances = invoice.get("performances").getAsJsonArray();
        for (int i = 0; i < performances.size(); i++) {
            JsonObject performance = performances.get(i).getAsJsonObject();
            String playId = performance.get("playID").getAsString();
            int audience = performance.get("audience").getAsInt();

            JsonObject play = plays.get(playId).getAsJsonObject();
            String playType = play.get("type").getAsString();

            PerformanceCalculator calculator = CalculatorFactory.createCalculator(playType, audience);
            int playCost = calculator.getCost();
            int credits = calculator.getVolumeCredits();
            totalCost += playCost;
            totalVolumeCredits += credits;

            resultBuilder.append(generatePerformanceLine(play, audience, playCost));
        }

        resultBuilder.append(generateSummary(totalCost, totalVolumeCredits));
        return resultBuilder.toString();
    }

    /**
     * Erzeugt die Zeile für eine einzelne Performance.
     *
     * @param play     JSON-Objekt mit Spieldaten.
     * @param audience Zuschauerzahl.
     * @param playCost Kosten der Performance in Cent.
     * @return Formatierte Zeile als String.
     */
    private String generatePerformanceLine(JsonObject play, int audience, int playCost) {
        double costInDollars = playCost / 100.0;
        return String.format(Locale.US, "    %s: $%,.2f (%d seats)%n",
                play.get("name").getAsString(), costInDollars, audience);
    }

    /**
     * Erzeugt die Zusammenfassung des Statements.
     *
     * @param totalCost         Gesamtkosten in Cent.
     * @param totalVolumeCredits Gesamte Credits.
     * @return Formatierte Zusammenfassung als String.
     */
    private String generateSummary(int totalCost, int totalVolumeCredits) {
        double totalCostDollars = totalCost / 100.0;
        return String.format(Locale.US, "  Amount owed is $%,.2f%n  You earned %d credits%n",
                totalCostDollars, totalVolumeCredits);
    }
}
