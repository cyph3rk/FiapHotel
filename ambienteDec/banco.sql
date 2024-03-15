-- Database: base_dev

BEGIN;

CREATE TABLE IF NOT EXISTS public.localidade
(
    id     serial NOT NULL,
    nome   text NOT NULL,
    rua    text NOT NULL,
    cep    text NOT NULL,
    cidade text NOT NULL,
    estado text NOT NULL,
    PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS public.predio
(
    id serial NOT NULL,
    id_localidade serial NOT NULL,
    nome    text NOT NULL,
    PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS public.quarto
(
    id serial NOT NULL,
    id_predio serial NOT NULL,
    tipo     text NOT NULL,
    pessoas  text NOT NULL,
    camas    text NOT NULL,
    moveis   text NOT NULL,
    banheiro text NOT NULL,
    valor    text NOT NULL,
    PRIMARY KEY (id)
    );

ALTER TABLE IF EXISTS public.predio
    ADD FOREIGN KEY (id_localidade)
    REFERENCES public.localidade (id) MATCH SIMPLE
    ON UPDATE NO ACTION
       ON DELETE NO ACTION
    NOT VALID;

ALTER TABLE IF EXISTS public.quarto
    ADD FOREIGN KEY (id_predio)
    REFERENCES public.predio (id) MATCH SIMPLE
    ON UPDATE NO ACTION
       ON DELETE NO ACTION
    NOT VALID;




INSERT INTO localidade (id, nome, rua, cep, cidade, estado)
VALUES (10, 'Fazenda da Pós Tech', 'Rua da Pós Tech, 567', '01000-002', 'Cidade da Pós Tech', 'Estado da Pós Tech');

INSERT INTO predio (id, id_localidade, nome) VALUES (10, 10, 'Casa Principal');

INSERT INTO quarto (id, id_predio, tipo, pessoas, camas, moveis, banheiro, valor)
VALUES (10, 10, 'Standart Simples', '4', '1 x Queen Size',
        '1 x Sofá, 1 x Poltronas, 1 x Frigobar, 1 x TV Led 54 pols., 1 x Mesa de Escritório, 1 x Cadeira de Escritório',
        'Simples (Box com Ducha, Privada, Ducha higiênica e pia com espelho)', '350,00');

END;
