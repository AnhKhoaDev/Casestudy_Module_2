package storages.voucherStorage;

import models.Voucher;
import storages.cartStorage.CartStorage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class VoucherStorage implements IVoucherStorage {
    private static VoucherStorage instance;

    private VoucherStorage() {}

    public static VoucherStorage getInstance() {
        if(instance == null) {
            synchronized (VoucherStorage.class){
                if(instance == null) {
                    instance = new VoucherStorage();
                }
            }
        }
        return instance;
    }

    private static final File file = new File("voucher.csv");
    @Override
    public void writeFile(List<Voucher> voucherList) {
        try {
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            StringBuilder voucherString = new StringBuilder();
            for(Voucher voucher : voucherList){
                voucherString.append(voucher.getIdVoucher()).append(",")
                        .append(voucher.getCost()).append(",")
                        .append(voucher.isAvailable()).append("\n");
            }
            bufferedWriter.write(voucherString.toString());
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Voucher> readFile() {
        List<Voucher> voucherList = new ArrayList<>();
        String line;
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while ((line = bufferedReader.readLine()) != null) {
                String[] voucherString = line.split(",");
                String id = voucherString[0];
                double cost = Double.parseDouble(voucherString[1]);
                boolean available = Boolean.parseBoolean(voucherString[2]);
                Voucher newCode = new Voucher(id,cost,available);
                voucherList.add(newCode);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return voucherList;
    }
}
