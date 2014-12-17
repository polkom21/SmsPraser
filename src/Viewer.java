import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by polkom21 on 2014-09-30 12:09.
 * Package: PACKAGE_NAME
 * Project: SmsPraser
 */
public class Viewer extends JFrame implements ActionListener {

    private JPanel panel;
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem open, exit;
    private File fileXML;
    private JFileChooser fc;
    private JTextArea textArea;
    private SmsReader reader;

    public Viewer(){
        setTitle("SMS Praser");

        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        menuBar = new JMenuBar();
        menu = new JMenu("Plik");
        menuBar.add(menu);

        //DefaultListModel listModel = new DefaultListModel();
        //listModel.addElement("Nazwa");

        //watki.setModel(listModel);

        open = new JMenuItem("Otwórz");
        open.addActionListener(this);
        menu.add(open);
        menu.addSeparator();
        exit = new JMenuItem("Wyjście");
        exit.addActionListener(this);
        menu.add(exit);

        textArea = new JTextArea(2, 30);
        textArea.setEditable(false);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panel.add(scrollPane, BorderLayout.CENTER);
        //add(scrollPane, BorderLayout.CENTER);

        setContentPane(panel);
        setJMenuBar(menuBar);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        System.out.println(s);
        if (s.equals("Otwórz")) {
            fc = new JFileChooser();
            int returnVal = fc.showOpenDialog(this);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                fileXML = fc.getSelectedFile();
                String filename = fileXML.getAbsolutePath();
                reader = new SmsReader(filename);
                System.out.println(fileXML.getAbsolutePath());
                System.out.println("Znaleziono "+ reader.messages.size() +" wiadomosci.");
                textArea.append(reader.getMessages());
            }
        } else if(s.equals("Wyjście")) {
            setVisible(false);
            dispose();
            System.exit(0);
        }
    }

}
