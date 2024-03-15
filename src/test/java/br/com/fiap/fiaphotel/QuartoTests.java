package br.com.fiap.fiaphotel;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class QuartoTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void salva_Quarto_SucessoTest() {

        String randomWord = geraPalavraRandomica(8);
        String idPredio = cadastrandoPredioSucesso(randomWord);
        Assert.assertNotEquals("Falha", idPredio);

        randomWord = geraPalavraRandomica(8);
        String url = "http://localhost:" + port + "/quarto/" + idPredio;

        String requestBody = "{\"tipo\":\"" + randomWord + "\"," +
                "\"pessoas\":\"10\"," +
                "\"camas\":\"5\"," +
                "\"moveis\":\"muitos moveis\"," +
                "\"banheiro\":\"5\"," +
                "\"valor\":\"200,00\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.getBody());

            String mensagem = jsonNode.get("Messagem").asText();

            Assert.assertEquals(mensagem, "Quarto CADASTRADO com sucesso.");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void altera_Quarto_SucessoTest() {

        String randomWord = geraPalavraRandomica(8);
        String idPredio = cadastrandoPredioSucesso(randomWord);
        Assert.assertNotEquals("Falha", idPredio);

        randomWord = geraPalavraRandomica(8);
        String url = "http://localhost:" + port + "/quarto/" + idPredio;

        String requestBody = "{\"tipo\":\"" + randomWord + "\"," +
                "\"pessoas\":\"10\"," +
                "\"camas\":\"5\"," +
                "\"moveis\":\"muitos moveis\"," +
                "\"banheiro\":\"5\"," +
                "\"valor\":\"200,00\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.getBody());

            String mensagem = jsonNode.get("Messagem").asText();
            String id = jsonNode.get("id").asText();

            Assert.assertEquals(mensagem, "Quarto CADASTRADO com sucesso.");

            url = "http://localhost:" + port + "/quarto/" + id;

            requestBody = "{\"tipo\":\"" + randomWord + "\"," +
                    "\"pessoas\":\"20\"," +
                    "\"camas\":\"20\"," +
                    "\"moveis\":\"bebhum moveis\"," +
                    "\"banheiro\":\"1\"," +
                    "\"valor\":\"50,00\"}";

            requestEntity = new HttpEntity<>(requestBody, headers);
            response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);

            String resp = "{\"id\":"+ id +"," +
                    "\"tipo\":\"" + randomWord + "\"," +
                    "\"pessoas\":\"20\"," +
                    "\"camas\":\"20\"," +
                    "\"moveis\":\"bebhum moveis\"," +
                    "\"banheiro\":\"1\"," +
                    "\"valor\":\"50,00\"," +
                    "\"predioDto\":null}";

            Assert.assertTrue(response.getBody() != null && response.getBody().contains(resp));

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void deleta_Predio_SucessoTest() {

        String randomWord = geraPalavraRandomica(8);
        String idPredio = cadastrandoPredioSucesso(randomWord);
        Assert.assertNotEquals("Falha", idPredio);

        randomWord = geraPalavraRandomica(8);
        String url = "http://localhost:" + port + "/quarto/" + idPredio;

        String requestBody = "{\"tipo\":\"" + randomWord + "\"," +
                "\"pessoas\":\"10\"," +
                "\"camas\":\"5\"," +
                "\"moveis\":\"muitos moveis\"," +
                "\"banheiro\":\"5\"," +
                "\"valor\":\"200,00\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.getBody());

            String mensagem = jsonNode.get("Messagem").asText();
            String id = jsonNode.get("id").asText();

            Assert.assertEquals(mensagem, "Quarto CADASTRADO com sucesso.");

            url = "http://localhost:" + port + "/quarto/" + id;

            requestEntity = new HttpEntity<>(headers);
            response = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, String.class);
            Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
            Assert.assertTrue(response.getBody() != null && response.getBody().contains("{\"Mensagem\": \"Quarto DELETADO com sucesso.\"}"));

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void pesquisa_Predio_Por_Nome_SucessoTest() {

        String nomePredio = geraPalavraRandomica(8);
        String idPredio = cadastrandoPredioSucesso(nomePredio);
        Assert.assertNotEquals("Falha", idPredio);

        String randomWord = geraPalavraRandomica(8);
        String url = "http://localhost:" + port + "/quarto/" + idPredio;

        String requestBody = "{\"tipo\":\"" + randomWord + "\"," +
                "\"pessoas\":\"10\"," +
                "\"camas\":\"5\"," +
                "\"moveis\":\"muitos moveis\"," +
                "\"banheiro\":\"5\"," +
                "\"valor\":\"200,00\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.getBody());

            String mensagem = jsonNode.get("Messagem").asText();
            String id = jsonNode.get("id").asText();

            Assert.assertEquals(mensagem, "Quarto CADASTRADO com sucesso.");

            url = "http://localhost:" + port + "/quarto/tipo/" + randomWord;
            requestEntity = new HttpEntity<>(headers);
            response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);

            String resp = "{\"id\":"+ id +"," +
                    "\"tipo\":\"" + randomWord + "\"," +
                    "\"pessoas\":\"10\"," +
                    "\"camas\":\"5\"," +
                    "\"moveis\":\"muitos moveis\"," +
                    "\"banheiro\":\"5\"," +
                    "\"valor\":\"200,00\"," +
                    "\"predioDto\":{\"id\":" + idPredio + "," +
                    "\"nome\":\"" + nomePredio + "\"," +
                    "\"localidadeDto\":null}}";

            Assert.assertTrue(response.getBody() != null && response.getBody().contains(resp));

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void pesquisa_Quarto_Por_Id_SucessoTest() {

        String nomeLocalidade = geraPalavraRandomica(8);
        String idLocalidade = cadastrandoLocalidadeSucesso(nomeLocalidade);
        Assert.assertNotEquals("Falha", idLocalidade);

        String randomWord = geraPalavraRandomica(8);
        String url = "http://localhost:" + port + "/predio/" + idLocalidade;

        String requestBody = "{\"nome\":\"" + randomWord + "\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.getBody());

            String mensagem = jsonNode.get("Messagem").asText();
            String id = jsonNode.get("id").asText();

            Assert.assertEquals(mensagem, "Predio CADASTRADO com sucesso.");

            url = "http://localhost:" + port + "/predio/" + id;
            requestEntity = new HttpEntity<>(headers);
            response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);

            String resp = "{\"id\":"+ id +"," +
                    "\"nome\":\"" + randomWord + "\"," +
                    "\"localidadeDto\":{\"id\":" + idLocalidade + "," +
                    "\"nome\":\"" + nomeLocalidade + "\"," +
                    "\"rua\":\"Nome da Rua\"," +
                    "\"cep\":null," +
                    "\"cidade\":\"Nome Cidade\"," +
                    "\"estado\":\"Nome Estado\"}}";

            Assert.assertTrue(response.getBody() != null && response.getBody().contains(resp));

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private String cadastrandoPredioSucesso(String randomWord) {

        String idLocalidade = cadastrandoLocalidadeSucesso(randomWord);
        Assert.assertNotEquals("Falha", idLocalidade);

        randomWord = geraPalavraRandomica(8);
        String url = "http://localhost:" + port + "/predio/" + idLocalidade;

        String requestBody = "{\"nome\":\"" + randomWord + "\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.getBody());

            String mensagem = jsonNode.get("Messagem").asText();
            String id = jsonNode.get("id").asText();

            Assert.assertEquals(mensagem, "Predio CADASTRADO com sucesso.");

            return id;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return  "Falha";
        }
    }

    private String cadastrandoLocalidadeSucesso(String randomWord) {

        String url = "http://localhost:" + port + "/localidade";

        String requestBody = "{\"nome\":\"" + randomWord + "\"," +
                "\"rua\":\"Nome da Rua\"," +
                "\"cep\":\"88000-000\"," +
                "\"cidade\":\"Nome Cidade\"," +
                "\"estado\":\"Nome Estado\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.getBody());

            String mensagem = jsonNode.get("Messagem").asText();
            String id = jsonNode.get("id").asText();

            Assert.assertEquals(mensagem, "Localidade CADASTRADA com sucesso.");

            return id;

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return  "Falha";
        }
    }

    private static String geraPalavraRandomica(int length) {
        String allowedChars = "abcdefghijklmnopqrstuvwxyz"; // caracteres permitidos
        Random random = new Random();
        StringBuilder word = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(allowedChars.length());
            char randomChar = allowedChars.charAt(randomIndex);
            word.append(randomChar);
        }

        return word.toString();
    }


}
