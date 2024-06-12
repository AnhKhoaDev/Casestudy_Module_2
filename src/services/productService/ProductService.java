package services.productService;

import models.Cart;
import models.Product;
import storages.productStorage.ProductStorage;

import java.util.*;

public class ProductService implements IProductService {
    Scanner sc = new Scanner(System.in);
    private static final List<Product> productList = ProductStorage.getInstance().readFile();
    private static ProductService instance;

    private ProductService() {}

    public static ProductService getInstance() {
        if(instance == null){
            synchronized (ProductService.class){
                if(instance == null){
                    instance = new ProductService();
                }
            }
        }
        return instance;
    }

    @Override
    public void addProduct() throws InterruptedException {
        int idProduct = productList.size() + 1;
        System.out.println("====Add product====");
        for (int i = 0; i < productList.size(); i++) {
            if (i + 1 != productList.get(i).getIdProduct()) {
                idProduct = i + 1;
            }
        }
        System.out.println("Tên sản phẩm: ");
        String nameProduct = sc.nextLine();
        System.out.println("Số lượng sản phẩm: ");
        int priceProduct = Integer.parseInt(sc.nextLine());
        System.out.println("Giá của sản phẩm: ");
        double price = Double.parseDouble(sc.nextLine());
        System.out.println("Loại sản phẩm: ");
        String category = sc.nextLine();

        Product product = new Product(idProduct, nameProduct, priceProduct, price, category);
        productList.add(product);
        ProductStorage.getInstance().writeFile(productList);
        System.out.println("Sản pẩm được thêm thành công");
    }

    @Override
    public void editProduct() throws InterruptedException {
        ProductService productService = new ProductService();
        productService.showAllProducts();
        System.out.println("Enter id of product to edit");
        String idEdit = sc.nextLine();
        boolean isMatch = false;
        for(Product product : productList){
            if( idEdit.equals(product.getIdProduct()+ " ") ){
                isMatch = true;
                System.out.println("1.Tên sản phẩm");
                System.out.println("2.Số lượng sản phẩm");
                System.out.println("3. Giá sản phẩm");
                System.out.println("4.Loại sản phẩm");
                System.out.println("Lựa chọn chức năng: ");
                String choice = sc.nextLine();
                switch (choice){
                    case "1" :
                        System.out.println("Tên sản phẩm: ");
                        String newName = sc.nextLine();
                        product.setNameProduct(newName);
                        System.out.println("Tên sản phẩm đã được cập nhật");
                        ProductStorage.getInstance().writeFile(productList);
                        break;
                    case "2":
                        System.out.println("Số lượng sản phẩm: ");
                        int newQuantity = sc.nextInt();
                        sc.nextLine();
                        product.setQuantity(newQuantity);
                        System.out.println("Số lượng sản phẩm đã được cập nhật");
                        ProductStorage.getInstance().writeFile(productList);
                        break;
                    case "3":
                        System.out.println("Giá sản phẩm: ");
                        double newPrice = sc.nextDouble();
                        sc.nextLine();
                        product.setPrice(newPrice);
                        System.out.println("Giá sản phẩm đã được cập nhật");
                        ProductStorage.getInstance().writeFile(productList);
                        break;
                    case "4":
                        System.out.println("Loại sản phẩm: ");
                        String newType = sc.nextLine();
                        product.setCategory(newType);
                        System.out.println("Loại sản phẩm đã được cập nhật");
                        ProductStorage.getInstance().writeFile(productList);
                        break;
                    default:
                        System.err.println("Mời bạn chọn lại chức năng");;
                }
                break;
            }
        }
        if(!isMatch){
            System.err.println("Không tìm thấy sản phẩm!");
        }
    }

    @Override
    public void deleteProduct() throws InterruptedException {
        System.out.println("Nhập mã sản phẩm: ");
        int idProduct = Integer.parseInt(sc.nextLine());
        productList.remove(idProduct - 1);
        ProductStorage.getInstance().writeFile(productList);
        System.out.println("Sản phẩm được xoá thành công");
    }

    @Override
    public void findByName() throws InterruptedException {
        System.out.println("Nhập tên sản phẩm: ");
        String nameProduct = sc.nextLine();
        boolean isExists = false;
        System.out.println("Số thứ tự  Tên sản phẩm    Số lượng  Giá   Loại");
        System.out.println("---------------------------------------------------");
        for (Product product : productList) {
            if (product.getNameProduct().trim().equalsIgnoreCase(nameProduct.trim())) {
                String formattedString = String.format("%-7d %-16s %-9d %.2f   %s",
                        product.getIdProduct(),
                        product.getNameProduct(),
                        product.getQuantity(),
                        product.getPrice(),
                        product.getCategory());
                System.out.println(formattedString);
                isExists = true;
            }
        }
        if (!isExists) {
            System.err.println("Không tìm thấy sản phẩm!");
        }
    }

