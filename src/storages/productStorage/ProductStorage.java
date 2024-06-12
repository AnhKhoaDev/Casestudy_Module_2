package storages.productStorage;

import models.Product;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProductStorage implements IProductStorage {
    private static ProductStorage instance;

    private ProductStorage() {}

    public static ProductStorage getInstance() {
        if(instance == null) {
            synchronized (ProductStorage.class){
                if(instance == null) {
                    instance = new ProductStorage();
                }
            }
        }
        return instance;
    }

    private static final File file = new File("product.csv");
    @Override
    public void writeFile(List<Product> productList) {
        try{
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            StringBuilder productString = new StringBuilder();
            for (Product product : productList) {
                productString.append(product.getIdProduct()).append(",")
                        .append(product.getNameProduct()).append(",")
                        .append(product.getQuantity()).append(",")
                        .append(product.getPrice()).append(",")
                        .append(product.getCategory()).append("\n");
            }
            bufferedWriter.write(productString.toString());
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> readFile() {
        List<Product> productList = new ArrayList<>();
        String line;
        try{
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while ((line = bufferedReader.readLine()) != null){
                String[] productString = line.split(",");
                int idProduct = Integer.parseInt(productString[0]);
                String nameProduct = productString[1];
                int quantity = Integer.parseInt(productString[2]);
                double price = Double.parseDouble(productString[3]);
                String category = productString[4];
                Product product = new Product(idProduct, nameProduct, quantity, price, category);
                productList.add(product);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return productList;
    }
}
