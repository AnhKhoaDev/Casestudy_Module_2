package services.voucherService;

import models.Voucher;
import services.userService.UserService;
import storages.userStorage.UserStorage;
import storages.voucherStorage.VoucherStorage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class VoucherService implements IVoucherService {
    Scanner scanner = new Scanner(System.in);
    private static final List<Voucher> voucherList = VoucherStorage.getInstance().readFile();
    private static VoucherService instance;

    private VoucherService() {}

    public static VoucherService getInstance() {
        if(instance == null){
            synchronized (VoucherService.class){
                if(instance == null){
                    instance = new VoucherService();
                }
            }
        }
        return instance;
    }

    @Override
    public void addVoucher() {
        System.out.println("Nhập số tiền: ");
        double cost = Double.parseDouble(scanner.nextLine());
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formattedDateTime = now.format(formatter);
        String idVoucher = "KS" + formattedDateTime;

        Voucher voucher= new Voucher(idVoucher, cost, true);
        voucherList.add(voucher);
        VoucherStorage.getInstance().writeFile(voucherList);
        System.out.println(voucher.getIdVoucher() + "được tạo thành công");
    }

    @Override
    public void disableVoucher(String voucherId) {
        for (Voucher voucher : voucherList) {
            if(voucher.getIdVoucher().equals(voucherId)) {
                voucher.setAvailable(!voucher.isAvailable());
                break;
            }
        }
        VoucherStorage.getInstance().writeFile(voucherList);
    }
}
