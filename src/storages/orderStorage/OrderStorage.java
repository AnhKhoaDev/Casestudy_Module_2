package storages.orderStorage;

import models.Cart;
import models.Order;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class OrderStorage implements IOrderStorage {
    private static OrderStorage instance;

    private OrderStorage() {}

    public static OrderStorage getInstance() {
        if(instance == null) {
            synchronized (OrderStorage.class){
                if(instance == null) {
                    instance = new OrderStorage();
                }
            }
        }
        return instance;
    }

    private static final File file = new File("order.csv");
    @Override
    public void writeFile(List<Order> orderList) {

        try {
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for (Order order : orderList) {
                for (Cart cart : order.getBoughtList()) {
                    StringBuilder cartString = new StringBuilder();
                    cartString.append(cart.getNow()).append(",")
                            .append(cart.getIdCart()).append(",")
                            .append(cart.getNameCart()).append(",")
                            .append(cart.getQuantity()).append(",")
                            .append(cart.getUnitPrice()).append(",")
                            .append(cart.getTotalAmount()).append(",")
                            .append(cart.getCategory()).append(",")
                            .append(cart.getUsername()).append("\n");
                    bufferedWriter.append(cartString.toString());
                }
            }
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Order> readFile() {
        List<Order> orderList = new ArrayList<>();
        String line;
        String now = "";
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            List<Cart> boughtList = new ArrayList<>();
            String userName = "";
            while ((line = bufferedReader.readLine()) != null) {
                String[] orderString = line.split(",");
                now =  orderString[0];
                int id  = Integer.parseInt(orderString[1]);
                String productName = orderString[2];
                int quantity = Integer.parseInt(orderString[3]);
                double price = Double.parseDouble(orderString[4]);;
                double totalAmount = Double.parseDouble(orderString[5]);;
                String category = orderString[6];
                userName = orderString[7];
                Cart cart = new Cart(now,id,productName, quantity, price, category,userName);
                boughtList.add(cart);
            }
            Order order = new Order(now, userName, boughtList);
            orderList.add(order);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return orderList;
    }
}

