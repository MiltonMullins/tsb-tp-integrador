package modeloDeDatos;

import javax.print.Doc;
import java.util.ArrayList;

/**
 * Created by agus on 25/10/15.
 */
public class Palabra {

    private String texto;
    private int frecuencia;
    private ArrayList<Documento> documentosDondeAparece;

    public Palabra(String texto, int frecuencia, ArrayList<Documento> documentosDondeAparece){
        this.texto = texto;
        this.frecuencia = frecuencia;
        this.documentosDondeAparece = documentosDondeAparece;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public int getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(int frecuencia) {
        this.frecuencia = frecuencia;
    }

    public ArrayList<Documento> getDocumentosDondeAparece() {
        return documentosDondeAparece;
    }

    public void setDocumentosDondeAparece(ArrayList<Documento> documentosDondeAparece) {
        this.documentosDondeAparece = documentosDondeAparece;
    }
}
