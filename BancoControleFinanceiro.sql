CREATE DATABASE BancoControleFinanceiro;
-- DROP DATABASE BancoControleFinanceiro;

USE BancoControleFinanceiro;

CREATE TABLE Usuario (
    login VARCHAR(20) PRIMARY KEY,
    senha VARCHAR(20) NOT NULL
);

CREATE TABLE Banco (
    id INT PRIMARY KEY AUTO_INCREMENT,
    usuario VARCHAR(20) NOT NULL,
    nome VARCHAR(20) NOT NULL,
    FOREIGN KEY (usuario)
        REFERENCES Usuario (login)
);

CREATE TABLE MovimentoFinanceiro (
    data DATE NOT NULL,
    id INT PRIMARY KEY AUTO_INCREMENT,
    banco INT NOT NULL,
    nome VARCHAR(20) NOT NULL,
    valor DECIMAL(9 , 2 ) NOT NULL,
    recorrente BOOLEAN NOT NULL,
    variavel BOOLEAN NOT NULL,
    FOREIGN KEY (banco)
        REFERENCES Banco (id)
);