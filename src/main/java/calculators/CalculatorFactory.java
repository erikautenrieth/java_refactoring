package main.java.calculators;


public class CalculatorFactory {
    /**
     * Factory zur Erzeugung eines PerformanceCalculators.
     *
     * @param playType Spieltyp (z.B. "tragedy" oder "comedy").
     * @param audience Zuschauerzahl.
     * @return Passender PerformanceCalculator.
     */
    public static PerformanceCalculator createCalculator(String playType, int audience) {
        if ("tragedy".equalsIgnoreCase(playType)) {
            return new TragedyCalculator(audience);
        } else if ("comedy".equalsIgnoreCase(playType)) {
            return new ComedyCalculator(audience);
        } else {
            throw new IllegalArgumentException("Unknown play type: " + playType);
        }
    }
}