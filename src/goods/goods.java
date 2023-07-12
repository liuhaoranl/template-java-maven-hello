package goods;

public class goods {
    private String name;//商品名
    private int price;//价格
    private int number;//数量
    private String type;//类型
     
 
    public goods(String name, int price, int number, String type) {//构造方法
        this.name = name;
        this.price = price;
        this.number = number;
        this.type = type;
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public String getNumber() {
        return number;
    }
 
    public void getNumber(String number) {
        this.number = number;
    }
 
    public int getPrice() {
        return price;
    }
 
    public void setPrice(int price) {
        this.price = price;
    }
 
    public String getType() {
        return type;
    }
 
    public void setType(String type) {
        this.type = type;
    }
 
    
 
    @Override
    public String toString() {
        return "goods{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", type='" + type + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
 
}