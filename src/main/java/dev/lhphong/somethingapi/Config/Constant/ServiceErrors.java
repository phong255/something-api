package dev.lhphong.somethingapi.Config.Constant;

public class ServiceErrors {
    public static final ServiceError SUCCESS = new ServiceError(0, "Thành công");
    public static final ServiceError ERROR = new ServiceError(99, "Lỗi không xác định");
    public static final ServiceError BAD_REQUEST = new ServiceError(99, "Yêu cầu không hợp lệ");
    public static final ServiceError FORBIDDEN = new ServiceError(403, "Không có quyền truy cập vào hệ thống !");
}
