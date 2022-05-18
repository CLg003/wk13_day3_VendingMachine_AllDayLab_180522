import coins.Coin;
import coins.CoinReturn;
import coins.CoinType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CoinReturnTest {

    CoinReturn coinReturn;
    Coin coin1;
    Coin coin2;

    @Before
    public void before(){
        coinReturn = new CoinReturn();
        coin1 = new Coin(CoinType.P5);
        coin2 = new Coin(CoinType.P100);
    }

    @Test
    public void hasAnEmptyCoinsReturned(){
        assertEquals(0, coinReturn.getCoins().size());
    }

    @Test
    public void canAddCoin(){
        coinReturn.addCoin(coin1);
        assertEquals(1, coinReturn.getCoins().size());
    }

    @Test
    public void canCalculateCoinsTotal(){
        coinReturn.addCoin(coin1);
        coinReturn.addCoin(coin1);
        coinReturn.addCoin(coin2);
        coinReturn.addCoin(coin2);
        assertEquals(2.10, coinReturn.calculateCoinsTotal(), 0.0);


    }



}


