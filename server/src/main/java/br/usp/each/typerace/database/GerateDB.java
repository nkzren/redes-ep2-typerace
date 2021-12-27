package br.usp.each.typerace.database;

public class GerateDB {

    public static void main(String[] args){

        final String PATH_RESOURCES = "../../../../../resources";
        final String LORD_OF_RINGS = PATH_RESOURCES + "/word/LordOfRings.pdf";
        final String WRITE_DB = PATH_RESOURCES + "/DB/LordOfRings.csv";

        WriterCSV writerCSV = new WriterCSV(LORD_OF_RINGS, WRITE_DB);
        System.out.println("Generating DB, wait a minute.");
        writerCSV.generate();
    }
}
