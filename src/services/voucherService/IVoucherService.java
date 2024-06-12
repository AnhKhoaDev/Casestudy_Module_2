package services.voucherService;

import models.Voucher;

public interface IVoucherService {
    void addVoucher();
    void disableVoucher(String voucherId);
}
