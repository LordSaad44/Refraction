package me.lordsaad.refraction.gui;

import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.google.common.base.Charsets.UTF_8;

/**
 * Created by Saad on 4/19/2016.
 */
public class DocumentationParser {

    File file;

    public DocumentationParser(File file) {
        this.file = file;
    }

    public void parse() {
        List<String> txt = null;
        try {
            txt = Files.readLines(file, UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String realText = null;
        for (String lines : txt) {
            if (lines.startsWith("")) ;
        }
    }
}
