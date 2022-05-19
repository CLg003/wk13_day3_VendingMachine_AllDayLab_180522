import coins.Coin;
import coins.CoinReturn;
import coins.CoinType;
import drawers.Drawer;
import drawers.DrawerCode;
import org.junit.Before;
import org.junit.Test;
import products.Sweet;
import sun.invoke.empty.Empty;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class VendingMachineTest {

    VendingMachine vendingMachine;
    Coin coin;
    Coin coin2;
    Coin coin3;
    CoinReturn coinReturn;
    Drawer drawer;
    Drawer drawer2;
    Drawer drawer3;

    Sweet sweet;




    @Before
    public void before(){
        coin = new Coin(CoinType.P100);
        coin2 = new Coin(CoinType.P10);
        coin3 = new Coin(CoinType.P1);
        coinReturn = new CoinReturn();
        vendingMachine = new VendingMachine(coinReturn);
        drawer = new Drawer(DrawerCode.A1);
        drawer2 = new Drawer(DrawerCode.B1);
        drawer3 = new Drawer(DrawerCode.C1);
        Collections.addAll(vendingMachine.getDrawers(),drawer, drawer2, drawer3);

        sweet = new Sweet("M&M's", "Mars");

    }
//    NOT VALID SINCE CHANGING @Before METHOD ABOVE
//    @Test
//    public void hasEmptyDrawerArrayList(){
//        assertEquals(0, vendingMachine.getDrawers().size());
//    }

    @Test
    public void hasCoinsEnteredArrayList(){
        assertEquals(0, vendingMachine.getCoinsEntered().size());
    }

    @Test
    public void hasCoinReturned(){
        assertEquals(coinReturn, vendingMachine.getCoinReturn());
    }

    @Test
    public void canAddCoin(){
        vendingMachine.addCoin(coin);
        assertEquals(1, vendingMachine.getCoinsEntered().size());
    }

    @Test
    public void canCalculateCoinsTotal(){
        vendingMachine.addCoin(coin);
        vendingMachine.addCoin(coin2);
        assertEquals(1.1, vendingMachine.calculateCoinsTotal(), 0.0);
    }

    @Test
    public void canCheckIfCoinValidTrue(){
        assertEquals(true, vendingMachine.checkCoinValid(coin));
    }

    @Test
    public void canCheckIfCoinValidFalse(){
        assertEquals(false, vendingMachine.checkCoinValid(coin3));
    }

    @Test
    public void canProcessCoinsInsertedAccepted(){
        vendingMachine.insertCoin(coin);
        assertEquals(1, vendingMachine.getCoinsEntered().size());
        assertEquals(1.00, vendingMachine.calculateCoinsTotal(), 0.0);
    }
    @Test
    public void canProcessCoinsInsertedRejected(){
        vendingMachine.insertCoin(coin3);
        assertEquals(0, vendingMachine.calculateCoinsTotal(), 0.0);
        assertEquals(0.01, vendingMachine.getCoinReturn().calculateCoinsTotal(), 0.0);
    }

    @Test
    public void canGetPriceFromCodeZero(){
        assertEquals(0.0, vendingMachine.getPriceFromCode(DrawerCode.A1),0.0);

    }

    @Test
    public void vendingMachineHas3Drawers(){
        assertEquals(3, vendingMachine.getDrawers().size());
    }

    @Test
    public void canGetPriceFromCode(){
        drawer.addProduct(sweet);
        assertEquals(0.65, vendingMachine.getPriceFromCode(DrawerCode.A1),0.0);
    }

    @Test
    public void canBuySweet(){
        vendingMachine.insertCoin(coin);
        drawer.addProduct(sweet);
        assertEquals(sweet, vendingMachine.buyProduct(DrawerCode.A1));
        assertEquals(0, vendingMachine.calculateCoinsTotal(),0.0);
        assertEquals(0.35, coinReturn.calculateCoinsTotal(), 0.0);
    }

    @Test
    public void canAllocateCoinsForChange(){
        vendingMachine.allocateCoinsToReturn(BigDecimal.valueOf(0.35));
    }

    @Test
    public void canNotBuySweet(){
        vendingMachine.insertCoin(coin2);
        drawer.addProduct(sweet);
        assertEquals(null, vendingMachine.buyProduct(DrawerCode.A1));
    }




}
