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

    public GeneralResponse() {
    }

    public GeneralResponse(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
