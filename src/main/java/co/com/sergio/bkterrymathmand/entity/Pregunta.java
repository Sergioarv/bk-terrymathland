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

  @Column(nullable = false)
  private String enunciado;

  private String urlImg;

  @OneToMany(mappedBy = "pregunta")
  private List<Opcion> opciones;

  @JoinTable(
          name = "cartilla_pregunta",
          joinColumns = @JoinColumn(name = "idpregunta", nullable = false),
          inverseJoinColumns = @JoinColumn(name = "idcartilla", nullable = false)
  )
  @ManyToMany(cascade = CascadeType.ALL)
  private List<Cartilla> catillas;

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

  public List<Opcion> getOpciones() {
    return opciones;
  }

  public void setOpciones(List<Opcion> opciones) {
    this.opciones = opciones;
  }

  public String getUrlImg() {
    return urlImg;
  }

  public void setUrlImg(String urlImg) {
    this.urlImg = urlImg;
  }

  public void setCatillas(List<Cartilla> catillas) {
    this.catillas = catillas;
  }
}
