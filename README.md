**FiapHotel | Hackathon Totalmente desnecessário no meio do projeto da 5a fase Pos Tech Fiap**


![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Badge em Desenvolvimento](http://img.shields.io/static/v1?label=STATUS&message=EM%20DESENVOLVIMENTO&color=GREEN&style=for-the-badge)


*******

<br>


## Subindo Ambiente Desenvolvimento

Na pasta AmbienteDev subis docker-compose

Esse comando só é necessário uma vez, ou depois de alguma alteração no docker-compose
```shell
# docker compose build
```

Sobe um docker com postgres e outro com PgAdmin
```shell
# docker compose up
```

Para e desaloca os containers
```shell
# docker compose down
```

<br>

### Comandos cUrl para: Clientes

#### &emsp;&emsp; Cadastro Cliente
```shell
curl -i -X POST "http://localhost:8080/cliente" -H "Content-Type:application/json" -d '{"pais":"Brasil","cpf":"999.999.999-99","passaporte":"998998998","nome":"nome cliente","datanascimento":"02/02/1900","telefone":"55-48-98888.8888","email":"email@host.com.br"}'
```

#### &emsp;&emsp; Consulta Cliente Por Id
```shell
curl -i -X GET "http://localhost:8080/cliente/17" 
```

#### &emsp;&emsp; Consulta Cliente Por Nome
```shell
curl -i -X GET "http://localhost:8080/cliente/nome/nome%20cliente" 
```

#### &emsp;&emsp; Altera Cliente
```shell
curl -i -X PUT "http://localhost:8080/cliente/17" -H "Content-Type:application/json" -d '{"pais":"EUA","cpf":"888.888.888-88","passaporte":"8888888888","nome":"Novo nome cliente","datanascimento":"01/01/1901","telefone":"55-48-99999.9999","email":"novoemail@host.com.br"}'
```

#### &emsp;&emsp; Deleta Cliente
```shell
curl -i -X DELETE "http://localhost:8080/cliente/17"
```

*******

<br>

### Comandos cUrl para: Servicos

#### &emsp;&emsp; Cadastro Servicos
```shell
curl -i -X POST "http://localhost:8080/servicos" -H "Content-Type:application/json" -d '{"nome":"manicure","valor":"135,00"}'
```

#### &emsp;&emsp; Consulta Servicos Por Id
```shell
curl -i -X GET "http://localhost:8080/servicos/1" 
```

#### &emsp;&emsp; Consulta Servicos Por Nome
```shell
curl -i -X GET "http://localhost:8080/servicos/nome/manicure" 
```

#### &emsp;&emsp; Altera Servicos
```shell
curl -i -X PUT "http://localhost:8080/servicos/1" -H "Content-Type:application/json" -d '{"nome":"manicure","valor":"255,00"}'
```

#### &emsp;&emsp; Deleta Servicos
```shell
curl -i -X DELETE "http://localhost:8080/servicos/1"
```

*******

<br>

### Comandos cUrl para: Item

#### &emsp;&emsp; Cadastro Item
```shell
curl -i -X POST "http://localhost:8080/item" -H "Content-Type:application/json" -d '{"nome":"Bolo","valor":"135,00"}'
```

#### &emsp;&emsp; Consulta Item Por Id
```shell
curl -i -X GET "http://localhost:8080/item/2" 
```

#### &emsp;&emsp; Consulta Item Por Nome
```shell
curl -i -X GET "http://localhost:8080/item/nome/Bolo" 
```

#### &emsp;&emsp; Altera Item
```shell
curl -i -X PUT "http://localhost:8080/item/1" -H "Content-Type:application/json" -d '{"nome":"Suco","valor":"55,00"}'
```

#### &emsp;&emsp; Deleta Item
```shell
curl -i -X DELETE "http://localhost:8080/item/2"
```

*******

<br>

### Comandos cUrl para: Localidade

#### &emsp;&emsp; Cadastro Localidade
```shell
curl -i -X POST "http://localhost:8080/localidade" -H "Content-Type:application/json" -d '{"nome":"Sergipe","rua":"Nome da Rua","cep":"88000-000","cidade":"São José","estado":"Santa Catarina"}'
```

#### &emsp;&emsp; Consulta Localidade Por Id
```shell
curl -i -X GET "http://localhost:8080/localidade/1" 
```

#### &emsp;&emsp; Consulta Localidade Por Nome
```shell
curl -i -X GET "http://localhost:8080/localidade/nome/Sergipe" 
```

#### &emsp;&emsp; Altera Localidade
```shell
curl -i -X PUT "http://localhost:8080/localidade/1" -H "Content-Type:application/json" -d '{"nome":"São Paulo","rua":"Novo Nome da Rua","cep":"90000-000","cidade":"São Paulo","estado":"SP"}'
```

#### &emsp;&emsp; Deleta Localidade
```shell
curl -i -X DELETE "http://localhost:8080/localidade/1"
```

*******

### Docker do Projeto

# docker build -t fiaphotel .

# docker run -p 8080:8080 FiapHotel
