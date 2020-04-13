-- ------------------------------------------------------
-- ------------------------------------------------------
-- Universidade do Minho
-- Mestrado Integrado em Engenharia Informática
-- Lincenciatura em Ciências da Computação
-- Unidade Curricular de Bases de Dados
-- 
-- Caso de Estudo: LivrariaBello - Loja de livros
-- Povoamento inicial da base de dados
-- Novembro/2018
-- ------------------------------------------------------

-- ------------------------------------------------------
--
-- Esquema: "Loja de livros."
USE `LivrariaBello` ;

--
-- Permissão para fazer operações de remoção de dados.
SET SQL_SAFE_UPDATES = 0;



-- ------------------------------------------------------
-- ------------------------------------------------------
-- Universidade do Minho
-- Mestrado Integrado em Engenharia Informática
-- Lincenciatura em Ciências da Computação
-- Unidade Curricular de Bases de Dados
-- 
-- Caso de Estudo: LivrariaBello - Loja de Livros
-- Povoamento inicial da base de dados
-- Novembro/2018
-- ------------------------------------------------------
-- ------------------------------------------------------


--
-- Esquema: "Loja de livros"
USE `LivrariaBello` ;

--
-- Permissão para fazer operações de remoção de dados.
SET SQL_SAFE_UPDATES = 0;


-- Povoamento da tabela "Cliente"
INSERT INTO Cliente
	(idCliente, Nome,DataNascimento,Localidade,Rua,CodigoPostal, Telefone,Email)
	VALUES 
		(1, 'Anabela Franqueira Alves Rodrigues', '1963/01/24', 'Matosinhos' , 'Avenida Meneres nº234 Bloco 8 1ºesquerdo', 
			'4715-292 Porto',   '916275555' ,  'anabela.a.alves@gmail.com'),
		(2, 'Marisa Tatiana Morais de Picao', 	    '1994/08/07', 'Vila Nova de Gaia', 		'Avenida das Poias nº12 2ºdireito', 
			'4450-189 Porto',   '916473543' ,  'marisamorais@gmail.com'),
		(3, 'Rita Maria Ferreira', 				    '1996/05/18', 'Lisboa', 				'Rua do Canudo nº15 1ºdireito', 
			'3456-133 Lisboa',  '911139494' ,  'ritaferreira@gmail.com'),
		(4, 'Luís Filipe Pereira Machado', 		    '1962/01/07', 'Coimbra', 				'Praceta João Beltrão nº12 4ºesq. frente', 
			'3923-456 Coimbra', '935072034' , 'lfpm1962@gmail.com'),
		(5, 'Daniela Costa Martins Coimbra e Cruz', '1989/04/16', 'Faro' , 					'Rua da Baixada Porta 8', 
			'4715-123 Algarve', '911901514' ,  'danielacruz94@gmail.com'),
		(6, 'Inês Gonçalves Barros',			 	'1994/01/26', 'Braga', 					'Rua Maria Feliciana nº130 2ºesq. frente', 
			'4715-234 Braga',   '916808958' ,  'ines_barros@gmail.com'),
		(7, 'Mariana Rosário de Matos Araújo', 		'1994/07/05', 'Aveiro', 				'Rua Luís de Camões nº27 5ºesq. trás', 
			'4715-456 Aveiro',  '917645367',   'mariana_araujo@gmail.com'),
		(8, 'Tiago Filipe Fernardes Vilaça', 		'1987/10/04', 'Vila Nova de Famalicão', 'Rua da Palmeira nº9 1ºesquerdo',
			'4715-678 Braga',   '917564297' ,  'tffv_1994@gmail.com'),
		(9, 'Pedro Miguel Marinho Almeida', 		'1993/04/15', 'Leça da Palmeira', 		'Rua da Igreja nº13',
			'4715-789 Porto',   '917283647' ,  'impiglet@gmail.com'),
		(10, 'João Carlos Farelo Marta da Cruz', 	'1964/01/12', 'São Vítor', 				'Rua Maria de Sousa nº4 4ºesq. trás',
			'4715-148 Braga',   '912749675' ,  'joaomcruz@gmail.com'),
		(11, 'Ana Sofia Franqueira Marta da Cruz', 	'1996/07/17', 'Matosinhos', 			'Rua das Forças Armadas nº8 2ºesq. trás', 
			'4450-194 Porto',   '934678278',   'anasofia_Cruz@gmail.com'),
		(12, 'Cátia Franqueira Marta da Cruz',		'1991/01/12', 'Porto', 					'Rua do Pinheiro nº200 2ºesq. frente',
			'4450-106 Porto',   '934585619',   'catiacruz_1991@gmail.com'),
		(13, 'António Gonçalves da Costa Gomes',	'1992/08/23', 'Braga', 					'Rua São José nº1 2ºesq. trás',
			'4450-183 Braga',   '936281967' ,  'ozil_gomes@gmail.com'),
		(14, 'Nuno André Morais Ribeiro', 			'1991/03/29', 'Lisboa', 				'Rua Almeida Garret nº15 2ºandar',
			'4450-185 Lisboa',  '912637486',   'nunoandre91@gmail.com'),	
		(15, 'Margarida da Silva Ferreira', 		'1983/06/17', 'Faro', 					'Rua do Caixo nº18 7ºfrente',
			'4450-190 Faro',    '934728917' ,  'guidaferreira@gmail.com');




