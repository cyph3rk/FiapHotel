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

CREATE TABLE IF NOT EXISTS public.servicos
(
    id serial NOT NULL,
    nome serial NOT NULL,
    valor     text NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.item
(
    id serial NOT NULL,
    nome serial NOT NULL,
    valor     text NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.cliente
(
    id serial NOT NULL,
    pais           text NOT NULL,
    cpf            text NOT NULL,
    passaporte     text NOT NULL,
    nome           text NOT NULL,
    datanascimento text NOT NULL,
    telefone       text NOT NULL,
    email          text NOT NULL,
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

END;
