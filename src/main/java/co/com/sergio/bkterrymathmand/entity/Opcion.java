package co.com.sergio.bkterrymathmand.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @project bk-terrymathmand
 * @Author Sergio Abelardo Rodríguez Vásquez
 * @Email ingsergiorodriguezv@gmail.com
 * @Date 20/04/2022 9:42
 **/

@Entity
@Table(name = "opcion")
public class Opcion implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int idopcion;

  @Column(length = 80, nullable = false)
  private String enunciadoopcion;

  private boolean respuesta;

  @ManyToOne
  @JoinColumn(name = "idpregunta")
  private Pregunta pregunta;

  /** Getter y Setter **/

  public int getIdopcion() {
    return idopcion;
  }

  public void setIdopcion(int idopcion) {
    this.idopcion = idopcion;
  }

  public String getEnunciadoopcion() {
    return enunciadoopcion;
  }

  public void setEnunciadoopcion(String enunciadoopcion) {
    this.enunciadoopcion = enunciadoopcion;
  }

  public boolean isRespuesta() {
    return respuesta;
  }

  public void setRespuesta(boolean respuesta) {
    this.respuesta = respuesta;
  }

  public void setPregunta(Pregunta pregunta) {
    this.pregunta = pregunta;
  }
}
