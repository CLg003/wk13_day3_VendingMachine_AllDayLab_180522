package coins;

public enum CoinType {

    P200("£2",2, true),
    P100("£1",1, true),
    P50("50p",0.50, true),
    P20("20p",0.20, true),
    P10("10p",0.10, true),
    P5("5p",0.05, true),
    P2("2p",0.02, false),
    P1("1p",0.01, false);


    private final String name;
    private final double value;
    private final boolean valid;

    CoinType(String name, double value, boolean valid) {
        this.name = name;
        this.value = value;
        this.valid = valid;
    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }

    public boolean isValid() {
        return valid;
    }

}
