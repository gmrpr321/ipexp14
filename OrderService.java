import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Path("/orders")
public class OrderService {

    @GET
    @Path("/user/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Order> getUserOrders(@PathParam("userId") int userId) {
        List<Order> orders = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydatabase", "username", "password");
            String sql = "SELECT * FROM orders o JOIN products p ON o.product_id = p.id WHERE user_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setUserId(rs.getInt("user_id"));
                order.setProductId(rs.getInt("product_id"));
                order.setProduct(new Product(rs.getInt("product_id"), rs.getString("product_name"), rs.getDouble("product_price")));
                orders.add(order);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }
}