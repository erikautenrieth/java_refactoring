package src.main.java.calculators;

public class TragedyCalculator implements PerformanceCalculator {
    private final int audience;

    public TragedyCalculator(int audience) {
        this.audience = audience;
    }

    @Override
    public int getCost() {
        int result = 40000;
        if (audience > 30) {
            result += 1000 * (audience - 30);
        }
        return result;
    }

    @Override
    public int getVolumeCredits() {
        // Vergibt 1 Credit pro Zuschauer über 30.
        return Math.max(audience - 30, 0);
    }
}