package co.com.sergio.bkterrymathmand.utils;

import java.io.Serializable;

/**
 * @project bk-aerolinea-bajocosto
 * @Author Sergio Abelardo Rodríguez Vásquez
 * @Email ingsergiorodriguezv@gmail.com
 * @Date 14/03/2022 16:51
 **/
public class GeneralResponse<T> implements Serializable {

    private T data;

    private boolean success;

    private String message;

    public GeneralResponse() {
    }

    public GeneralResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public GeneralResponse(T data, boolean success, String message) {
        this.data = data;
        this.success = success;
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
