package formularios;

import conexionBaseDeDatos.BaseDeDatosManager;
import modeloDeDatos.Palabra;
import parser.ParserDocumento;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by agus on 25/10/15.
 */
public class InsertarDocumentosForm extends JFrame {


    private JPanel panel1;
    private JButton agregarDocumentoButton;
    private JList listaArchivos;
    private JButton agregarAlVocabularioButton;

    private ArrayList<File> archivos = new ArrayList<File>();


    public InsertarDocumentosForm(){
        super("InsertarDocumentosForm");

        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);

        agregarAlVocabularioButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                ParserDocumento p = new ParserDocumento(archivos);
                ArrayList<Palabra> palabras = p.parseFiles();

                BaseDeDatosManager.agregarPalabras(palabras);


            }
        });

        agregarDocumentoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
                fileChooser.setFileFilter(filter);
                int result = fileChooser.showOpenDialog(panel1);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    System.out.println("Selected file: " + selectedFile.getAbsolutePath());


                    if (selectedFile != null){
                        if (getFileExtension(selectedFile).equals("txt") ){
                            archivos.add(selectedFile);
                        } else {
                            JOptionPane.showMessageDialog(panel1,
                                    "Solo se permiten archivos de extensión txt",
                                    "Error del tipo de archivo",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }

                    actualizarListaArchivos();
                }


            }
        });

    }




    private String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }

    public void actualizarListaArchivos(){
        listaArchivos.setModel(new ListModel() {
            @Override
            public int getSize() {
                return archivos.size();
            }

            @Override
            public Object getElementAt(int index) {
                return archivos.get(index).getName();
            }

            @Override
            public void addListDataListener(ListDataListener l) {

            }

            @Override
            public void removeListDataListener(ListDataListener l) {

            }
        });
    }







}
