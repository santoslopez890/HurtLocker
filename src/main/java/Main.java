import org.apache.commons.io.IOUtils;

import javax.swing.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Main {

    public String readRawDataToString() throws Exception{
        ClassLoader classLoader = getClass().getClassLoader();
        String result = IOUtils.toString(classLoader.getResourceAsStream("RawData.txt"));
        //whenever 2 ps the second ones always capitalized



        return result;
    }
    public static ArrayList<Product> deparseify(String input) throws ParseException {
        String name;
        Double price;
        String type;
        Date date = null;

        ArrayList<Product> productArrayList=new ArrayList<>();
        String[] products= input.split("##");
        for (String i:products) {
            if (i.length()>1) {
                String[] productData = i.split("[;:@^%*!]");
                    name = (productData[1]);
                if (!productData[3].isEmpty()) {
                    price = Double.parseDouble(productData[3]);
                } else {
                    price = 0.0;
                }
                    type = (productData[5]);
                    date = new SimpleDateFormat("MM/dd/yyyy").parse(productData[7]);
                productArrayList.add(new Product(name, price, type, date));
            }
        }
        return productArrayList;
    }

    public static void main(String[] args) throws Exception{
        String output = (new Main()).readRawDataToString();
        deparseify(output);
        System.out.println(output);

        for (Product p : deparseify(output)) {
            System.out.println(p);
        }
    }
}