-- Povoamento da tabela "Funcionario"
INSERT INTO Funcionario
	(idFuncionario, Nome,DataNascimento, Localidade, Rua,CodigoPostal, Telefone, Vencimento, Email)
	VALUES 
		(1, 'Maria da Conceição Cruz','1963/01/24', 'Matosinhos' , 'Avenida Meneres nº234 Bloco 8 1ºesquerdo', 
			'4715-292 Porto',  '916275555' , 1000, 'mariacruz@gmail.com'),

		(2, 'Beatriz Silva Vilaça', 			'1994/08/07', 'Vila Nova de Gaia', 		'Avenida das Poias nº12 2ºdireito', 
			'4450-189 Porto',  '916473543',  950.50, 'beatrizvilaça@gmail.com'),

		(3, 'Beatriz Costa Ferreira', 			'1996/05/18','Lisboa', 					'Rua do Canudo nº15 1ºdireito', 
			'3456-133 Lisboa',  '911139494', 700.50, 'beatrizcosta@gmail.com'),

		(4, 'Alexandra Sousa de Carvalho', 		'1962/01/07', 'Coimbra', 				'Praceta João Beltrão nº12 4ºesq. frente', 
			'3923-456 Coimbra', '935072034', 450.50, 'alex_carvalho@gmail.com'),
		
		(5, 'João Miguel Couto',				'1989/04/16', 'Faro' , 					'Rua da Baixada Porta 8', 
			'4715-123 Algarve', '911901514', 1000, 'joaocouto@gmail.com'),
		(6, 'José Pedro Carvalho',				'1994/01/26', 'Braga', 					'Rua Maria Feliciana nº130 2ºesq. frente', 
			'4715-234 Braga',   '916808958', 450.50, 'zecarvalho@gmail.com'),
		(7, 'André Maria Gonçalves', 			'1994/07/05', 'Aveiro', 				'Rua Maria Feliciana nº130 2ºesq. frente', 
			'4715-456 Aveiro',  '917645367', 1000, 'amg_1994@gmail.com'),
		(8, 'Leonardo Teixeira Fangueiro', 		'1987/10/04', 'Vila Nova de Famalicão', 'Rua Maria Feliciana nº130 2ºesq. frente',
			'4715-678 Braga',   '917564297', 950.50, 'leo_fangueiro@gmail.com'),
		(9, 'Eduardo Nova Silva', 				'1993/04/15', 'Leça da Palmeira',       'Rua da Igreja nº13',
			'4715-789 Porto',   '917283647', 700.50, 'eduardosilva@gmail.com'),
		(10, 'Maria João Coutinho', 			'1964/01/12', 'São Vítor', 				'Rua Maria de Sousa nº4 5ºesq. frente',
			'4715-148 Braga',   '912749675', 1000, 'mariacoutinho@gmail.com'),
		(11, 'João Eduardo Ferreira', 			'1996/07/17', 'Matosinhos', 			'Rua das Forças Armadas nº8 2ºesq. frente', 
			'4450-194 Porto',   '934678278', 950.50, 'joaoferreira@gmail.com'),
		(12, 'André Pereira Marques',			'1991/01/12', 'Porto', 					'Rua do Pinheiro nº200 2ºesq. frente',
			'4450-106 Porto',   '934585619', 700.50, 'andremarques_1991@gmail.com'),
		(13, 'Rogério Gomes Moreira',			'1992/08/23', 'Braga', 					'Rua São José nº1 2ºesq. trás',
			'4450-183 Braga',   '936281967', 450.50, 'rogerio_gomes@gmail.com'),
		(14, 'Samuel Gonçalves Ferreira', 		'1991/03/29', 'Lisboa', 				'Rua Almeida Garret nº15 2ºandar',
			'4450-185 Lisboa',  '912637486', 950.50, 'samu_ferreira@gmail.com'),
		(15, 'Tiago Filipe Sá', 				'1983/06/17', 'Faro', 					'Rua do Caixo nº18 7ºfrente',
			'4450-190 Faro',    '934728917', 700.50, 'tiagofilipesa@gmail.com'),
		(16, 'Ricardo Manuel de Sousa e Pinto', '1963/01/24', 'Matosinhos' , 			'Avenida Meneres nº234 Bloco 8 1ºesquerdo', 
			'4715-292 Porto',   '916275555', 1000, 'ricardopinto@gmail.com'),
		(17, 'Pedro Miguel Barroso', 			'1994/08/07', 'Vila Nova de Gaia', 		'Avenida das Poias nº12 2ºdireito', 
			'4450-189 Porto',   '916473543', 700.50, 'pedrobarroso@gmail.com'),
		(18, 'Nuno Rosário dos Reis', 			'1996/05/18', 'Lisboa', 				'Rua do Canudo nº15 1ºdireito', 
			'3456-133 Lisboa',  '911139494', 950.50, 'nuno_reis@gmail.com'),
		(19, 'Liliana de Castro Pereira', 		'1962/01/07', 'Coimbra', 				'Praceta João Beltrão nº12 4ºesq. frente', 
			'3923-456 Coimbra', '935072034', 450.50, 'lilianapereira1@gmail.com'),
		(20, 'Nuno Bento Armada',				'1989/04/16', 'Faro' , 					'Rua da Baixada Porta 8', 
			'4715-123 Algarve', '911901514', 1000, 'nunoarmada89@gmail.com'),
		(21, 'Mariana Fernandes de Brito',		'1994/01/26', 'Braga', 					'Rua Maria Feliciana nº130 2ºesq. frente', 
			'4715-234 Braga',   '916808958', 950.50, 'marianabrito94@gmail.com'),
		(22, 'Marcelo Filipe Lima', 			'1994/07/05', 'Aveiro', 				'Rua Maria Feliciana nº130 2ºesq. frente', 
			'4715-456 Aveiro',  '917645367', 700.50, 'marcelolima94@gmail.com'),
		(23, 'Paulo Castro de Freitas', 		'1987/10/04', 'Vila Nova de Famalicão', 'Rua Maria Feliciana nº130 2ºesq. frente',
			'4715-678 Braga',   '917564297', 450.50, 'paulofreitas@gmail.com'),
		(24, 'Luis Leite Ferraz', 				'1993/04/15', 'Leça da Palmeira', 		'Rua da Igreja nº13',
			'4715-789 Porto',   '917283647', 950.50, 'luisferraz@gmail.com'),
		(25, 'José Gomes da Cunha', 			'1964/01/12', 'São Vítor', 				'Rua Maria de Sousa nº4 5ºesq. frente',
			'4715-148 Braga',   '912749675', 700.50, 'josedacunha@gmail.com'),
		(26, 'João Miguel Amorim Fernandes', 	'1996/07/17', 'Matosinhos', 			'Rua das Forças Armadas nº8 2ºesq. frente', 
			'4450-194 Porto',   '934678278', 950.50, 'joaoamorim@gmail.com'),
		(27, 'Joana Maria Feijó',				'1991/01/12', 'Porto', 					'Rua do Pinheiro nº200 2ºesq. frente',
			'4450-106 Porto',   '934585619', 1000, 'joanafeijo@gmail.com'),
		(28, 'Inês Guerreiro Vasconcelos',		'1992/08/23', 'Braga', 					'Rua São José nº1 2ºesq. trás',
			'4450-183 Braga',   '936281967', 950.50, 'inesvasconcelos@gmail.com'),
		(29, 'Fábio Vieira Mendes', 			'1991/03/29', 'Lisboa', 				'Rua Almeida Garret nº15 2ºandar',
			'4450-185 Lisboa',  '912637486', 450.50, 'fabiomendes91@gmail.com'),
		(30, 'Bruna Carvalho Cardoso', 			'1983/06/17', 'Faro', 					'Rua do Caixo nº18 7ºfrente',
			'4450-190 Faro',    '934728917', 1000, 'brunacardoso@gmail.com');