    @Override
    public void findByCategory() throws InterruptedException {
        List<String> categoryList = new ArrayList<>();
        Map<String, String> categoryMap = new HashMap<>();
        for (Product product : productList) {
            boolean isExists = false;
            String category = product.getCategory();
            for (String stringCategory : categoryList) {
                if (stringCategory.equals(category)) {
                    isExists = true;
                }
            }
            if (!isExists) {
                categoryList.add(category);
            }
        }
        System.out.println("Loại sản phẩm bạn muốn tìm: ");
        for (String category : categoryList) {
            int number = categoryList.indexOf(category) + 1;
            String key = "" + number;
            categoryMap.put(key, category);
            System.out.println(key + ". " + categoryMap.get(key));
        }
        System.out.println("Lựa chọn chức năng: ");
        String choose = sc.nextLine();
        System.out.println("Số thứ tự  Tên sản phẩm    Số lượng  Giá   Loại");
        System.out.println("---------------------------------------------------");
        boolean isMatch = false;
        for (Product product : productList) {
            if (product.getCategory().equals(categoryMap.get(choose))) {
                isMatch = true;
                String formattedString = String.format("%-7d %-16s %-9d %.2f   %s",
                        product.getIdProduct(),
                        product.getNameProduct(),
                        product.getQuantity(),
                        product.getPrice(),
                        product.getCategory());
                System.out.println(formattedString);
            }
            if (isMatch) {
                System.out.println("________________________________________________");
            } else {
                System.err.println("Không tìm thấy sản phẩm");
            }
        }
    }

    @Override
    public void showAllProducts() throws InterruptedException {
        sortById();
    }

    @Override
    public void editWhenBuy(List<Cart> cartList) throws InterruptedException {
        for (Cart cart : cartList) {
            for (Product product : productList) {
                if (cart.getNameCart().equals(product.getNameProduct())) {
                    product.setQuantity(product.getQuantity() - cart.getQuantity());
                }
            }
        }
        ProductStorage.getInstance().writeFile(productList);
    }

    @Override
    public void sortPriceLowToHigh() throws InterruptedException {
        for (int i = 0; i < productList.size(); i++) {
            for (int j = 0; j < productList.size(); j++) {
                if (productList.get(i).getPrice() < productList.get(j).getPrice()) {
                    Product temp = productList.get(i);
                    productList.set(i, productList.get(j));
                    productList.set(j, temp);
                }
            }
        }
        System.out.println("__________________________________________________");
        System.out.println("Số thứ tự  Tên sản phẩm    Số lượng  Giá   Loại");
        System.out.println("---------------------------------------------------");
        for (Product product : productList) {
            String formattedString = String.format("%-7d %-16s %-9d %.2f   %s",
                    product.getIdProduct(),
                    product.getNameProduct(),
                    product.getQuantity(),
                    product.getPrice(),
                    product.getCategory());
            System.out.println(formattedString);
        }
        System.out.println("________________________________________________");
    }

    @Override
    public void sortPriceHighToLow() throws InterruptedException {
        for (int i = 0; i < productList.size(); i++) {
            for (int j = 0; j < productList.size(); j++) {
                if (productList.get(i).getPrice() > productList.get(j).getPrice()) {
                    Product temp = productList.get(i);
                    productList.set(i, productList.get(j));
                    productList.set(j, temp);
                }
            }
        }
        System.out.println("__________________________________________________");
        System.out.println("Số thứ tự  Tên sản phẩm    Số lượng  Giá   Loại");
        System.out.println("---------------------------------------------------");
        for (Product product : productList) {
            String formattedString = String.format("%-7d %-16s %-9d %.2f   %s",
                    product.getIdProduct(),
                    product.getNameProduct(),
                    product.getQuantity(),
                    product.getPrice(),
                    product.getCategory());
            System.out.println(formattedString);
        }
        System.out.println("________________________________________________");
    }

    @Override
    public void sortById() throws InterruptedException {
        for (int i = 0; i < productList.size(); i++) {
            for (int j = 0; j < productList.size(); j++) {
                if (productList.get(i).getIdProduct() < productList.get(j).getIdProduct()) {
                    Product temp = productList.get(i);
                    productList.set(i, productList.get(i));
                    productList.set(j, temp);
                }
            }
        }
        System.out.println("__________________________________________________");
        System.out.println("Số thứ tự  Tên sản phẩm    Số lượng  Giá   Loại");
        System.out.println("---------------------------------------------------");
        for (Product product : productList) {
            String formattedString = String.format("%-7d %-16s %-9d %.2f   %s",
                    product.getIdProduct(),
                    product.getNameProduct(),
                    product.getQuantity(),
                    product.getPrice(),
                    product.getCategory());
            System.out.println(formattedString);
        }
        System.out.println("________________________________________________");
    }
}
