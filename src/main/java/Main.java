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
    private static LinkedHashMap<String, ArrayList<Product>> data = new LinkedHashMap<>();

    public String readRawDataToString() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        String result = IOUtils.toString(classLoader.getResourceAsStream("RawData.txt"));
        //whenever 2 ps the second ones always capitalized
        return result;
    }

    public ArrayList<Product> deparseify(String input) throws ParseException {
        String name;
        Double price = null;
        String type;
        Date date;
        boolean exception = false;
        ArrayList<Product> productArrayList = new ArrayList<>();
        String[] products = input.split("##");
        for (String i : products) {
            if (i.length() > 1) {
                String[] productData = i.split("[;:@^%*!]");
                name = (productData[1]);
                try {
                    price = Double.parseDouble(productData[3]);
                } catch (NumberFormatException e) {
                    price = 0.0;
                    exceptycount++;
                    exception = true;
                }
                type = (productData[5]);
                date = new SimpleDateFormat("MM/dd/yyyy").parse(productData[7]);
                if (exception == false) {
                    productArrayList.add(new Product(name, price, type, date));
                }
                exception = false;
            }
        }
        return productArrayList;
    }

    public void nameSorter(ArrayList<Product> productArrayList) {
        ArrayList<Product> bread = new ArrayList<>();
        ArrayList<Product> cookies = new ArrayList<>();
        ArrayList<Product> milk = new ArrayList<>();
        ArrayList<Product> apples = new ArrayList<>();
        ArrayList<Product> sorted = new ArrayList<>();
        for (Product j : productArrayList) {
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
            } catch (StringIndexOutOfBoundsException e) {
                exceptycount++;
            }
        }
        data.put("Apples", apples);
        data.put("Bread", bread);
        data.put("Milk", milk);
        data.put("Cookies", cookies);
    }

    public String finalParse() {
        // Create a HashMap to store groups of products based on price
        StringBuilder finalString = new StringBuilder();
        HashMap<Double, ArrayList<Product>> groupedProducts = new HashMap<>();
        finalString.append(groupedProducts("Milk") + "\n");
        finalString.append(groupedProducts("Bread") + "\n");
        finalString.append(groupedProducts("Cookies") + "\n");
        finalString.append(groupedProducts("Apples") + "\n");
        finalString.append("Errors         \t \t seen: " + exceptycount + " times");
        return finalString.toString();
    }

    public void runAllParsers() {

    }

    public String groupedProducts(String input) {
        String groupedproduct = null;
        StringBuilder groupString = new StringBuilder();
        int count = 0;
        int thing = 0;
        HashMap<Double, ArrayList<Product>> groupedProducts = new HashMap<>();
        for (Product product : data.get(input)) {
            count++;
            if (!groupedProducts.containsKey(product.getPrice())) {
                groupedProducts.put(product.getPrice(), new ArrayList<Product>());
            }
            groupedProducts.get(product.getPrice()).add(product);
        }
        groupString.append(outputBuilder(input, count));
        for (Double price : groupedProducts.keySet()) {
            int pricecount = 0;
            for (Product product : groupedProducts.get(price)) {
                pricecount++;
            }
            if (thing == 0) {
                groupString.append(outputBuilderTwo(price, pricecount));
            }
            if (thing > 0) {
                groupString.append(outputBuilderThree(price, pricecount));
            }
            thing++;
        }
        return groupString.toString();
    }


    public String outputBuilder(String name, int seen) {
        return "name:    " + name + " \t\t seen: " + seen + " times\n" +
                "============= \t \t =============\n";
    }

    public String outputBuilderTwo(double price, int seenPrice) {
        return "Price: \t " + price + "\t\t seen: " + seenPrice + " times" +
                "\n-------------\t\t -------------\n";
    }

    public String outputBuilderThree(double price, int seenPrice) {
        return "Price: \t " + price + "\t\t seen: " + seenPrice + " times\n";
    }
}

