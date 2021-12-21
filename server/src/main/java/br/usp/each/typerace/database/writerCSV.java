package br.usp.each.typerace.database;

import com.opencsv.CSVWriter;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class writerCSV {


    private String path;

    public writerCSV(String path) {
        this.path = path;
    }

    public void generate(){
        String[] cabecalho = {"Palavras"};
        ReaderPDF readerPDF = new ReaderPDF(path);
        readerPDF.gerateFullText();
        readerPDF.gerateUtilDB();
        List<String[]> linhas = new ArrayList<>(readerPDF.getUtilDB());

        try {
            Writer writer = Files.newBufferedWriter(Paths.get(path));
            CSVWriter csvWriter = new CSVWriter(writer);
            csvWriter.writeNext(cabecalho);
            csvWriter.writeAll(linhas);
            csvWriter.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
