import coins.Coin;
import coins.CoinReturn;
import coins.CoinType;
import coins.ITotalCoins;
import drawers.Drawer;
import drawers.DrawerCode;
import products.Product;
//import java.lang.Double;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;

import static java.lang.Double.compare;

public class VendingMachine implements ITotalCoins {

    private ArrayList<Drawer> drawers;
    private ArrayList<Coin> coinsEntered;
    private CoinReturn coinReturn;

    public VendingMachine(CoinReturn coinReturn) {
        this.coinReturn = coinReturn;
        this.drawers = new ArrayList<>();
        this.coinsEntered = new ArrayList<>();
    }

    public ArrayList<Drawer> getDrawers() {
        return drawers;
    }

    public ArrayList<Coin> getCoinsEntered() {
        return coinsEntered;
    }

    public CoinReturn getCoinReturn() {
        return coinReturn;
    }

    public void setCoinReturn(CoinReturn coinReturn) {
        this.coinReturn = coinReturn;
    }
    public void addCoin(Coin coin){
        coinsEntered.add(coin);

    }

    public double calculateCoinsTotal(){
        double total = 0;
        for(Coin coin: coinsEntered){
            total = Double.sum(total, coin.getCoinValue());
        }
        return total;
    }

    public boolean checkCoinValid(Coin coin){
        return coin.getCoinType().isValid();
    }

    public void insertCoin(Coin coin){
        if(checkCoinValid(coin)){
            addCoin(coin);
        }else{
            getCoinReturn().addCoin(coin);
        }
    }

    public double getPriceFromCode(DrawerCode code){
        for(Drawer drawer: drawers){
            if(drawer.getCode() == code){
                return drawer.getPrice();
            }
        }
        return 0;
    }

    public Drawer getDrawerFromCode(DrawerCode code){

        for(Drawer drawer: drawers){
            if(drawer.getCode() == code){
                return drawer;
            }
        }
        return null;
    }

    public BigDecimal calculateChangeToReturn(double price){
        MathContext m = new MathContext(3);
        BigDecimal productPrice = BigDecimal.valueOf(price);
        BigDecimal vendingMachineCredit = BigDecimal.valueOf(calculateCoinsTotal());
        return vendingMachineCredit.subtract(productPrice).round(m);
    }

    public void allocateCoinsToReturn(BigDecimal change){
        MathContext m = new MathContext(3);
        Coin coin;
        BigDecimal decimalChange = change;
        BigDecimal zero = BigDecimal.valueOf(0.0);
        while (decimalChange.compareTo(zero) > 0){
            System.out.println("change = " + decimalChange);
            for (CoinType coinType : CoinType.values()){
                if (coinType.isValid()) {
                    System.out.println("Coins checking: " + coinType);
                    double value = coinType.getValue();
                    BigDecimal decimalValue = BigDecimal.valueOf(value);
                    System.out.println("coin value: " + decimalValue);
                    if (decimalChange.compareTo(decimalValue) > 0) {
                        coin = new Coin(coinType);
                        coinReturn.addCoin(coin);
                        System.out.println("Change allocated = " + coinReturn.calculateCoinsTotal());
                        decimalChange = decimalChange.subtract(decimalValue).round(m);
                        System.out.println("Change to allocate = " + decimalChange);

                    }
                }
            }
            if (decimalChange.compareTo(zero) == 0) {
                break;
            }
        }
    }

    public Product buyProduct(DrawerCode code){
        Drawer drawer = getDrawerFromCode(code);
        double price = getPriceFromCode(code);
        if(Double.compare(calculateCoinsTotal(), price) >= 0 && price > 0){ //
            BigDecimal change = (calculateChangeToReturn(price));
            if(Double.compare(calculateCoinsTotal(), price) > 0) {
                allocateCoinsToReturn(change);
            }
            coinsEntered.clear();
            return drawer.removeProduct();
        } else{
            System.out.println("Please insert more coins to the value of GBP " + (price-calculateCoinsTotal()));
            return null;
        }



    }

}
