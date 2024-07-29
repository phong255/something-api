package dev.lhphong.somethingapi.Config.Response;

import dev.lhphong.somethingapi.Config.Constant.ServiceError;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ServiceResponse {
    private Date at;
    private ServiceError error;
    private Object data;

    public ServiceResponse(Date date, ServiceError error) {
        this.at = date;
        this.error = error;
    }
}
