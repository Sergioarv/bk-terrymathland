package co.com.sergio.bkterrymathmand.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @project bk-terrymathmand
 * @Author Sergio Abelardo Rodríguez Vásquez
 * @Email ingsergiorodriguezv@gmail.com
 * @Date 20/04/2022 9:38
 **/

@Entity
@Table(name = "pregunta")
public class Pregunta implements Serializable {

    @Id
    @Column(name = "idpregunta", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idpregunta;

    @Column(nullable = false)
    private String enunciado;

    @Column(nullable = false)
    private String urlImg;
    @Column(nullable = false)
    private String nombreImg;
    @Column(nullable = false)
    private String idImg;

    @OneToMany(mappedBy = "pregunta")
    private List<Opcion> opciones;

    @JoinTable(
            name = "cartilla_pregunta",
            joinColumns = @JoinColumn(name = "idpregunta", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "idcartilla", nullable = false)
    )
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Cartilla> cartillas;

    public void agregarCartilla(Cartilla cartilla) {
        if (this.cartillas == null) {
            this.cartillas = new ArrayList<>();
        }

        this.cartillas.add(cartilla);
    }

    public  void removerCartilla(Cartilla cartilla){
        this.cartillas.remove(cartilla);
        cartilla.getPreguntas().remove(this);
    }

    public List<Cartilla> obtenerCartillas(){
        return this.cartillas;
    }

    /**
     * Getter y Setter
     **/

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

    public void setCartillas(List<Cartilla> cartillas) {
        this.cartillas = cartillas;
    }

    public String getNombreImg() {
        return nombreImg;
    }

    public void setNombreImg(String nombreImg) {
        this.nombreImg = nombreImg;
    }

    public String getIdImg() {
        return idImg;
    }

    public void setIdImg(String idImg) {
        this.idImg = idImg;
    }
}
