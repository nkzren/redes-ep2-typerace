package br.usp.each.typerace.database;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ReaderPDF {

    private String path;
    private String fullText;
    private Set<String[]> utilDB;

    public ReaderPDF(String path) {
        this.path = path;
        this.fullText = "";
        this.utilDB = new HashSet<>();
    }

    //Responsavel por ler o PDF e convertelo em uma String
    public void gerateFullText(){
        File file = new File(path);
        try {
            PDDocument document = Loader.loadPDF(file);
            AccessPermission ap = document.getCurrentAccessPermission();

            if(!ap.canExtractContent())
                throw new IOException("You do not have permission to extract text");

            PDFTextStripper tracy = new PDFTextStripper(); //HIMYM S01E09
            tracy.setSortByPosition(true);
            tracy.setStartPage(0);
            tracy.setEndPage(document.getNumberOfPages() -1);
            this.fullText = tracy.getText(document);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //responsável por ler a String e converter em um Set
    public void gerateUtilDB(){
            String[] temp = this.fullText.split("\\s");
        for(String word: temp){
           // System.out.println(word);
            if(word.length() > 5){
                String result = word.replaceAll("\\p{Punct}", "");
                String[] wordArray = {result.toLowerCase()};
                this.utilDB.add(wordArray);
            }
        }

    }



    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFullText() {
        return fullText;
    }

    public void setFullText(String fullText) {
        this.fullText = fullText;
    }

    public Collection<? extends String[]> getUtilDB() {
        return utilDB;
    }

    public void setUtilDB(Set<String[]> utilDB) {
        this.utilDB = utilDB;
    }
}
