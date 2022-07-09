package co.com.sergio.bkterrymathmand.entity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import java.util.List;

/**
 * @project bk-terrymathmand
 * @Author Sergio Abelardo Rodríguez Vásquez
 * @Email ingsergiorodriguezv@gmail.com
 * @Date 21/04/2022 11:44
 **/

@Entity
@PrimaryKeyJoinColumn(name = "idusuario")
public class Estudiante extends Usuario{

  @OneToMany(mappedBy = "estudiante")
  private List<Respuesta> respuestas;

  /* Getter y Setter */
  public List<Respuesta> getRespuestas() {
    return respuestas;
  }

  public void setRespuestas(List<Respuesta> respuestas) {
    this.respuestas = respuestas;
  }
}
