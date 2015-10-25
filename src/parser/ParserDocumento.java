package parser;

import modeloDeDatos.Documento;
import modeloDeDatos.Palabra;
import modeloDeDatos.Vocabulario;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by agus on 25/10/15.
 */
public class ParserDocumento {

    private ArrayList<File> archivos;

    public ParserDocumento(ArrayList<File> archivos){
        this.archivos = archivos;
    }

    public ArrayList<Palabra> parseFiles(){
        ArrayList<Palabra> array = new ArrayList<Palabra>();

        if (archivos != null){
            for (int i = 0; i < archivos.size(); i++) {

                try {
                // obtener todos los strings

                    FileReader fileReader = new FileReader(archivos.get(i));
                    BufferedReader reader = new BufferedReader(fileReader);

                    StringBuilder sb = new StringBuilder();
                    String line;
                    do {
                        line = reader.readLine();
                        if (line != null){
                            sb.append(line);
                        }
                    }
                    while (line != null);

                    reader.close();
                    fileReader.close();

                    parseIntoWords(sb.toString(),array,archivos.get(i).getName());


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e){
                    e.printStackTrace();
                }


            }
        }

        return array;
    }


    private void parseIntoWords(String s, ArrayList<Palabra> palabras,final String fileName){

        String[] array = s.split("\\W+");
        for (int i = 0; i < array.length; i++) {
            ArrayList<Documento> documentos = new ArrayList<Documento>();
            documentos.add(new Documento(fileName));
            palabras.add(new Palabra(array[i],1,documentos));
        }

    }
}
