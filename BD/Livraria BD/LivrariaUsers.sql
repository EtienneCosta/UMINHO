--
-- Unidade Curricular de Bases de Dados.
-- Sistemas de Dados Relacionais.
--
-- U S E R S
--
-- Exemplos de Aplicação.
-- Costa, E., 2018.
--

-- Base de Dados de Trabalho.


-- Criação do administrador Bello Jr.
CREATE USER 'bellojr'@'localhost';
SET PASSWORD FOR 'bellojr'@'localhost' = 'root';


-- Caracterização geral de um utilizador em particular.
SELECT * 
	FROM mysql.user
	WHERE User = 'bellojr';


-- Permissão de acesso a todos os objetos de todas as bases de dados em 'localhost'.
GRANT ALL ON LivrariaBello.* TO 'bellojr'@'localhost';

-- Consulta dos privilégios atribuídos ao utilizador 'bellojr' em 'localhost'.
SHOW GRANTS FOR 'bellojr'@'localhost';


-- -----------------------**------------------------**----------------------------**----------------------------------------
-- Criação do perfil de Funcionário.
CREATE USER 'funcionario'@'localhost';
SET PASSWORD FOR 'funcionario'@'localhost' = 'root';


-- Definição de alguns previlégios para o utilizador 'funcionario'. 
-- Permissão para a execução de instruções SELECT,INSERT e UPDATE sobre a base de dados 
-- em 'localhost', apenas sobre as tabelas .
GRANT SELECT, INSERT,UPDATE ON LivrariaBello.Livro TO 'funcionario'@'localhost';
GRANT SELECT, INSERT,UPDATE ON LivrariaBello.Escritor TO 'funcionario'@'localhost';
GRANT SELECT, INSERT,UPDATE ON LivrariaBello.Categoria TO 'funcionario'@'localhost';
GRANT SELECT, INSERT,UPDATE ON LivrariaBello.Venda TO 'funcionario'@'localhost';
GRANT SELECT, INSERT,UPDATE ON LivrariaBello.Cliente TO 'funcionario'@'localhost';

-- Permissão para a  execução de procedimentos .
GRANT EXECUTE ON PROCEDURE LivrariaBello.* TO 'funcionario'@'localhost';
-- -----------------------**------------------------**----------------------------**----------------------------------------
-- Criação do perfil de Cliente.
CREATE USER 'cliente'@'localhost';
SET PASSWORD FOR 'cliente'@'localhost' = 'root';



-- E apenas de leitura sobre as tabelas Livro e Escritor.
GRANT SELECT ON LivrariaBello.Livro TO 'cliente'@'localhost';
GRANT SELECT ON LivrariaBello.Escritor TO 'cliente'@'localhost';
-- -----------------------**------------------------**----------------------------**----------------------------------------

