package co.com.sergio.bkterrymathmand.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

/**
 * @project bk-terrymathmand
 * @Author Sergio Abelardo Rodríguez Vásquez
 * @Email ingsergiorodriguezv@gmail.com
 * @Date 19/05/2022 10:52
 **/

@Entity
@PrimaryKeyJoinColumn(name = "idusuario")
public class Docente extends Usuario {

}
