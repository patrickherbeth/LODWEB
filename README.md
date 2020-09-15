
# Projeto gerenciamento de obra e autor

Este projeto tem como finalidade o gerenciamento de obra e autores.

## Instalação
Para instalação é nescessário ter instalado o banco de dados MySql. O script abaixo cria o banco e as tabelas nescessárias

```shell

CREATE DATABASE IF NOT EXISTS `stefanini` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `stefanini`;

CREATE TABLE IF NOT EXISTS `autor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `birthdate` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `cpf` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `sex` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `livro` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `description` varchar(50) DEFAULT NULL,
  `image` varchar(50) DEFAULT NULL,
  `publicationdate` varchar(50) DEFAULT NULL,
  `exposuredate` varchar(50) DEFAULT NULL,
  `idautor` int(11) DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `fk_autor` (`idautor`)
) ENGINE=MyISAM AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4;

```
### Instalação das dependências

Para a instalação das dependencias foi utilizado o "MAVEN" que é o gerenciados de dependência "JAVA"

```shell
maven clean install
```

### Paginação e Filtragem

Foi criado o filtro para busca de autores por nome e cpf. Também foi criada a consulta para filtrar  livros por nome e descrição.
Abaixo as rotas utilizadas:

Filtar autor por nome ou CPF:
```shell
http://localhost:8080/page/search_autor?searchTerm=[NOME OU CPF]
```

Filtar todos autores:
```shell
http://localhost:8080/page/find_all_autor
```

Filtar alivro por nome ou descrição:
```shell
http://localhost:8080/page/search_livro?searchTerm=[NOME OU CPF]
```

Filtar todos livros:
```shell
http://localhost:8080/page/find_all_obras
```