-- Povoamento da tabela "Escritor"
INSERT INTO Escritor 
	(idEscritor,Nome,Nacionalidade,DataNascimento,Biografia)
	VALUES
		(1,'Jorge Amado','Brasileiro','1912/08/10','Bio1'),
		(2,'Paulo Coelho','Brasileiro','1947/08/24','Bio2'),
		(3,'José Saramago','Português','1922/11/16','Bio3'),
		(4,'Eça de Queiroz','Português','1845/11/25','Bio4'),
		(5,'Fiódor Dostoiévski','Russo','1821/10/30','Bio5'),
		(6,'Augusto Cury','Brasileiro','1958/10/02','Bio6'),
		(7,'José Bernado Barros','Português','1965/05/22','Bio7'),
		(8,'Henry Miller','Norte Americano','1891/12/26','Bio8'),
		(9,'Charles Dickens','Britânico','1812/02/07','Bio9'),
		(10,'Machado de Assis','Brasileiro','1839/06/21','Bio10'),
		(11,'Michel de Montaigne','Francês','1533/02/28','Bio11'),
		(12,'Joanne Murray','Britânica','1965/07/31','Bio12'),
		(13,'Stephen King','Norte Americano','1947/09/21','Bio13'),
		(14,'Pepetela','Angolano','1941/10/29','Bio14'),
		(15,'Mia Couto','Moçambicano','1955/07/05','Bio15');

