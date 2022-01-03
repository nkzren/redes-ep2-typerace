package br.usp.each.typerace.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class GerateDB {

    private static final Logger LOGGER = LoggerFactory.getLogger(GerateDB.class);

    public static void main(String[] args){


        final String PATH_RESOURCES = "./src/main/resources";
        final String LORD_OF_RINGS = PATH_RESOURCES + "/word/LordOfRings.pdf";
        final String WRITE_DB = PATH_RESOURCES + "/DB/LordOfRings.csv";

        WriterCSV writerCSV = new WriterCSV(LORD_OF_RINGS, WRITE_DB);
        LOGGER.info("Generating DB, wait a minute.");
        writerCSV.generate();
    }
}
