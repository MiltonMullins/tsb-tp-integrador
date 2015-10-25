package conexionBaseDeDatos;

import modeloDeDatos.Documento;
import modeloDeDatos.Palabra;
import modeloDeDatos.Vocabulario;
import parser.ParserDocumento;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by agus on 25/10/15.
 */
public class BaseDeDatosManager {
    private static Vocabulario vocabularioActual;
    private final static String databaseName = "jdbc:sqlite:tsb-practico-integrador.db";


    public static void agregarPalabras(ArrayList<Palabra> palabrasNuevas){
        crearTablas();
        vocabularioActual = cargarVocabulario();




        guardarVocabulario(vocabularioActual,palabrasNuevas);
    }

    // con jdbc y sqlite la BD se crea automaticamente.
    public static void crearTablas(){
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(databaseName);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql1 = "CREATE TABLE IF NOT EXISTS Palabras " +
                    "(texto TEXT PRIMARY KEY     NOT NULL," +
                    " frecuencia           INT    NOT NULL) " ;
            stmt.executeUpdate(sql1);

            stmt = c.createStatement();
            String sql2 = "CREATE TABLE IF NOT EXISTS DocumentosPorPalabra " +
                    "(nombre TEXT     NOT NULL," +
                    " palabra TEXT    NOT NULL, " +
                    " PRIMARY KEY (nombre,palabra) ) " ;
            stmt.executeUpdate(sql2);


            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Tables Palabra and DocumentosPorPalabra created successfully");
    }

    public static void reiniciarBaseDeDatos(){

    }

    public static Vocabulario cargarVocabulario(){
        Vocabulario v = null;

        Connection c = null;
        Statement stmt = null;
        Statement stmt2 = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(databaseName);
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            ArrayList<Palabra> palabras = new ArrayList<Palabra>();
            v = new Vocabulario(palabras);

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM Palabras;" );
            while ( rs.next() ) {


                String texto = rs.getString("texto");
                int frecuencia = rs.getInt("frecuencia");

                Palabra p = new Palabra(texto,frecuencia,null);

                stmt2 = c.createStatement();
                ResultSet rs2 = stmt.executeQuery( "SELECT * FROM DocumentosPorPalabra WHERE palabra = '"+texto+"';" );
                while ( rs2.next() ) {
                    String nombre = rs2.getString("nombre");
                    Documento d = new Documento(nombre);
                    p.getDocumentosDondeAparece().add(d);
                }
                stmt2.close();


            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");


        return v;
    }

    private static boolean existePalabra(Palabra palabra, Vocabulario v){
        boolean res = false;

        for (int i = 0; i < v.getPalabras().size() ; i++) {
            if (v.getPalabras().get(i).getTexto().equalsIgnoreCase(palabra.getTexto())){
                res = true;
                break;
            }
        }

        return res;
    }

    private static void insertarPalabra(Palabra p, Vocabulario vocabularioActual){
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(databaseName);
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "INSERT INTO Palabras (texto,frecuencia) " +
                    "VALUES ('"+p.getTexto()+"',"+p.getFrecuencia()+");";
            stmt.executeUpdate(sql);

            //insert documentos
            stmt = c.createStatement();
            for (int j = 0; j < p.getDocumentosDondeAparece().size(); j++) {
                String sql2 = "INSERT INTO DocumentosPorPalabra (nombre,palabra) "+
                        "VALUES ('"+p.getDocumentosDondeAparece().get(j).getNombreDocumento()+"','"+p.getTexto()+"');";
                stmt.executeUpdate(sql2);
            }


            stmt.close();
            c.commit();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Records created successfully");

    }

    private static void actualizarPalabra(Palabra p, Vocabulario vocabularioActual){
        // TODO

        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(databaseName);
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "UPDATE COMPANY set SALARY = 25000.00 where ID=1;";
            stmt.executeUpdate(sql);
            c.commit();

            ResultSet rs = stmt.executeQuery( "SELECT * FROM COMPANY;" );
            while ( rs.next() ) {
                int id = rs.getInt("id");
                String  name = rs.getString("name");
                int age  = rs.getInt("age");
                String  address = rs.getString("address");
                float salary = rs.getFloat("salary");
                System.out.println( "ID = " + id );
                System.out.println( "NAME = " + name );
                System.out.println( "AGE = " + age );
                System.out.println( "ADDRESS = " + address );
                System.out.println( "SALARY = " + salary );
                System.out.println();
            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");
    }

    private static void guardarVocabulario(Vocabulario vocabularioActual, ArrayList<Palabra> palabrasNuevas){


        for (int i = 0; i < palabrasNuevas.size(); i++) {
            if (existePalabra(palabrasNuevas.get(i),vocabularioActual)){
                //actualizarPalabra(palabrasNuevas.get(i),vocabularioActual);
            } else { // no existe, se hace un INSERT
                insertarPalabra(palabrasNuevas.get(i),vocabularioActual);
            }
        }




    }
}
