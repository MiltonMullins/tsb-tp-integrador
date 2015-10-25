package modeloDeDatos;

/**
 * Created by agus on 25/10/15.
 */
public class Documento {

    private String nombreDocumento;

    public Documento(String nombreDocumento){
        this.nombreDocumento = nombreDocumento;
    }

    public String getNombreDocumento() {
        return nombreDocumento;
    }

    public void setNombreDocumento(String nombreDocumento) {
        this.nombreDocumento = nombreDocumento;
    }
}