-- Povoamento da tabela "Categoria"

INSERT INTO Categoria
	(idCategoria,Genero)
	VALUES
	(1,'Romance'),
	(2,'Gastronomia'),
	(3,'Ficção'),
	(4,'Filosofia'),
	(5,'História'),
	(6,'Guerra'),
	(7,'Medicina'),
	(8,'Música'),
	(9,'Psicologia'),
	(10,'Terror'),
	(11,'Contos'),
	(12,'Policial'),
	(13,'Teatro'),
	(14,'Quadrinhos'),
	(15,'Thriller'),
	(16,'Drama'),
	(17,'Educação');



-- Povoamento da tabela "Livro"
INSERT INTO Livro
	(Isbn,Titulo,Editora,Edicao,Ano,Stock,Paginas,Preco,Idioma,Categoria,Escritor)
	VALUES
		(1,'Sua Excelência de Corpo Presente','Dom Quixote',9,2018,15,272,15.21,'Português',1,14),
		(2,'Parábola do Cágado velho','Dom Quixote',12,2006,13,160,13.90,'Português',1,14),
		(3,'Se o Passado Não Tivesse Asas','Dom Quixote',5,2016,10,384,18.90,'Português',1,14),
		(4,'A Montanha da Água Lilas','Bis',1,2009,20,160,5.95,'Português',1,14),
		(5, 'A Confissão da Leoa', 'Editorial Caminho' , 4 , 2012 ,25, 272, 12.90 ,'Português',1,15),
		(6, 'O Beijo da Palavrinha', 'Editorial Caminho' , 11 , 2008 ,30, 32, 12.90 ,'Português',3,15),
		(7, 'Mar Me Quer', 'Editorial Caminho' , 9 , 2012 ,7, 72, 9.90 ,'Português',1,15),
		(8, 'A Varanda do Frangipani', 'Editorial Caminho' , 4 , 2000 ,10, 154, 12.90 ,'Português',1,15),
		(9, 'IT', 'HODDER & STOUGHTON GENERAL DIVISION' , 5 , 2011 ,45, 1184, 13.28 ,'Inglês',15,13),
		(10, 'The Shining', '11X17' , 1 , 2017 ,33, 632, 11.00 ,'Inglês',15,13),
		(11, 'Elevation', 'HODDER & STOUGHTON GENERAL DIVISION' , 10 , 2018 ,20, 160, 16.30 ,'Inglês',15,13),
		(12, 'Sr.Mercedes', 'Bertrand Editora' , 3 , 2017 ,5, 472, 18.80 ,'Português',15,13),
		(13, 'Fantastic Beasts: The Crimes Of Grindelwald', 'Little,Brown Book Group' , 11 , 2018 ,13,304, 15.96 ,'Inglês',11,12),
		(14, 'Harry Potter e a Pedra Filosofal', 'Editorial Presença' , 4 , 2002 ,10,260, 15.90 ,'Português',11,12),
		(15, 'Uma Morte Súbita', 'Editorial Presença' , 11 , 2012 ,15,496, 22.90 ,'Português',1,12),
		(16, 'Fantastic Beasts And Where To Find Them', 'Little,Brown Book Group' , 7 , 2018 ,5,304, 8.69 ,'Inglês',12,12),
		(17, 'Dictionnaire DE Michek De Montaigne', 'Classiques Garnier' , 11 , 2018 ,15,2000, 56.10 ,'Francês',9,11),
		(18, 'Essais', 'Gallimard' , 9 , 2009 ,20,10, 27.91 ,'Francês',1,11),
		(19, 'Vivre à Propos', 'Flammarion' , 3 , 2009 ,7,256, 24.17 ,'Francês',4,11),
		(20, 'Ensaios-Antologia', 'Relógio D Água', 4 , 1998 ,3,336, 32.72 ,'Português',5,11),
		(21, 'O Alienista', 'Porto Editora' , 10 , 2017 ,20,112, 8.80 ,'Português',11,10),
		(22, 'Dom Casmurro', 'BIS' , 3 , 2013 ,10,320, 7.50 ,'Português',1,10),
		(23, 'Casa Velha', 'Narrativa' , 6 , 2018 ,5,128, 10.80 ,'Português',1,10),
		(24, 'Memórias Póstumas de Brás Cubas', 'Relógio D Água' , 02 , 2017 ,15,232, 10.00 ,'Português',16,10),
		(25, 'Oliver Twist', 'Engage Books' , 11 , 2018 ,20,400, 48.29 ,'Inglês',3,9),
		(26, 'The Old Curiosity Shop', 'Engage Books' , 11 , 2018 ,9,508, 54.32 ,'Inglês',3,9),
		(27, 'A Tale of Two Cities', 'Engage Books' , 11 , 2018 ,11,344, 54.32 ,'Inglês',3,9),
		(28, 'Hard Times', 'Engage Books' , 11 , 2018 ,9,252, 54.32 ,'Inglês',3,9),
		(29, 'Essays On Fielding Miscellanies', 'Princeton University Press' , 8 , 2018 ,24,490, 142.47 ,'Inglês',5,8),
		(30, 'Sexus', 'PENGUIN BOOKS LTD' , 8 , 2015 ,5,512, 12.07 ,'Inglês',3,8),
		(31, 'Plexus', 'PENGUIN BOOKS LTD' , 8 , 2015 ,9,528, 12.07 ,'Inglês',3,8),
		(32, 'Tropic Of Cancer', 'PENGUIN BOOKS LTD' , 6 , 2015 ,9,272, 12.07 ,'Inglês',3,8),
		(33, 'Fundamentos da Computação :Programação Funcional', 'PENGUIN BOOKS LTD' , 2 , 2015 ,32,100, 25.00 ,'Português',17,7),
		(34, 'O Homem Mais Feliz da História', 'Pergaminho' , 10 , 2018 ,15,336, 14.94 ,'Português',1,6),
		(35, 'Pais Brilhantes,Professores Fascinantes', 'Pergaminho' , 4 , 2004 ,14,176, 13.30 ,'Português',17,6),
		(36, 'Crime And Punishment', 'PENGUIN BOOKS LTD' , 1 , 2003 ,9,720, 9.66 ,'Inglês',3,5),
		(37, 'O Jogador', 'Editorial Presença' , 4 , 2001 ,3,168, 12.90 ,'Inglês',1,5),
		(38, 'Os Maias', 'Livros do Brasil' , 8 , 2017 ,50,736, 11.10 ,'Português',1,4),
    	(39, 'Memorial do Convento', 'Porto Editora' , 1 , 2018 ,30,402, 17.70 ,'Português',1,3),
		(40, 'Ensaio sobre a Cegueira', 'Porto Editora' , 7 , 2018 ,10,272, 15.93 ,'Português',1,3),
		(41, 'O Alquimista', '11X17' , 10 , 2013 ,12,200, 8 ,'Português',1,2),
		(42, 'A Espia', 'Pergaminho' , 9 , 2016 ,2,184, 15.50 ,'Português',1,2),
		(43, 'Capitães da Areia', 'BIS' , 1 , 2009 ,6,208, 7.50 ,'Português',1,1),
     	(44, 'Gabriela,Cravo e Canela', 'BIS' , 10 , 2018 ,12,464, 8.96 ,'Português',1,1);





