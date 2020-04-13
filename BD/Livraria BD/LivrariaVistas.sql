--
-- Unidade Curricular de Bases de Dados.
-- Sistemas de Dados Relacionais.
--
-- V I S T A S 
-- Operações de Descrição de Dados.
-- Criação, alteração e remoção de vistas.
--
-- Exemplos de Aplicação.
-- Costa, E., 2018.
--

-- Base de Dados de Trabalho.


-- Vistas de um Administrador: Informação relevante sobre um funcionário tal como o total arrecadado para loja.



-- Vista de Funcionários: 

CREATE VIEW Faturas AS 
select idVenda,F.Nome AS Funcionario,C.Nome AS Cliente,`Data`,Cupao,Total FROM Venda
	INNER JOIN Funcionario AS F ON F.idFuncionario=Venda.Funcionario
	INNER JOIN Cliente AS C ON C.idCliente=Venda.Cliente
	Order by IdVenda;


-- Vista dos Cliente:

CREATE VIEW ListadeLivro AS 
select Isbn,Titulo,Editora,Edicao,Stock,Ano,Paginas,Preco,Idioma,C.Genero,E.Nome AS Escritor from Livro
	INNER JOIN Escritor AS E on E.idEscritor=Livro.Escritor
	INNER JOIN Categoria AS C on C.idCategoria=Livro.Categoria 
	Order by Isbn;



select * from ListadeLivro;