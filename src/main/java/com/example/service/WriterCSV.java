package com.example.service;

import com.example.model.Anuncio;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class WriterCSV {

    public void writeAnunciosToCSV(String fileName, List<Anuncio> anuncios) {
        try (CSVPrinter printer = new CSVPrinter(new FileWriter(fileName), CSVFormat.DEFAULT.withHeader("Título", "Valor", "Endereço", "URL"))) {
            for (Anuncio anuncio : anuncios) {
                printer.printRecord(anuncio.getTitulo(), anuncio.getValor(), anuncio.getEndereco(), anuncio.getUrl());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
