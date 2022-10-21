package helloWorld;
// import java.util.*;

public class HelloWorld {
    public String getHelloWorld() {
        return "Hello World!";
    }
    
    public static void main(String[] args) {
        HelloWorld hw = new HelloWorld();
        String s = hw.getHelloWorld();
        System.out.println(s);
    }
}
