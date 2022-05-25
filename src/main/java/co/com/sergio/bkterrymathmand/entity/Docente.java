package co.com.sergio.bkterrymathmand.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @project bk-terrymathmand
 * @Author Sergio Abelardo Rodríguez Vásquez
 * @Email ingsergiorodriguezv@gmail.com
 * @Date 19/05/2022 10:52
 **/

@Entity
public class Docente extends Usuario {

  @Column(nullable = false)
  private String correo;

  /* Getter y Setter */

  public String getCorreo() {
    return correo;
  }

  public void setCorreo(String correo) {
    this.correo = correo;
  }
}
