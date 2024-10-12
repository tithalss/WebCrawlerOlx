package com.example.service;

import com.example.model.Anuncio;
import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.core5.http.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class CrawlerOLX {

    private static final String BASE_URL = "https://www.olx.com.br/celulares/estado-go?q=iphone%2011&sp=2";
    private static final String[] USER_AGENTS = {
            "Mozilla/5.0 (iPad; CPU OS 12_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E148",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.83 Safari/537.36",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.51 Safari/537.36"
    };

    // Cabeçalhos HTTP
    private static final Map<String, String> HEADERS;

    static {
        HEADERS = new HashMap<>();
        HEADERS.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:98.0) Gecko/20100101 Firefox/98.0");
        HEADERS.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8");
        HEADERS.put("Accept-Language", "en-US,en;q=0.5");
        HEADERS.put("Accept-Encoding", "gzip, deflate");
        HEADERS.put("Connection", "keep-alive");
        HEADERS.put("Upgrade-Insecure-Requests", "1");
        HEADERS.put("Sec-Fetch-Dest", "document");
        HEADERS.put("Sec-Fetch-Mode", "navigate");
        HEADERS.put("Sec-Fetch-Site", "none");
        HEADERS.put("Sec-Fetch-User", "?1");
        HEADERS.put("Cache-Control", "max-age=0");
    }

    // Lista de proxies
    private static final String[] PROXIES = {
            "http://Username:Password@IP1:20000",
            "http://Username:Password@IP2:20000",
            "http://Username:Password@IP3:20000",
            "http://Username:Password@IP4:20000"
    };

    public List<Anuncio> getAnuncios(int numPaginas) throws IOException, ParseException {
        List<Anuncio> anuncios = new ArrayList<>();
        Random random = new Random(); // Criar uma instância de Random
        int proxyIndex = 0;

        for (int i = 1; i <= numPaginas; i++) {
            String url = BASE_URL + "&o=" + i;

            // Escolhe um User Agent aleatório da lista
            String userAgent = USER_AGENTS[random.nextInt(USER_AGENTS.length)];

            // Escolhe um proxy da lista
            String proxy = PROXIES[proxyIndex];
            proxyIndex = (proxyIndex + 1) % PROXIES.length; // Rotaciona entre os proxies

            // Cria a requisição com os cabeçalhos e proxy
            String response = Request.get(url)
                    .addHeader("User-Agent", userAgent)
                    .addHeader("Accept", HEADERS.get("Accept"))
                    .addHeader("Accept-Language", HEADERS.get("Accept-Language"))
                    .addHeader("Accept-Encoding", HEADERS.get("Accept-Encoding"))
                    .addHeader("Connection", HEADERS.get("Connection"))
                    .addHeader("Upgrade-Insecure-Requests", HEADERS.get("Upgrade-Insecure-Requests"))
                    .addHeader("Sec-Fetch-Dest", HEADERS.get("Sec-Fetch-Dest"))
                    .addHeader("Sec-Fetch-Mode", HEADERS.get("Sec-Fetch-Mode"))
                    .addHeader("Sec-Fetch-Site", HEADERS.get("Sec-Fetch-Site"))
                    .addHeader("Sec-Fetch-User", HEADERS.get("Sec-Fetch-User"))
                    .addHeader("Cache-Control", HEADERS.get("Cache-Control"))
                    .addHeader("Proxy-Authorization", "Basic " + encodeProxyCredentials(proxy)) // Adiciona credenciais do proxy
                    .execute()
                    .returnContent()
                    .asString();

            Document doc = Jsoup.parse(response);
            Elements elements = doc.select(".sc-12rk7z2-0");

            for (Element element : elements) {
                String titulo = element.select(".sc-12rk7z2-9").text();
                String valor = element.select(".sc-ifAKCX.eoKYee").text().replace("R$", "").trim();
                String endereco = element.select(".sc-1c3ysll-1").text();
                String link = element.select("a").attr("href");

                if (!titulo.isEmpty() && !valor.isEmpty()) {
                    try {
                        anuncios.add(new Anuncio(titulo, Double.parseDouble(valor.replace(".", "")), endereco, link));
                    } catch (NumberFormatException e) {
                        System.out.println("Erro ao processar o valor: " + valor);
                    }
                }
            }

            // Aguarda 5 segundos entre as requisições
            try {
                Thread.sleep(5000); // Aumenta para 5 segundos
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restaura o estado de interrupção
                throw new RuntimeException(e);
            }
        }
        return anuncios;
    }

    // Método para codificar credenciais de proxy
    private String encodeProxyCredentials(String proxy) {
        // Extrai Username e Password do proxy
        String[] parts = proxy.split("@")[0].split("://")[1].split(":");
        String username = parts[0];
        String password = parts[1];
        String credentials = username + ":" + password;

        // Codifica em Base64
        return java.util.Base64.getEncoder().encodeToString(credentials.getBytes());
    }
}
