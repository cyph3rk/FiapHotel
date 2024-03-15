package br.com.fiap.fiaphotel.servicos;

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
class ItemTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void salva_Item_SucessoTest() {
        String randomWord = geraPalavraRandomica(8);
        String id = cadastrandoServicoSucesso(randomWord);
        Assert.assertNotEquals("Falha", id);
    }

    @Test
    public void altera_Servico_SucessoTest() {

        String randomWord = geraPalavraRandomica(8);
        String id = cadastrandoServicoSucesso(randomWord);
        Assert.assertNotEquals("Falha", id);

        String url = "http://localhost:" + port + "/servicos/" + id;

        String requestBody = "{\"nome\":\"" + randomWord + "\"," +
                "\"valor\":\"200,00\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        String resp = "{\"id\":"+ id +"," +
                    "\"nome\":\"" + randomWord + "\"," +
                    "\"valor\":\"200,00\"}";

        Assert.assertTrue(response.getBody() != null && response.getBody().contains(resp));
    }

    @Test
    public void deleta_Servico_SucessoTest() {

        String randomWord = geraPalavraRandomica(8);
        String id = cadastrandoServicoSucesso(randomWord);
        Assert.assertNotEquals("Falha", id);

        String url = "http://localhost:" + port + "/servicos/" + id;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, String.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        Assert.assertTrue(response.getBody() != null && response.getBody().contains("{\"Mensagem\": \"Servicos DELETADA com sucesso.\"}"));

    }

    @Test
    public void pesquisa_Servico_Por_Nome_SucessoTest() {

        String randomWord = geraPalavraRandomica(8);
        String id = cadastrandoServicoSucesso(randomWord);
        Assert.assertNotEquals("Falha", id);

        String url = "http://localhost:" + port + "/servicos/nome/" + randomWord;


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());


        String resp = "{\"id\":" + id + "," +
                    "\"nome\":\"" + randomWord + "\"," +
                    "\"valor\":\"135,00\"}";

            Assert.assertTrue(response.getBody() != null && response.getBody().contains(resp));

    }

    @Test
    public void pesquisa_Servico_Por_Id_SucessoTest() {

        String randomWord = geraPalavraRandomica(8);
        String id = cadastrandoServicoSucesso(randomWord);
        Assert.assertNotEquals("Falha", id);

        String url = "http://localhost:" + port + "/servicos/" + id;


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());


        String resp = "{\"id\":" + id + "," +
                "\"nome\":\"" + randomWord + "\"," +
                "\"valor\":\"135,00\"}";

        Assert.assertTrue(response.getBody() != null && response.getBody().contains(resp));

    }

















    private String cadastrandoServicoSucesso(String randomWord) {

        String url = "http://localhost:" + port + "/servicos";

        String requestBody = "{\"nome\":\"" + randomWord + "\"," +
                "\"valor\":\"135,00\"}";

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

            Assert.assertEquals(mensagem, "Servicos CADASTRADA com sucesso.");

            return id;

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return  "Falha";
        }
    }

    private static String geraPalavraRandomica(int length) {
        String allowedChars = "abcdefghijklmnopqrstuvwxyz";
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
