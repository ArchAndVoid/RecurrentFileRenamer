package com.main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class ApplicationWindow {
    private JButton runButton;
    private JPanel main;
    private JButton selectFolderButton;
    private JTextField oldFormatField;
    private JTextField newFormatField;
    private JTextArea console;
    private String path;

    public ApplicationWindow(final JFrame frame) {
        selectFolderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int option = fileChooser.showOpenDialog(frame);
                if(option == JFileChooser.APPROVE_OPTION){
                    File file = fileChooser.getSelectedFile();
                    path = file.getAbsolutePath();
                    console.append("\nFolder Selected: " + path);
                }else{
                    console.append("\nOpen command canceled");
                }
            }
        });
        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String oldFormat = oldFormatField.getText();
                String newFormat = newFormatField.getText();
                if (oldFormat.isEmpty()){
                    console.append("\nPlease enter text to replace");
                    return;
                }
                if (newFormat.isEmpty()){
                    console.append("\nPlease enter text to replace with");
                    return;
                }
                FileRenamer renamer = new FileRenamer();
                try {
                    try {
                        console.append("\nFiles renamed: "  + renamer.rename(path, oldFormat, newFormat).toString());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } catch (NullPointerException nullPointerException) {
                    console.append("\nPlease select folder");
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("App");
        frame.setContentPane(new ApplicationWindow(frame).main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(560, 200);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
