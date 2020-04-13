-- ------------------------------------------------------
-- ------------------------------------------------------
-- Universidade do Minho
-- Mestrado Integrado em Engenharia Informática
-- Unidade Curricular de DSS
-- 
-- Caso de Estudo: ConfiguraFácil - Configurador de carros.
-- Povoamento inicial da base de dados
-- Dezembro/2018
-- ------------------------------------------------------

-- ------------------------------------------------------
--
-- Esquema: "Loja de livros."
USE `ConfiguraFacil`;

--
-- Permissão para fazer operações de remoção de dados.
SET SQL_SAFE_UPDATES = 0;





-- Povoamento da tabela "Utilizador"
INSERT INTO Utilizador
	(Username, `Password`)
	VALUES 
	('admin','admin'),
	('joanacruz','pintas'),
	('juniorsalgado','fresquinho'),
	('rafaelalves','mandachuva'),
	('etiennecosta','etienne>joana');



-- Povoamento da tabela "Funcionário"

INSERT INTO Funcionario
	(Username,Nome,Contacto,Morada)
	VALUES
	('joanacruz','Joana Franqueira Marta da Cruz','1','Rua de cima Nr 1'),
	('juniorsalgado','Mauricio Zulueta Lima Salgado','2','Rua de baixo Nr 2'),
	('rafaelalves','Rafael Dias Alves','3','Rua da esquerda Nr 3'),
	('etiennecosta','Etienne da Silva Filipe Amado da Costa','4','Rua da direita Nr 4');



-- Povoamente da tabela "Funcionário Stand"
INSERT INTO FuncionarioStand
	(FuncionarioS)
	VALUES
	('joanacruz'),
	('juniorsalgado');

-- Povoamente da tabela "Funcionário Fabrica"

INSERT INTO FuncionarioFabrica
	(FuncionarioF)
	VALUES
	('rafaelalves'),
	('etiennecosta');


-- Povoamento da tabela "Categoria"

INSERT INTO Categoria 
    (IdCategoria,Categoria)
    VALUES
    (1,'Motor'),
    (2,'Transmissão'),
    (3,'Cor'),
    (4,'Estofo'),
    (5,'CorTejadilho'),
    (6,'Jantes'),
    (7,'Pneus'),
    (8,'Interior'),
    (9,'Exterior'),
    (10,'Direção'),
    (11,'Farois'),/*<<-----Nome desta categoria foi alterado face ao modelo inicial*/
    (12,'CorTetoInterior'), /*<<----Nome desta categoria foi alterado face ao modelo inicial*/
    (13,'Climatização');

