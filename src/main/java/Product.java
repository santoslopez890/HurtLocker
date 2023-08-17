import java.util.Date;

public class Product {
    private String name;
    private Double price;
    private String type;
    private Date expiration;
    public Product() {
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Date getExpiration() {
        return expiration;
    }
    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }
    public Product(String name, double price, String type, Date expiration) {
        this.name = name;
        this.price = price;
        this.type = type;
        this.expiration = expiration;
    }
    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", type='" + type + '\'' +
                ", expiration=" + expiration +
                '}';
    }
}
