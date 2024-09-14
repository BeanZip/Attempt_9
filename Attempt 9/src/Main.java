import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.util.Scanner;

import com.formdev.flatlaf.*;

class Main extends JFrame{
    static class frame{
        JFrame frame = new JFrame("Gui Application Menu");
        int screenwidth = 540;
        int screenheight = screenwidth/2;

        void InitWindow(){
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(screenwidth,screenheight);
        }

        void Visible(){
            frame.setVisible(true);
        }

        void text(){
        }
    }


    static class Menu extends frame{
        JMenuBar menuBar = new JMenuBar();

        JMenu File = new JMenu("File");
        JMenuItem Select = new JMenuItem("Select File");
        JMenuItem Create = new JMenuItem("Create File");
        JMenuItem Read = new JMenuItem("Read File");
        JMenuItem Write = new JMenuItem("Write File");
        JMenuItem Delete = new JMenuItem("Delete File");

        File file = new File("YourFile.txt");
        File selectedFile;
        FileWriter fileWriter = new FileWriter(file,true);
        JFileChooser fileChooser = new JFileChooser();



        //NOTE: Finish Implementing Create,Read,Write
        int frameheight = frame.getHeight();
        int panelheight = 100;
        int panelwidth = 200;

        JPanel panel = new JPanel();
        int x = (frame.getWidth() - panelwidth);
        int y = frameheight - 50;
        JTextField textField;
        JLabel resultlabel;



        Scanner input = new Scanner(file);

        JMenu help = new JMenu("Help");
        JMenuItem help1 = new JMenuItem("How to Select a File");
        JMenuItem help2 = new JMenuItem("Where is the code for this?");
        JMenuItem help3 = new JMenuItem("Github");

        JMenu options = new JMenu("Options");
        JMenu Theme = new JMenu("Themes");

        JMenuItem light = new JMenuItem("Light");
        JMenuItem dark = new JMenuItem("Dark");
        JMenuItem IntelliJ = new JMenuItem("System Theme");

        JMenu Adjust = new JMenu("Adjust Size");
        JMenuItem Large = new JMenuItem("Large Size");
        JMenuItem medium = new JMenuItem("Medium Size");
        JMenuItem Small = new JMenuItem("Small Size");

        Menu() throws IOException {
        }

        public void interactable() {

            light.addActionListener(e -> {
               FlatLightLaf.setup();

               SwingUtilities.updateComponentTreeUI(frame);
            });
            dark.addActionListener(e -> {
                FlatDarkLaf.setup();

                SwingUtilities.updateComponentTreeUI(frame);
            });

            IntelliJ.addActionListener(e -> {
                FlatIntelliJLaf.setup();

                SwingUtilities.updateComponentTreeUI(frame);
            });



            Select.addActionListener(e -> {

                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    selectedFile = fileChooser.getSelectedFile();
                    JOptionPane.showMessageDialog(frame, "Selected File: " + selectedFile.getAbsolutePath());
                }
            });

            Create.addActionListener(e ->{
                try {
                    if (file.createNewFile()) {
                        JOptionPane.showMessageDialog(null, "File has been created: " + file.getName());
                    } else {
                        JOptionPane.showMessageDialog(null, "File already exists at: " + file.getAbsolutePath());
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error creating file: " + ex.getMessage());
                }
            });

            Delete.addActionListener(e ->{
                if (selectedFile.delete()){
                    JOptionPane.showMessageDialog(null, "File deleted");
                } else{
                    JOptionPane.showMessageDialog(null, "Failed to delete file");
                }
            });

            Read.addActionListener(e -> {
                try(Scanner scanner = new Scanner(selectedFile)){
                    while(scanner.hasNextLine()){
                        String line = scanner.nextLine();
                        JOptionPane.showMessageDialog(frame,line);
                    }
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
            });

            help1.addActionListener(e -> {
                System.out.println("Input Detected");
                JOptionPane.showMessageDialog(frame, "Just Press The Select Button");
            });
            help2.addActionListener(e -> {
                System.out.println("Input Detected");
                JOptionPane.showMessageDialog(frame, "It will be put on my github if Necessary.");
            });
            help3.addActionListener(e -> {
                System.out.println("Input Detected");
                try {
                    URI uri = new URI("https://github.com/nerdbro34");

                    Desktop desktop = Desktop.getDesktop();
                    if (desktop.isSupported(Desktop.Action.BROWSE)){
                        desktop.browse(uri);
                    }
                    else{
                        JOptionPane.showMessageDialog(frame, "Could not be Initialized");
                    }
                } catch (Exception rx) {
                    rx.printStackTrace();
                }
            });

            Large.addActionListener(e ->{
                screenwidth = 800;
                screenheight = 500;
                frame.setSize(screenwidth,screenheight);
            });
            medium.addActionListener(e ->{
                screenwidth = 540;
                screenheight = screenwidth/2;
                frame.setSize(screenwidth,screenheight);
            });

            Small.addActionListener(e ->{
                screenwidth = 328;
                screenheight = 122;
                frame.setSize(screenwidth,screenheight);
            });

        }

        private void DrawButton() {
            File.add(Select);
            File.add(Create);
            File.add(Read);
            File.add(Delete);

            help.add(help1);
            help.add(help2);
            help.add(help3);

            options.add(Adjust);
            options.add(Theme);

            Theme.add(light);
            Theme.add(dark);
            Theme.add(IntelliJ);

            Adjust.add(Large);
            Adjust.add(Small);
            Adjust.add(medium);

            menuBar.add(help);
            menuBar.add(File);
            menuBar.add(options);
        }

        private void TextArea(){
            panel.setBounds(x,y,panelwidth,panelheight);
            frame.add(panel);
            setupUI(panel);
            frame.pack();
        }

        private void setupUI(JPanel panel) {
                panel.setLayout(new FlowLayout()); // Use a layout manager instead of null layout

                textField = new JTextField("Enter Text Here", 16);
                JButton submit = new JButton("Submit");

                resultlabel = new JLabel("Nothing Written");

                // Remove this, so it doesn't reset immediately
                // textField.setText("");

                // Action listener for submit button
                submit.addActionListener(e -> {
                    resultlabel.setText(textField.getText());
                });

                // Add components to the panel
                panel.add(textField);
                panel.add(submit);
                panel.add(resultlabel);
        }

        public void DrawMenuFrame(){
             // JPanel panel = new JPanel(); //
            InitWindow();
            frame.setJMenuBar(menuBar);
            TextArea();
            DrawButton();
            interactable();
            Visible();
        }
    }

    public static void main(String[] args) throws IOException {
        FlatDarkLaf.setup();

        Menu m1 = new Menu();
        m1.DrawMenuFrame();
    }
}