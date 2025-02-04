package src.main.java.calculators;

public class ComedyCalculator implements PerformanceCalculator {
    private final int audience;

    public ComedyCalculator(int audience) {
        this.audience = audience;
    }

    @Override
    public int getCost() {
        int result = 30000;
        if (audience > 20) {
            result += 10000 + 500 * (audience - 20);
        }
        result += 300 * audience;
        return result;
    }

    @Override
    public int getVolumeCredits() {
        // Vergibt 1 Credit pro Zuschauer über 30 und zusätzlich 1 Extra-Credit für jeweils 5 Zuschauer.
        return Math.max(audience - 30, 0) + (int) Math.floor(audience / 5.0);
    }
}