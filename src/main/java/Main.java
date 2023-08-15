import org.apache.commons.io.IOUtils;

import javax.swing.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Main {
    private static int exceptycount;
    private static LinkedHashMap<String, ArrayList<Product>> data=new LinkedHashMap<>();

    public String readRawDataToString() throws Exception{
        ClassLoader classLoader = getClass().getClassLoader();
        String result = IOUtils.toString(classLoader.getResourceAsStream("RawData.txt"));
        //whenever 2 ps the second ones always capitalized
        return result;
    }
    public ArrayList<Product> deparseify(String input) throws ParseException {
        String name;
        Double price = null;
        String type;
        Date date ;
        ArrayList<Product> productArrayList=new ArrayList<>();
        String[] products= input.split("##");
        for (String i:products) {
            if (i.length()>1) {
                String[] productData = i.split("[;:@^%*!]");
                    name = (productData[1]);
                    try {
                        price = Double.parseDouble(productData[3]);
                    }
                    catch (NumberFormatException e){
                        price=0.0;
                        exceptycount++;
                    }
                    type = (productData[5]);
                    date = new SimpleDateFormat("MM/dd/yyyy").parse(productData[7]);
                productArrayList.add(new Product(name, price, type, date));
            }
        }

        return productArrayList;

    }
    public ArrayList<Product> nameSorter(ArrayList<Product> productArrayList){
        ArrayList<Product> bread=new ArrayList<>();
        ArrayList<Product> cookies=new ArrayList<>();
        ArrayList<Product> milk=new ArrayList<>();
        ArrayList<Product> apples=new ArrayList<>();
        ArrayList<Product> sorted =new ArrayList<>();
            for (Product j:productArrayList) {
                try {
                    char firstCharacter = j.getName().charAt(0);
                    if (firstCharacter == 'a') {
                        apples.add(j);
                    }
                    if (firstCharacter == 'B') {
                        bread.add(j);
                    }
                    if (firstCharacter == 'M') {
                        milk.add(j);
                    }
                    if (firstCharacter == 'C') {
                        cookies.add(j);
                    }
                }
                catch (StringIndexOutOfBoundsException e){
                    exceptycount++;
                }
            }
            sorted.addAll(apples);
            data.put("Apples",apples);
            sorted.addAll(bread);
            data.put("Bread",bread);
            sorted.addAll(milk);
            data.put("Milk",milk);
            sorted.addAll(cookies);
            data.put("Cookies",cookies);
            return sorted;
    }
    public String finalParse(){
        // Create a HashMap to store groups of products based on price
        HashMap<Double, ArrayList<Product>> groupedProducts = new HashMap<>();

        groupedProducts("Milk");
        groupedProducts("Bread");
        groupedProducts("Cookies");
        groupedProducts("Apples");
        return null;

    }
    public void groupedProducts(String input){
        int count=0;
        int thing=0;
        HashMap<Double, ArrayList<Product>> groupedProducts = new HashMap<>();
        for (Product product : data.get(input)) {
            count++;
            if (!groupedProducts.containsKey(product.getPrice())) {
                groupedProducts.put(product.getPrice(), new ArrayList<Product>());
            }
            groupedProducts.get(product.getPrice()).add(product);
        }
        System.out.println(outputBuilder(input,count));
        for (Double price : groupedProducts.keySet()) {
            int pricecount=0;

            for (Product product : groupedProducts.get(price)) {
                pricecount++;
            }
            if(thing==0) {
                System.out.println(outputBuilderTwo(price, pricecount));
            }
            if(thing > 0){
                System.out.println(outputBuilderThree(price, pricecount));
            }
            thing++;
        }
        System.out.println("\n");

    }


    public String outputBuilder(String name,int seen){
        return "name:    " + name + " \t\t seen: " + seen + " times\n" +
                "============= \t \t =============";
    }

    public String outputBuilderTwo(double price,int seenPrice){
        return "Price: \t " + price + "\t\t seen: " + seenPrice + " times"+
        "\n-------------\t\t -------------";
    }
    public String outputBuilderThree(double price,int seenPrice){
        return "Price: \t " + price + "\t\t seen: " + seenPrice + " times";
    }
}
