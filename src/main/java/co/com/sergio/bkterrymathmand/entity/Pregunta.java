package co.com.sergio.bkterrymathmand.entity;

import javax.persistence.*;
import java.io.Serializable;

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

  @Column(length = 120, nullable = false)
  private String enunciado;

  private String urlImg;

  @OneToOne
  @JoinColumn(name = "opcionA", nullable = false)
  private Opcion opcionA;

  @OneToOne
  @JoinColumn(name = "opcionB",nullable = false)
  private Opcion opcionB;

  @OneToOne
  @JoinColumn(name = "opcionC", nullable = false)
  private Opcion opcionC;

  @OneToOne
  @JoinColumn(name = "opcionD", nullable = false)
  private Opcion opcionD;

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

  public Opcion getOpcionC() {
    return opcionC;
  }

  public void setOpcionC(Opcion opcionC) {
    this.opcionC = opcionC;
  }

  public Opcion getOpcionD() {
    return opcionD;
  }

  public void setOpcionD(Opcion opcionD) {
    this.opcionD = opcionD;
  }

  public String getUrlImg() {
    return urlImg;
  }

  public void setUrlImg(String urlImg) {
    this.urlImg = urlImg;
  }
}