-- Povoamento da tabela "Componente"
INSERT INTO Componente
    (IdComponente,Nome,Descricao,Stock,Valor,Categoria)
    VALUES
    /*1 - Motor*/
    /*Tendo em conta que esta categoria faz parte de configuração base parte-se do pressuposto que não existem incompatibilidades entre estes componentes*/
    (1,'BMW M102 TurboCharged','110kW (150cv) - 4.5-5.6l/100Km',12,6000,1),
    (2,'BMW M106 TurboCharged','140kW (190cv) - 5.5-7.6l/100Km',12,6000,1),
    (3,'BMW M50 Naturally Aspirated','170kW (231cv) - 6.1-8.6l/100Km',12,6000,1),
    (4,'BMW S50 Naturally Aspirated','195kW (265cv) - 6.9-9.6l/100Km',12,6000,1),
    (5,'BMW M52 Naturally Aspirated','235kW (320cv) - 7.43-9.6l/100Km',12,6000,1),
    (6,'BMW N55 Single Twin Scroll TurboCharged','294kW (400cv) - 9.5-10.6l/100Km',12,6000,1),
    (7,'BMW B58 Single Twin Scroll TurboCharged','441kW (600cv) - 11.6l/100Km',12,6000,1),

    /*2-Transmissão*/
    /*Tendo em conta que esta categoria faz parte de configuração base parte-se do pressuposto que não existem incompatibilidades entre estes componentes*/
    (8,'Transmissão automática 6 Velocidades','Instalação de transmissão automática 6 Velocidades',12,43.5,2),
    (9,'Transmissão automática 7 Velocidades','Instalação de transmissão automática 7 Velocidades',12,52.1,2),
    (10,'Transmissão automática 8 Velocidades','Instalação de transmissão automática 8 Velocidades',12,34.8,2),
    (11,'Transmissão manual 6 Velocidades','Instalação de transmissão manual 6 Velocidades',12,45.1,2),
    (12,'Transmissão manual 7 Velocidades','Instalação de transmissão manual 7 Velocidades',12,45.1,2),
    (13,'Transmissão semiautomática de 6 velocidades','Instalação de transmissão semiautomática de 6 velocidades',12,46.5,2),
    (14,'Transmissão semiautomática de 7 velocidades','Instalação de transmissão semiautomática de 7 velocidades',12,50,2),

    /*3-Cor*/
    /*Tendo em conta que esta categoria faz parte de configuração base parte-se do pressuposto que não existem incompatibilidades entre estes componentes*/
    (15,'Branca','Cor de chassi metalizada branca',10000,300,3),
    (16,'Preto','Cor de chassi metalizada preto',10000,300,3),
    (17,'Cinza','Cor de chassi metalizada cinza',10000,300,3),
    (18,'Azul','Cor de chassi metalizada azul perola',10000,300,3),
    (19,'Castanho','Cor de chassi metalizada castanho',10000,300,3),
    (20,'Vermelho','Cor de chassi metalizada vermelho',10000,300,3),

    /*4-Estofo*/
    /*Tendo em conta que esta categoria faz parte de configuração base parte-se do pressuposto que não existem incompatibilidades entre estes componentes*/
    (21,'Pele','Estofos em pele',40000,900,4),
    (22,'Tecido','Estofos em tecido',50000,600,4), 
    (23,'Pele/tecido','Combinação de estofos em pele e tecido',1000,2340.00,4),

    /*5-CorTejadilho*/
    /*Tendo em conta que esta categoria faz parte de configuração base parte-se do pressuposto que não existem incompatibilidades entre estes componentes A NAO SER QUE SEJA ESCOLHIDO TETO PANORAMICO*/
    (24,'Branca','Tejadilho com acabamento em branco a contrastar com a cor do chassi',10000,300,5),
    (25,'Preto','Tejadilho com acabamento em preto a contrastar com a cor do chassi',10000,300,5),
    (26,'Cinza','Tejadilho com acabamento em cinza a contrastar com a cor do chassi',10000,300,5),
    (27,'Azul','Tejadilho com acabamento em azul a contrastar com a cor do chassi',10000,300,5),
    (28,'Vermelho','Tejadilho com acabamento em vermelho a contrastar com a cor do chassi',10000,300,5),

    /*6-Jantes*/
    /*Tendo em conta que esta categoria faz parte de configuração base parte-se do pressuposto que não existem incompatibilidades entre estes componentes*/
    (29,'Infiny Spike','Jante de 17 polegadas',800,158.18,6),
    (30,'CMS C16','Jante de 21 polegadas',600,99.81,6),
    (31,'ATS Emotion','Jante de 17 polegadas',400,85.18,6),
    (32,'Platin P58','Jante de 18 polegadas',200,94.58,6),
    (33,'Inter Action Sirius','Jante de 20 polegadas',150,96.12,6),
    (34,'Inter Action Pulsar','Jante de 18 polegadas',100,97.53,6),
    (35,'Proline CX100','Jante de 17 polegadas',500,100.84,6),

    /*7-Pneus*/
    /*APENAS É POSSIVEL ESCOLHER UM TIPO DE PNEUS. CASO UM SEJA ESCOLHIDO, ESSE MESMO PASSA A SER INCOMPATIVEL COM TODOS OS OUTROS PNEUS. CADA PNEUS É INCOMPATIVEL COM TODOS OS OUTROS PNEUS*/     
    (36,'Michelin Primacy 4','Um pneu direcionado para uma condução mais disportiva',200,71.25,7),
    (37,'Kormoran Ultra High','Um pneu direcionado para uma condução confortavel e silenciosa',100,57.23,7),
    (38,'Bridgestone Turanza TOO5','Um pneu equilibrado com boa aderencia em piso molhado e seco',80,57.11,7),
    (39,'GoodYear Eagle F1 Asymmetric 3','Um pneu direcionado para uma condução mais disportiva',40,76.28,7),
    (40,'Roadstone Eurovis SP O4','Um pneu direcionado para uma condução preventiva',40,38.55,7),
    (41,'MaxTrek SU 800','Um pneu direcionado para condução em piso gelado e com neve',20,75.36,7),
    (42,'MAxxis ME3','Um pneu direcionado para uma condução mais disportiva',40,32.60,7),

    /*8-Interior*/
    /*APENAS É POSSIVEL ESCOLHER UM TIPO DE FRISOS. CASO UM SEJA ESCOLHIDO, ESSE MESMO PASSA A SER INCOMPATIVEL COM TODOS OS OUTROS FRISOS. CADA FRISO É INCOMPATIVEL COM TODOS OS OUTROS FRISOS*/
    (43,'Friso 1','Friso em branco Acrilico e linha de realce em cromado perola',100,380.50,8),
    (44,'Friso 2','Friso em preto brilhante com linha de realce em vermelho coral',90,500.70,8),
    (45,'Friso 3','Frisos em aluminio Hexagon com linha de realce em preto brilhante',430.40,60,8),
    (46,'Friso 4','Friso em aluminio Hexagon com linha de realce em azul mate',64,320.80,8),
    (47,'Friso 5','Friso em preto brilhante acrilico e linha de realce em cromado perola',89,250.25,8),
    (48,'Friso 6','Friso em prata Satin mate',20,490.80,8),
    (49,'Friso 7','Friso em preto brilhante com linha de realce em cromado com brilho',57,800.20,8),
    (50,'Friso 8','Friso em aluminio escovado com linha de realce em preto brilhante',83,980.90,8),
    (51,'Friso 9','Frisos em aluminio escovado com linha de realce em cromado com brilho perola',54,875.00,8),
    (52,'Friso 10','Friso em madeira nobre Fineline Stream com acabamentos em perola cromado',15,1200,8),
    /*APENAS É POSSIVEL ESCOLHER UM PACK DE ILUMINAÇÃO INTERIOR. CASO UM SEJA ESCOLHIDO, ESSE MESMO PASSA A SER INCOMPATIVEL COM TODOS OS OUTROS PACKS. CADA PACK É INCOMPATIVEL COM TODOS OS OUTROS*/
    (53,'Pack Luzes interior','Luz interior do habitaculo',252,1400.50,8),
    (54,'Pack Luzes Interior - Regulavel','Luz interior do habitáculo com regulação de intencidade e de cor',330,2760.00,8),
    (55,'Pack Luzes interior e exterior','Luz interior do habitaculo e exterior com eluminação dos manipulos das portas',208,3100.00,8),
    /*Interior - No caso da escolha do radio, este é um bom exemplo de um componente que pode obrigar a instalação de um outro componente*/
    /*O sistema de som BOSE Premium implica a instalação do "Radio GPS All in One"*/
    (56,'Sistema de som BOSE Premium', 'Sistema de som de alta fidelidade',300,1180.00,8),
    (57,'Sistema de som 4 localizações','Sistema de som base',1232,340.00,8),
    /*O sistema de som Bose Premium não é compativel com o radio a baixo*/
    (58,'Radio CD', 'Radio com leitor de CD',305,320.80,8),
    (59,'Radio com sistema bluetoth','Radio com sistema bluetoth de maos livres integrado',560,870.50,8),
    /*<<<O RADIO ABAIXO É UM CASO DE IMPLICAÇÃO DE INSTALAÇÃO DE COMPONENTES EXTRA---------------A instalação do radio a baixo implica a instalação do sistema de som BOSE Premium*/
    (60,'Radio GPS All in One','Radio com sistema de navegação e quit de mão livres incorporado',430,2350.00,8),


    /*9-Exterior*/
    (61,'Retrovisores exteriores sport','Retrovisores exteriores em preto',200,580.00,9),
    /*SO É POSSIVEL TER 1 DOS DOIS TIPOS DE VIDROS ABAIXO*/
    (62,'Vidros Private','Vidros escurecidos',375,860.0,9),
    (63,'Vidros PrivaTerm','Vidros laterais e traseiros com isolamento térmico',126,1350.90,9),
    (64,'Para-brisas Executive','Para-brisas acústico com aquecimento sem fios',34,2100.00,9),
    (65,'Teto panorâmico em vidro','Teto panorâmico em vidro',17,3500.00,9),
    (66, 'Escova','Escovas do Para-brisas adaptativas com saídas de água integradas',329,470.00,9),
    (67,'Retrovisores exteriores pintados na cor da carroçaria','Retrovisores exteriores pintados na cor da carroçaria',999,0.00,9), /*<<------Um componente com preço zero!*/
    (68,'Teto de abrir','Teto de abrir eletrico',180,860.00,9),


    /*10 - Direcção*/
    /*APENAS É POSSIVEL ESCOLHER UM TIPO DE DIREÇÃO. CASO UM SEJA ESCOLHIDO, ESSE MESMO PASSA A SER INCOMPATIVEL COM TODOS AS OUTRAS DIREÇÕES. CADA TIPO DE DIREÇÃO É INCOMPATIVEL COM TODAS AS OUTRAS*/
    (69,'Direção hidráulica',' A direção fica mais leve devido a uma bomba que faz circular o óleo dentro da caixa de direção.',587,900.00,10),
    (70,'Direção eletrohidráulica','Uma bomba é acionada por um dispositivo elétrico e não pelo motor do carro. Isso evita a perda de potência do carro.',364,1480.00,10),
    (71,'Direção elétrica','Os movimentos da direção são acionados por um sensor, tornando a dirigibilidade do carro mais confortável para o motorista.',789,2400.50,10),


    /*11 - Farois*/
    (72,'Farois LED padrão','Faróis LED',390,1000.00,11),
    /*<<-ABAIXO É UM CASO DE IMPLICAÇÃO DE INSTALAÇÃO DE COMPONENTES EXTRA-------'Matrix LED com Laser Light' implica a instalação também de 'Luzes de nevoeiro Deluxe'*/
    (73,'Matrix LED com Laser Light','Faróis dianteiros HD Matrix LED incluindo Laser Light',850,3580.70,11),
    (74,'LED-Matrix','Faróis dianteiros HD Matrix LED incluindo indicadores dinâmicos à frente e atrás',453,2040.50,11),
    /*O SISTEMA DE LAVAGEM DE FAROIS NÃO É COMPATIVEL COM OS FAROIS "Matrix LED com Laser Light"*/
    (75,'Sistema de lavagem dos faróis','Sistema de lavagem dos faróis automatico',490,300.00,11),
    (76,'Luzes adaptativas LED','Farois com luzes adaptativas LED consoante a luz exterior',390,1430,11),
    (77,'Luzes de nevoeiro','Luzes de nevoeiro',840,460.00,11),
    /*O SISTEMA DE "Luzes de nevoeiro Deluxe" NÃO É COMPATIVEL COM OS FAROIS "Farois LED padrao"*/
    (78,'Luzes de nevoeiro Deluxe','Luzes de nevoeiro LED',340,870.00,11),


    /*12-CorTetoInterior*/   
    (79,'Branca','Cor do teto em cor Branca',1000,300,12),
    (80,'Preto','Cor do teto em cor Preto',1400,300,12),
    (81,'Cinza','Cor do teto em cor Cinza',1900,300,12),
    (82,'Preto e Vermelho','Cor do teto preto com acabamentos em vermelho',3000,300,12),
    /*<<-ABAIXO É UM CASO DE IMPLICAÇÃO DE INSTALAÇÃO DE COMPONENTES EXTRA------Quando escolhido teto panoramico os dois acabamentos abaixo são as escolhas possiveis para acabamentos do teto interior*/
    (83,'Acabamento em cinza rato', 'Escolha exclusiva para teto panoramico',780,900.00,12),
    (84,'Acabamento camel escurecido', 'Escolha exclusiva para teto panoramico',540,940.00,12),


    /*13-Climatização*/
    /*NOS 2 COMPONENTES ABAIXO, SÓ É POSSIVEL ADICIONAR UM DELES, LOGO UM É INCOMPATIVEL COM O OUTRO*/
    (85,'Bancos dianteiros aquecidos','Bancos dianteiros aquecidos',290,1860.90,13),
    (86,'Bancos dianteiros e e traseiros aquecidos','Bancos dianteiros e e traseiros aquecidos',300,2300.70,13),
    (87,'Volante desportivo M em pele aquecido','Volante aquece em segundos logo após o carro ligar',654,2100.20,13),   
    /*NOS 2 COMPONENTES ABAIXO, SÓ É POSSIVEL ADICIONAR UM DELES, LOGO UM É INCOMPATIVEL COM O OUTRO*/ 
    (88,'Ar condicionado automático','Ar condicionado automático',560,980.00,13),
    (89,'Ar condicionado automático multi-zona','Ar condicionado automático multi-zona',430,1580.40,13);



