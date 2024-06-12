package storages.cartStorage;

import models.Cart;
import models.Product;
import storages.productStorage.ProductStorage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CartStorage implements ICartStorage {
    private static CartStorage instance;

    private CartStorage() {}

    public static CartStorage getInstance() {
        if(instance == null) {
            synchronized (CartStorage.class){
                if(instance == null) {
                    instance = new CartStorage();
                }
            }
        }
        return instance;
    }

    private static final File file = new File("cart.csv");
    @Override
    public void writeFile(List<Cart> cartList) {
        try {
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            StringBuilder cartString = new StringBuilder();
            for(Cart cart : cartList){
                cartString.append(cart.getIdCart()).append(",")
                        .append(cart.getNameCart()).append(",")
                        .append(cart.getQuantity()).append(",")
                        .append(cart.getUnitPrice()).append(",")
                        .append(cart.getCategory()).append(",")
                        .append(cart.getUsername()).append("\n");
            }
            bufferedWriter.write(cartString.toString());
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Cart> readFile() {
        List<Cart> cartList = new ArrayList<>();
        String line;
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while ((line = bufferedReader.readLine()) != null) {
                String[] cartString = line.split(",");
                int id = cartString[0].isEmpty() ? 0 : Integer.parseInt(cartString[0]);
                String name = cartString[1];
                int quantity = cartString[0].isEmpty() ? 0 : Integer.parseInt(cartString[2]);
                Double price = cartString[0].isEmpty() ? 0 : Double.parseDouble(cartString[3]);
                String category = cartString[4];
                String username = cartString[5];
                Cart cart = new Cart(id, name, quantity, price, category, username);
                cartList.add(cart);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return cartList;
    }
}