-- Povoamento da tabela "Venda"

INSERT INTO Venda
	(idVenda,`Data`,Cupao,Total,Funcionario,Cliente)
	VALUES
		(1, '2011/03/22',0,0,1, 3),
		(2, '2011/03/22',0.25,0, 1, 12),
		(3, '2011/03/22',0,0, 1, 10),
		(4, '2011/03/22',0.1,0, 2, 14),
		(5, '2011/05/10',0,0, 2, 1),
		(6, '2011/05/11',0,0, 2, 13),
		(7, '2011/05/11',0,0, 2, 2),
		(8, '2011/05/22',0,0, 3, 8),
		(9, '2011/11/11',0.15,0, 3, 10),
		(10,'2011/12/01',0,0, 9, 15),
		(11, '2013/01/02',0,0, 30, 11),
		(12, '2013/03/22',0,0, 30, 5),
		(13, '2013/03/22',0,0, 30, 3),
		(14, '2013/03/22',0.30,0, 30, 14),
		(15, '2014/09/08',0,0, 23, 7),
		(16, '2014/11/13',0.10,0, 26, 14),
		(17, '2014/11/17',0,0, 17, 1),
		(18, '2015/01/19',0,0, 27, 1),
		(19, '2015/02/14',0.15,0, 1, 3),
		(20, '2015/02/15',0,0, 7, 1),
		(21, '2015/06/12',0,0, 19, 15),
		(22, '2015/06/12',0.05,0, 19, 14),
		(23, '2016/07/06',0.10,0, 1, 6),
		(24, '2016/07/17',0,0, 1, 6),
		(25, '2016/08/16',0,0, 1, 14),
		(26, '2016/10/09',0,0, 30, 1),
		(27, '2017/04/03',0.20,0, 30, 9),
		(28, '2017/04/22',0,0, 20, 4),
		(29, '2018/08/22',0,0, 21, 14),
		(30, '2018/08/22',0,0, 21, 2);


	
	