INSERT INTO ComponentesIncompativeis
    (Componente,Incompativel)
    VALUES
    /*7-PNEUS - APENAS É POSSIVEL ESCOLHER UM TIPO DE PNEUS. CASO UM SEJA ESCOLHIDO, ESSE MESMO PASSA A SER INCOMPATIVEL COM TODOS OS OUTROS PNEUS. CADA PNEUS É INCOMPATIVEL COM TODOS OS OUTROS PNEUS*/
    (36,37),
    (36,38),
    (36,39),
    (36,40),
    (36,41),
    (36,42),

    (37,36),
    (37,38),
    (37,39),
    (37,40),
    (37,41),
    (37,42),

    (38,36),
    (38,37),
    (38,39),
    (38,40),
    (38,41),
    (38,42),

    (39,36),
    (39,37),
    (39,38),
    (39,40),
    (39,41),
    (39,42),

    (40,36),
    (40,37),
    (40,38),
    (40,39),
    (40,41),
    (40,42),

    (41,36),
    (41,37),
    (41,38),
    (41,39),
    (41,40),
    (41,42),

    (42,36),
    (42,37),
    (42,38),
    (42,39),
    (42,40),
    (42,41),

    /*8 - INTERIORES - Incompatibilidades entre frisos; Uma vez selecionado 1, não é possivel adicionar mais nenhum*/
    (43,44),
    (43,45),
    (43,46),
    (43,47),
    (43,48),
    (43,49),
    (43,50),
    (43,51),
    (43,52),

    (44,43),
    (44,45),
    (44,46),
    (44,47),
    (44,48),
    (44,49),
    (44,50),
    (44,51),
    (44,52),

    (45,43),
    (45,44),
    (45,46),
    (45,47),
    (45,48),
    (45,49),
    (45,50),
    (45,51),
    (45,52),

    (46,43),
    (46,44),
    (46,45),
    (46,47),
    (46,48),
    (46,49),
    (46,50),
    (46,51),
    (46,52),

    (47,43),
    (47,44),
    (47,45),
    (47,46),
    (47,48),
    (47,49),
    (47,50),
    (47,51),
    (47,52),

    (48,43),
    (48,44),
    (48,45),
    (48,46),
    (48,47),
    (48,49),
    (48,50),
    (48,51),
    (48,52),

    (49,43),
    (49,44),
    (49,45),
    (49,46),
    (49,47),
    (49,48),
    (49,50),
    (49,51),
    (49,52),

    (50,43),
    (50,44),
    (50,45),
    (50,46),
    (50,47),
    (50,48),
    (50,49),
    (50,51),
    (50,52),

    (51,43),
    (51,44),
    (51,45),
    (51,46),
    (51,47),
    (51,48),
    (51,49),
    (51,50),
    (51,52),

    (52,43),
    (52,44),
    (52,45),
    (52,46),
    (52,47),
    (52,48),
    (52,49),
    (52,50),
    (52,51),

    /*8 - INTERIOR - APENAS É POSSIVEL ESCOLHER UM PACK DE ILUMINAÇÃO INTERIOR. CASO UM SEJA ESCOLHIDO, ESSE MESMO PASSA A SER INCOMPATIVEL COM TODOS OS OUTROS PACKS. CADA PACK É INCOMPATIVEL COM TODOS OS OUTROS*/
    (53,54),
    (53,55),
    (54,53),
    (54,55),
    (55,53),
    (55,54),

    /*8 - INTERIOR - Só é possivel ter 1 radio num veiculo, daí cada radio ser incompativel com qualquer outro e também os casos em que alguns radios não sao compativeis com determinados tipos de sistemas de som*/
    (58,59),
    (58,60),
    (59,58),
    (59,60),
    (60,58),
    (60,59),
    (60,57),
    (57,60),

    /*9 - EXTERIOR - SO É POSSIVEL TER 1 DOS DOIS TIPOS DE VIDROS*/
    (62,63),
    (63,62),
    (64,63),
    (63,64),

    /*9 - EXTERIOR - Só é possivel ter 1 dos dois tipos de teto no veiculo, i. é, ou se tem 'Teto panorâmico em vidro' ou 'Teto de abrir'*/
    (65,68),
    (68,65),

    /*9 - EXTERIOR - Na escolha de retrovisores só é possivel escolher 1 daí a descriminação das seguintes incompatibilidades*/
    (61,67),
    (67,61),

    /*9 - EXTERIOR - Se for escolhido o teto panoramico torna-se impossivel escolher uma cor para o tejadilho*/
    (65,24),
    (65,25),
    (65,26),
    (65,27),
    (65,28),

    /*10 - Direcção - APENAS É POSSIVEL ESCOLHER UM TIPO DE DIREÇÃO. CASO UM SEJA ESCOLHIDO, ESSE MESMO PASSA A SER INCOMPATIVEL COM TODOS AS OUTRAS DIREÇÕES. CADA TIPO DE DIREÇÃO É INCOMPATIVEL COM TODAS AS OUTRAS*/
    (69,70),
    (69,71),
    (70,69),
    (70,71),
    (71,69),
    (71,70),

    /*11 - Farois - Ha casos de incompatibilidade, bem como farois que implicam a instalação de componentes extra*/
    (72,28),
    (78,72),
    (76,78),
    (75,74),
    (74,75),

    /*12 - CorTetoInterior - Todas as cores disponiveis para o teto são incompativeis quando selecionado o teto panoramico*/
    (68,82),
    (68,83),
    (68,84),
    (82,68),
    (83,68),
    (84,68),
    (65,79),
    (65,80),
    (65,82),
    (79,65),
    (80,65),
    (82,65),

    /*13 - Climatização - So é possivel ter bancos dianteiros aquecidos ou 'Bancos dianteiros e traseiros aquecidos' e o mesmo se verifica
    para as opções de ar condicionado*/
    (85,86),
    (86,85),
    (88,89),
    (89,88);



