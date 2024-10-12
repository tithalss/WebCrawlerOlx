package com.example;

import com.example.model.Anuncio;
import com.example.model.Email;
import com.example.service.CrawlerOLX;
import com.example.service.EmailSender;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        CrawlerOLX crawler = new CrawlerOLX();
        EmailSender emailSender = new EmailSender();

        int numPaginas = 3; // Defina o número de páginas que deseja coletar
        List<Anuncio> anuncios = crawler.getAnuncios(numPaginas);

        // (Lógica para calcular a média, filtrar os anúncios, gerar CSV...)

        // Defina os dados do email
        String toEmail = "destinatario@gmail.com";
        String subject = "Relatório de Anúncios de iPhone 11";
        String messageText = "Veja em anexo os anúncios coletados.";
        String csvFilePath = "caminho/para/o/seu/arquivo.csv"; // Defina o caminho correto

        // Crie uma instância do Email
        Email email = new Email(toEmail, subject, messageText, csvFilePath);

        // Envie o email
        emailSender.sendEmail(email);
    }
}
