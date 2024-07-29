package dev.lhphong.somethingapi.Config.Constant;

import lombok.Data;

@Data
public class ServiceError {
    private String message;
    private Integer code;

    public ServiceError(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