INSERT INTO ComponentesDependentes
    (Componente,Dependente)
    VALUES
    (56,60),
    (73,78),
    (65,83),
    (65,84);


INSERT INTO Modelo 
    (IdModelo,Nome)
    VALUES
    (1,'Série 1'),
    (2,'Série 2'),
    (3,'Série 3'),
    (4,'Série 4'),
    (5,'Série 5'),
    (6,'Série 6'),
    (7,'Série 7'),
    (8,'Serie 8');

INSERT INTO ModeloComponente
    (Componente,Modelo)
    VALUES
    /*----------------------------------------------SERIE 1---------------------------------------------*/  
    (1,1),
    (2,1),
    (3,1),
    (4,1),
    (8,1),
    (11,1),
    (14,1),
    (15,1),
    (16,1),
    (17,1),
    (18,1),
    (19,1),
    (20,1),
    (21,1),
    (22,1),
    (23,1),
    (24,1),
    (25,1),
    (26,1),
    (27,1),
    (28,1),
    (29,1),
    (31,1),
    (34,1),
    (35,1),
    (36,1),
    (37,1),
    (38,1),
    (39,1),
    (40,1),
    (41,1),
    (42,1),
    (43,1),
    (44,1),
    (47,1),
    (49,1),
    (53,1),
    (56,1),
    (57,1),
    (58,1),
    (59,1),
    (60,1),
    (61,1),
    (62,1),
    (66,1),
    (67,1),
    (68,1),
    (69,1),
    (70,1),
    (72,1),
    (74,1),
    (75,1),
    (76,1),
    (77,1),
    (78,1),
    (79,1),
    (80,1),
    (81,1),
    (82,1),
    (85,1),
    (86,1),
    (88,1),
    (89,1),


    /*------------------------------------------------------SERIE 2(tudo igual ao serie 1)-------------------------------------------*/
    (1,2),
    (2,2),
    (3,2),
    (4,2),
    (8,2),
    (11,2),
    (14,2),
    (15,2),
    (16,2),
    (17,2),
    (18,2),
    (19,2),
    (20,2),
    (21,2),
    (22,2),
    (23,2),
    (24,2),
    (25,2),
    (26,2),
    (27,2),
    (28,2),
    (29,2),
    (31,2),
    (34,2),
    (35,2),
    (36,2),
    (37,2),
    (38,2),
    (39,2),
    (40,2),
    (41,2),
    (42,2),
    (43,2),
    (44,2),
    (47,2),
    (49,2),
    (53,2),
    (56,2),
    (57,2),
    (58,2),
    (59,2),
    (60,2),
    (61,2),
    (62,2),
    (66,2),
    (67,2),
    (68,2),
    (69,2),
    (70,2),
    (72,2),
    (74,2),
    (75,2),
    (76,2),
    (77,2),
    (78,2),
    (79,2),
    (80,2),
    (81,2),
    (82,2),
    (85,2),
    (86,2),
    (88,2),
    (89,2),


    /*-------------------------------------------SERIE 3-----------------------------------------*/
    (2,3),
    (3,3),
    (4,3),
    (5,3),
    (8,3),
    (9,3),
    (11,3),
    (14,3),
    (15,3),
    (16,3),
    (17,3),
    (18,3),
    (19,3),
    (20,3),
    (21,3),
    (22,3),
    (23,3),
    (24,3),
    (25,3),
    (26,3),
    (27,3),
    (28,3),
    (29,3),
    (31,3),
    (34,3),
    (35,3),
    (36,3),
    (37,3),
    (38,3),
    (39,3),
    (40,3),
    (41,3),
    (42,3),
    (43,3),
    (44,3),
    (47,3),
    (49,3),
    (53,3),
    (54,3),
    (55,3),
    (56,3),
    (57,3),
    (58,3),
    (59,3),
    (60,3),
    (61,3),
    (66,3),
    (67,3),
    (68,3),
    (69,3),
    (70,3),
    (72,3),
    (74,3),
    (75,3),
    (76,3),
    (77,3),
    (78,3),
    (79,3),
    (80,3),
    (81,3),
    (82,3),
    (85,3),
    (86,3),
    (88,3),
    (89,3),


    /*-------------------------------------------SERIE 4-----------------------------------------*/
    (3,4),
    (4,4),
    (5,4),
    (9,4),
    (10,4),
    (12,4),
    (14,4),
    (15,4),
    (16,4),
    (17,4),
    (18,4),
    (19,4),
    (20,4),
    (21,4),
    (22,4),
    (23,4),
    (24,4),
    (25,4),
    (26,4),
    (27,4),
    (28,4),
    (29,4),
    (31,4),
    (34,4),
    (35,4),
    (36,4),
    (37,4),
    (38,4),
    (39,4),
    (40,4),
    (41,4),
    (42,4),
    (43,4),
    (44,4),
    (47,4),
    (49,4),
    (50,4),
    (51,4),
    (53,4),
    (54,4),
    (55,4),
    (56,4),
    (57,4),
    (58,4),
    (59,4),
    (60,4),
    (61,4),
    (65,4),
    (66,4),
    (67,4),
    (68,4),
    (69,4),
    (70,4),
    (72,4),
    (74,4),
    (75,4),
    (76,4),
    (77,4),
    (78,4),
    (79,4),
    (80,4),
    (81,4),
    (82,4),
    (83,4),
    (84,4),
    (85,4),
    (86,4),
    (88,4),
    (89,4),


    /*-------------------------------------------SERIE 5-----------------------------------------*/
    (3,5),
    (4,5),
    (5,5),
    (9,5),
    (10,5),
    (12,5),
    (14,5),
    (15,5),
    (16,5),
    (17,5),
    (18,5),
    (19,5),
    (20,5),
    (21,5),
    (22,5),
    (23,5),
    (24,5),
    (25,5),
    (26,5),
    (27,5),
    (28,5),
    (29,5),
    (31,5),
    (34,5),
    (35,5),
    (36,5),
    (37,5),
    (38,5),
    (39,5),
    (40,5),
    (41,5),
    (42,5),
    (43,5),
    (44,5),
    (47,5),
    (49,5),
    (50,5),
    (51,5),
    (53,5),
    (54,5),
    (55,5),
    (56,5),
    (57,5),
    (58,5),
    (59,5),
    (60,5),
    (61,5),
    (62,5),
    (63,5),
    (65,5),
    (66,5),
    (67,5),
    (68,5),
    (69,5),
    (70,5),
    (71,5),
    (72,5),
    (74,5),
    (75,5),
    (76,5),
    (77,5),
    (78,5),
    (79,5),
    (80,5),
    (81,5),
    (82,5),
    (83,5),
    (84,5),
    (85,5),
    (86,5),
    (88,5),
    (89,5),


    /*-------------------------------------------SERIE 6-----------------------------------------*/
    (4,6),
    (5,6),
    (6,6),
    (9,6),
    (10,6),
    (12,6),
    (14,6),
    (15,6),
    (16,6),
    (17,6),
    (18,6),
    (19,6),
    (20,6),
    (21,6),
    (22,6),
    (23,6),
    (24,6),
    (25,6),
    (26,6),
    (27,6),
    (28,6),
    (29,6),
    (31,6),
    (34,6),
    (35,6),
    (36,6),
    (37,6),
    (38,6),
    (39,6),
    (40,6),
    (41,6),
    (42,6),
    (43,6),
    (44,6),
    (45,6),
    (46,6),
    (47,6),
    (48,6),
    (49,6),
    (50,6),
    (51,6),
    (53,6),
    (54,6),
    (55,6),
    (56,6),
    (57,6),
    (58,6),
    (59,6),
    (60,6),
    (61,6),
    (62,6),
    (64,6),
    (63,6),
    (65,6),
    (66,6),
    (67,6),
    (68,6),
    (69,6),
    (70,6),
    (71,6),
    (72,6),
    (74,6),
    (75,6),
    (76,6),
    (77,6),
    (78,6),
    (79,6),
    (80,6),
    (81,6),
    (82,6),
    (83,6),
    (84,6),
    (85,6),
    (86,6),
    (88,6),
    (89,6),


    /*-------------------------------------------SERIE 7-----------------------------------------*/
    (5,7),
    (6,7),
    (7,7),
    (9,7),
    (10,7),
    (12,7),
    (14,7),
    (15,7),
    (16,7),
    (17,7),
    (18,7),
    (19,7),
    (20,7),
    (21,7),
    (22,7),
    (23,7),
    (24,7),
    (25,7),
    (26,7),
    (27,7),
    (28,7),
    (30,7),
    (31,7),
    (32,7),
    (33,7),
    (34,7),
    (36,7),
    (37,7),
    (38,7),
    (39,7),
    (40,7),
    (41,7),
    (42,7),
    (43,7),
    (44,7),
    (45,7),
    (46,7),
    (47,7),
    (48,7),
    (49,7),
    (50,7),
    (51,7),
    (52,7),
    (53,7),
    (54,7),
    (55,7),
    (56,7),
    (57,7),
    (58,7),
    (59,7),
    (60,7),
    (61,7),
    (62,7),
    (63,7),
    (64,7),
    (65,7),
    (66,7),
    (67,7),
    (68,7),
    (69,7),
    (70,7),
    (71,7),
    (72,7),
    (73,7),
    (74,7),
    (75,7),
    (76,7),
    (77,7),
    (78,7),
    (79,7),
    (80,7),
    (81,7),
    (82,7),
    (83,7),
    (84,7),
    (85,7),
    (86,7),
    (87,7),
    (88,7),
    (89,7),

    /*-------------------------------------------SERIE 8 (este modelo é literalmente uma copia do SERIE 7)-----------------------------------------*/
    (7,8),
    (10,8),
    (15,8),
    (16,8),
    (17,8),
    (18,8),
    (19,8),
    (20,8),
    (21,8),
    (22,8),
    (23,8),
    (24,8),
    (25,8),
    (26,8),
    (27,8),
    (28,8),
    (30,8),
    (36,8),
    (37,8),
    (38,8),
    (39,8),
    (40,8),
    (41,8),
    (42,8),
    (43,8),
    (44,8),
    (45,8),
    (46,8),
    (47,8),
    (48,8),
    (49,8),
    (50,8),
    (51,8),
    (52,8),
    (54,8),
    (55,8),
    (56,8),
    (60,8),
    (61,8),
    (62,8),
    (63,8),
    (64,8),
    (65,8),
    (66,8),
    (67,8),
    (68,8),
    (71,8),
    (72,8),
    (73,8),
    (74,8),
    (75,8),
    (76,8),
    (77,8),
    (78,8),
    (79,8),
    (80,8),
    (81,8),
    (82,8),
    (83,8),
    (84,8),
    (85,8),
    (86,8),
    (87,8),
    (88,8),
    (89,8);


 INSERT INTO Pacote/*<<------------------------OS VALORES DE CADA PACOTE TÊM QUE SER CALCULADOS AUTOMATICAMENTE, COM BASE NA SOMA DOS PREÇOS DE CADA COMPONENTE QUE PERTENCE AO PACOTE EM QUESTAO!!!*/
     (IdPacote,Nome,Valor,Desconto)
     VALUES
    (1,'Som',935,0),
    (2,'Race',1290,0),
    (3,'Iluminacao',1290,0),
    (4,'Conforto',1760,0),
    (5,'Delux',6543,15);

