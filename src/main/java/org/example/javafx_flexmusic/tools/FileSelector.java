package org.example.javafx_flexmusic.tools;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class FileSelector {
    public static File selectFile(String dialog, String description, String fileExtension) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(dialog);
        fileChooser.setFileFilter( new FileNameExtensionFilter(description, fileExtension));
        fileChooser.showOpenDialog(null);
        int userSelection = fileChooser.showOpenDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            return file;
        }
        return null;
    }
}
