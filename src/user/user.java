

public class user {
    
    private String name;//用户名
    private String password;//密码
 
    public user(String name, String password) {//构造方法
        this.name = name;
        this.password= password;
      
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void getPassword(String password) {
        this.v = password;
    }
 
 
    @Override
    public String toString() {
        return "user{" +
                "name='" + name + '\'' +
                ", password=" + password +
                '}';
    }
 
}