INSERT INTO PacoteComponente
    (Pacote,Componente)
    VALUES
    (1,56),
    (1,59),

    (2,66),
    (2,62),
    (2,61),
    (2,41),
    (2,82),

    (3,53),
    (3,62),
    (3,72),

    (4,38),
    (4,68),
    (4,64),
    (4,86),
    (4,89),

    (5,56),
    (5,60),
    (5,71),
    (5,87);


INSERT INTO ModeloPacote
    (IdModelo,IdPacote)
    VALUES
    (1,1),
    (1,2),
    (1,3),

    (2,1),
    (2,2),
    (2,3),

    (3,1),
    
    (4,1),

    (5,1),
    (5,2),
    (5,3),
    
    (6,1),
    (6,2),
    (6,3),
    (6,4),

    (7,1),
    (7,2),
    (7,3),
    (7,4),
    (7,5),

    (8,2),
    (8,4),
    (8,5);

INSERT INTO Encomenda
    (idEncomenda,`Data`,Cliente,Contacto,Valor,Estado,FuncionarioS,Modelo) /*Os estados das encomendas nao deviam de estar listados todos numa tabela e aqui na encomenda fazer o relacionamento da tabela 'EstadosEncomendas' com a tabela 'Encomendas'???*/
    VALUES
    (1,'2018-12-25','Rosalina do Mato Pinta Silvo','+351 987 764 233',98500.50,'Pendente','juniorsalgado',8); /*<<------------------------OS VALORES DE CADA PACOTE TÊM QUE SER CALCULADOS AUTOMATICAMENTE, COM BASE NA SOMA DOS PREÇOS DE CADA COMPONENTE QUE PERTENCE AO PACOTE EM QUESTAO!!!*/


INSERT INTO EncomendaComponente
    (Encomenda,Componente)
    VALUES
    (1,7),
    (1,10),
    (1,16),
    (1,23),
    (1,25),
    (1,31),
    (1,82);


INSERT INTO EncomendaPacote
    (Encomenda,Pacote)
    VALUES
    (1,4);