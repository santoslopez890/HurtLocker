import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;

public class ProductTest {
    private static LinkedHashMap<String, ArrayList<Product>> data ;
    @Before
    public void setup(){
        data=new LinkedHashMap<>();
    }
    @Test
    public void productTestBurger() throws ParseException {
        //given
        String nameExpected="Burger";
        Double price=8.50;
        String type= "Food";
        Date expiration=new SimpleDateFormat( "yyyyMMdd" ).parse( "20100520" );
        //when
        Product burger=new Product();
        burger.setName(nameExpected);
        burger.setPrice(price);
        burger.setType(type);
        burger.setExpiration(expiration);
        //then
        Assertions.assertEquals(nameExpected,burger.getName());
        Assertions.assertEquals(price,burger.getPrice());
        Assertions.assertEquals(type,burger.getType());
        Assertions.assertEquals(expiration,burger.getExpiration());
    }
    @Test
    public void deparsify() throws Exception {
        //Given
       String expected="Product{name='BreaD', price=1.23, type='Food', expiration=Sat Jan 02 00:00:00 EST 2016}";
        HurtLocker test=new HurtLocker();
        String output=test.readRawDataToString();
        ArrayList <Product> deparsedOutput=test.deparseify(output);
        Product bread=deparsedOutput.get(1);
        //When
        String actual =bread.toString();
        Assert.assertEquals(actual,expected);
    }
    @Test
    public void wordSorter() throws Exception {
        //Given
        String expected="Product{name='BrEAD', price=1.23, type='Food', expiration=Mon Jan 25 00:00:00 EST 2016}";
        HurtLocker test=new HurtLocker();
        String output=test.readRawDataToString();
        ArrayList <Product> deparsedOutput=test.deparseify(output);
//        ArrayList <Product> sorted=test.nameSorter(deparsedOutput);
//        Product bread=sorted.get(6);
//        //When
//        String actual =bread.toString();
//        Assert.assertEquals(actual,expected);
    }

    @Test
    public void outputBuilder(){
        //given
        HurtLocker test=new HurtLocker();
        String expected=
                "name: Cookies \t\t seen: 4 times\n" +
                "============= \t \t =============\n";
        //when
        String actual = test.outputBuilder("Cookies",4);
        //Then
        Assert.assertEquals(expected,actual);
    }
    @Test
    public void outputBuilderTwo(){
        //given
        HurtLocker test=new HurtLocker();
        String expected= "Price: \t 2.25\t\t seen: 3 times\n" +"-------------\t\t -------------\n";
        //when
        String actual = test.outputBuilderTwo(2.25,3);
        //Then
        Assert.assertEquals(expected,actual);
    }
    @Test
    public void outputBuilderThree(){
        //given
        HurtLocker test=new HurtLocker();
        String expected= "Price: \t 2.25\t\t seen: 3 times\n";
        //when
        String actual = test.outputBuilderThree(2.25,3);
        //Then
        Assert.assertEquals(expected,actual);
    }
    @Test
    public void parseData() throws Exception {
        HurtLocker test=new HurtLocker();
        String output=test.readRawDataToString();
        ArrayList <Product> deparsedOutput=test.deparseify(output);
        test.nameSorter(deparsedOutput,data);
        System.out.println(test.finalParse(data));

    }
    @Test
    public void parseDataDoneInOneMethod(){
        String actual="";
        String expected="name:    Milk \t\t seen: 6 times\n" +
                "============= \t \t =============\n" +
                "Price: \t 3.23\t\t seen: 5 times\n" +
                "-------------\t\t -------------\n" +
                "Price:   1.23\t\t seen: 1 time\n" +
                "\n" +
                "name:   Bread\t\t seen: 6 times\n" +
                "=============\t\t =============\n" +
                "Price:   1.23\t\t seen: 6 times\n" +
                "-------------\t\t -------------\n" +
                "\n" +
                "name: Cookies     \t seen: 8 times\n" +
                "=============     \t =============\n" +
                "Price:   2.25        seen: 8 times\n" +
                "-------------        -------------\n" +
                "\n" +
                "name:  Apples     \t seen: 4 times\n" +
                "=============     \t =============\n" +
                "Price:   0.25     \t seen: 2 times\n" +
                "-------------     \t -------------\n" +
                "Price:   0.23  \t \t seen: 2 times\n" +
                "\n" +
                "Errors         \t \t seen: 4 times";
        HurtLocker test=new HurtLocker();
        try {
            actual=test.runAllParsers();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Assert.assertEquals(expected,actual);
    }

}