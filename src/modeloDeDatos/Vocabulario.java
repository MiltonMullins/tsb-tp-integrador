package modeloDeDatos;

import java.util.ArrayList;

/**
 * Created by agus on 25/10/15.
 */
public class Vocabulario {

    private ArrayList<Palabra> palabras;

    public Vocabulario (ArrayList<Palabra> palabras){
        this.palabras = palabras;
    }

    public ArrayList<Palabra> getPalabras() {
        return palabras;
    }

    public void setPalabras(ArrayList<Palabra> palabras) {
        this.palabras = palabras;
    }
}
