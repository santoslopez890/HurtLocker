import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProductTest {
    @Test
    public void productTestBurger() throws ParseException {
        //given
        String nameExpected="Burger";
        Double price=8.50;
        String type= "Food";
        Date expiration=new SimpleDateFormat( "yyyyMMdd" ).parse( "20100520" );
        //when
        Product burger=new Product(nameExpected,price,type,expiration);
        //then
        Assertions.assertEquals(nameExpected,burger.getName());
        Assertions.assertEquals(price,burger.getPrice());
        Assertions.assertEquals(type,burger.getType());
        Assertions.assertEquals(expiration,burger.getExpiration());
    }

}