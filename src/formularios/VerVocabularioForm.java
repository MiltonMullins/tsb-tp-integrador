package formularios;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by agus on 25/10/15.
 */
public class VerVocabularioForm {

    private JPanel panel1;


    public void createJFrame(){
        JFrame frame = new JFrame("VerVocabularioForm");
        frame.setContentPane(new VerVocabularioForm().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }



}
