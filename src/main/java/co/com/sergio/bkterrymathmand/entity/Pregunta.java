package co.com.sergio.bkterrymathmand.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @project bk-terrymathmand
 * @Author Sergio Abelardo Rodríguez Vásquez
 * @Email ingsergiorodriguezv@gmail.com
 * @Date 20/04/2022 9:38
 **/

@Entity
@Table(name="pregunta")
public class Pregunta implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int idpregunta;

  @Column(length = 80)
  private String enunciado;

  @OneToOne
  @JoinColumn(name = "opcionA")
  private Opcion opcionA;

  @OneToOne
  @JoinColumn(name = "opcionB")
  private Opcion opcionB;

  /** Getter y Setter **/

  public int getIdpregunta() {
    return idpregunta;
  }

  public void setIdpregunta(int idpregunta) {
    this.idpregunta = idpregunta;
  }

  public String getEnunciado() {
    return enunciado;
  }

  public void setEnunciado(String enunciado) {
    this.enunciado = enunciado;
  }

  public Opcion getOpcionA() {
    return opcionA;
  }

  public void setOpcionA(Opcion opcionA) {
    this.opcionA = opcionA;
  }

  public Opcion getOpcionB() {
    return opcionB;
  }

  public void setOpcionB(Opcion opcionB) {
    this.opcionB = opcionB;
  }
}