-- Povoamento da tabela "LivroVenda"
INSERT INTO LivroVenda
	(Livro,Venda,Quantidade, Preco)
	VALUES
		(1,1,3, 15.21),
		(2, 1,2, 13.90),
		(1, 2,1, 15.21),
		(2, 2,3, 13.90),
		(44,2,2, 8.96 ),
		(7, 3,2, 9.90),
		(8, 4,1, 12.90),
		(9, 4,4, 13.28),
		(9, 5,4, 13.28),
		(11,6,1, 16.30),
		(23,6,1, 10.80),
		(23,7,2, 10.80),
		(27,8,3, 54.32),
		(28,8,1, 54.32),	
		(29,8,13,142.47),
		(29,9,1, 142.47),
		(29,10,1,142.47),
		(33,11,1,25.00),
		(33,12,5,25.00),
		(39,12,2,17.70),
		(40,13,4,15.93), 
		(41,14,6,8),
		(42,14,1,15.50),
		(44,15,3,8.96 ),
		(1,16,4, 15.21),
		(19,16,5,24.17),
		(1,17,1, 15.21),
		(1,18,1, 15.21),
		(1,19,1, 15.21),
		(23,19,1,10.80),
		(34,20,2, 14.94),
		(36,21,7, 9.66),
		(10,22,6,11.00),
		(24,23,4, 10.00),
		(16,24,4, 8.69),
		(35,25,1, 13.30),
		(26,26,4, 54.32),
		(44,27,5, 8.96 ),
		(1,28,1, 15.21),
		(1,29,1, 15.21),
		(1,30,2, 15.21);
        
        
        


