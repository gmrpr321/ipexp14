import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.util.List;

public class OrderClient {
    public static void main(String[] args) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080/myapp/orders/user/1");
        List<Order> orders = target.request(MediaType.APPLICATION_JSON).get(new GenericType<List<Order>>() {});
        for (Order order : orders) {
            System.out.println(order);
        }
    }
}
