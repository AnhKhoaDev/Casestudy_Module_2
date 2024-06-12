package services.integerCheckService;

import services.productService.ProductService;

public class IntegerCheckService implements IIntegerCheckService {
    private static IntegerCheckService instance;

    private IntegerCheckService() {}

    public static IntegerCheckService getInstance() {
        if(instance == null){
            synchronized (IntegerCheckService.class){
                if(instance == null){
                    instance = new IntegerCheckService();
                }
            }
        }
        return instance;
    }
    @Override
    public boolean integerCheck(String a) {
        try {
            int b = Integer.parseInt(a);
            return true;
        } catch (Exception e) {
            System.err.println("Thông tin nhập không hợp lệ, vui lòng nhập lại: ");
            return false;
        }
    }
}
