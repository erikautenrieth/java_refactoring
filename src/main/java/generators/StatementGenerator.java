package src.main.java.generators;

import src.main.java.calculators.CalculatorFactory;
import src.main.java.calculators.PerformanceCalculator;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.Locale;

public class StatementGenerator {

    /**
     * Erzeugt ein Abrechnungsstatement für einen Kunden.
     *
     * @param invoices JSON-Array mit Rechnungsinformationen (Kunde, Aufführungen und Zuschaueranzahl).
     * @param plays    JSON-Objekt mit Daten zu jedem Stück (Name, Typ).
     * @return         Formatiertes Statement als String mit Summen zu Kosten und Credits.
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

            // Erzeugt einen passenden Calculator für das Stück und berechnet Kosten und Credits.
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
     * Formatiert eine einzelne Performance-Zeile mit Stückname, Preis in Dollar und Zuschauerzahl.
     */
    private String generatePerformanceLine(JsonObject play, int audience, int playCost) {
        double costInDollars = playCost / 100.0;
        return String.format(Locale.US, "    %s: $%,.2f (%d seats)%n",
                play.get("name").getAsString(), costInDollars, audience);
    }

    /**
     * Erstellt eine Zusammenfassung mit dem gesamten zu zahlenden Betrag und den erworbenen Credits.
     */
    private String generateSummary(int totalCost, int totalVolumeCredits) {
        double totalCostDollars = totalCost / 100.0;
        return String.format(Locale.US, "  Amount owed is $%,.2f%n  You earned %d credits%n",
                totalCostDollars, totalVolumeCredits);
    }
}
