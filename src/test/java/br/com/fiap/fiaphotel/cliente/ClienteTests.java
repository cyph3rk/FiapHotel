package br.com.fiap.fiaphotel.cliente;

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
class ClienteTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void salva_Cliente_SucessoTest() {
        String randomWord = geraPalavraRandomica(8);
        String id = cadastrandoClienteSucesso(randomWord);
        Assert.assertNotEquals("Falha", id);
    }

    @Test
    public void altera_Cliente_SucessoTest() {

        String randomWord = geraPalavraRandomica(8);
        String id = cadastrandoClienteSucesso(randomWord);
        Assert.assertNotEquals("Falha", id);

        String url = "http://localhost:" + port + "/cliente/" + id;

        String requestBody = "{\"pais\":\"Brasil\"," +
                "\"cpf\":\"999.999.999-99\"," +
                "\"passaporte\":\"998998998\"," +
                "\"nome\":\"" + randomWord + "\"," +
                "\"datanascimento\":\"Brasil\"," +
                "\"telefone\":\"55-48-98888.8888\"," +
                "\"email\":\"email@host.com.br\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        String resp = "{\"id\":"+ id +"," +
                "\"pais\":\"Brasil\"," +
                "\"cpf\":\"999.999.999-99\"," +
                "\"passaporte\":\"998998998\"," +
                "\"nome\":\"" + randomWord + "\"," +
                "\"datanascimento\":\"Brasil\"," +
                "\"telefone\":\"55-48-98888.8888\"," +
                "\"email\":\"email@host.com.br\"}";

        Assert.assertTrue(response.getBody() != null && response.getBody().contains(resp));
    }

    @Test
    public void deleta_Cliente_SucessoTest() {

        String randomWord = geraPalavraRandomica(8);
        String id = cadastrandoClienteSucesso(randomWord);
        Assert.assertNotEquals("Falha", id);

        String url = "http://localhost:" + port + "/cliente/" + id;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, String.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        Assert.assertTrue(response.getBody() != null && response.getBody().contains("{\"Mensagem\": \"Cliente DELETADO com sucesso.\"}"));

    }

    @Test
    public void pesquisa_Cliente_Por_Nome_SucessoTest() {

        String randomWord = geraPalavraRandomica(8);
        String id = cadastrandoClienteSucesso(randomWord);
        Assert.assertNotEquals("Falha", id);

        String url = "http://localhost:" + port + "/cliente/nome/" + randomWord;


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());


        String resp = "{\"id\":" + id + "," +
                "\"pais\":\"Brasil\"," +
                "\"cpf\":\"999.999.999-99\"," +
                "\"passaporte\":\"998998998\"," +
                "\"nome\":\"" + randomWord + "\"," +
                "\"datanascimento\":\"Brasil\"," +
                "\"telefone\":\"55-48-98888.8888\"," +
                "\"email\":\"email@host.com.br\"}";

        Assert.assertTrue(response.getBody() != null && response.getBody().contains(resp));

    }

    @Test
    public void pesquisa_Cliente_Por_Id_SucessoTest() {

        String randomWord = geraPalavraRandomica(8);
        String id = cadastrandoClienteSucesso(randomWord);
        Assert.assertNotEquals("Falha", id);

        String url = "http://localhost:" + port + "/cliente/" + id;


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());


        String resp = "{\"id\":" + id + "," +
                "\"pais\":\"Brasil\"," +
                "\"cpf\":\"999.999.999-99\"," +
                "\"passaporte\":\"998998998\"," +
                "\"nome\":\"" + randomWord + "\"," +
                "\"datanascimento\":\"Brasil\"," +
                "\"telefone\":\"55-48-98888.8888\"," +
                "\"email\":\"email@host.com.br\"}";

        Assert.assertTrue(response.getBody() != null && response.getBody().contains(resp));

    }

    private String cadastrandoClienteSucesso(String randomWord) {

        String url = "http://localhost:" + port + "/cliente";

        String requestBody = "{\"pais\":\"Brasil\"," +
                "\"cpf\":\"999.999.999-99\"," +
                "\"passaporte\":\"998998998\"," +
                "\"nome\":\"" + randomWord + "\"," +
                "\"datanascimento\":\"Brasil\"," +
                "\"telefone\":\"55-48-98888.8888\"," +
                "\"email\":\"email@host.com.br\"}";

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

            Assert.assertEquals(mensagem, "Cliente CADASTRADO com sucesso.");

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
