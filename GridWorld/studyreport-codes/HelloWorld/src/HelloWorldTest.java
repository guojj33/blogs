import helloWorld.HelloWorld;
import static org.junit.Assert.*;
import org.junit.Test;

public class HelloWorldTest {
    @Test
    public void testGetHelloWorld() {
        assertEquals("Hello World!", new HelloWorld().getHelloWorld());
    }
}
