package co.com.sergio.bkterrymathmand.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @project bk-terrymathmand
 * @Author Sergio Abelardo Rodríguez Vásquez
 * @Email ingsergiorodriguezv@gmail.com
 * @Date 21/04/2022 11:44
 **/

@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int idusuario;

  @Column(nullable = false)
  private String nombre;

  @OneToMany(mappedBy = "usuario")
  private List<Respuesta> respuestas;

  /* Getter y Setter */

  public int getIdusuario() {
    return idusuario;
  }

  public void setIdusuario(int idusuario) {
    this.idusuario = idusuario;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public List<Respuesta> getRespuestas() {
    return respuestas;
  }

  public void setRespuestas(List<Respuesta> respuestas) {
    this.respuestas = respuestas;
  }
}
