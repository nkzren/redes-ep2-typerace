package br.usp.each.typerace.database;

import com.opencsv.CSVWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class WriterCSV {


    private String pathRead, pathWrite;
    private static final Logger LOGGER = LoggerFactory.getLogger(WriterCSV.class);

    public WriterCSV(String pathRead, String pathWrite) {
        this.pathRead = pathRead;
        this.pathWrite = pathWrite;
    }

    public void generate(){
        String[] cabecalho = {"Palavras"};
        ReaderPDF readerPDF = new ReaderPDF(pathRead);
        readerPDF.gerateFullText();
        LOGGER.info("Text Gerate");
        readerPDF.gerateUtilDB();
        LOGGER.info("DB Gerate");
        List<String[]> linhas = new ArrayList<>(readerPDF.getUtilDB());

        try {
            Writer writer = Files.newBufferedWriter(Paths.get(pathWrite));
            CSVWriter csvWriter = new CSVWriter(writer);
            //csvWriter.writeNext(cabecalho);
            csvWriter.writeAll(linhas);
            csvWriter.flush();
            writer.close();
            LOGGER.info(".csv Write");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getPathRead() {
        return pathRead;
    }

    public void setPathRead(String pathRead) {
        this.pathRead = pathRead;
    }

    public String getPathWrite() {
        return pathWrite;
    }

    public void setPathWrite(String pathWrite) {
        this.pathWrite = pathWrite;
    }
}
