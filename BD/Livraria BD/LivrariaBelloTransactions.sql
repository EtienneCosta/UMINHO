----
-- Unidade Curricular de Bases de Dados.
-- Sistemas de Dados Relacionais.
--
-- T R A N S A C T I O N S
--
-- Exemplos de Aplicação.
-- Costa, E., 2018.
--

-- Base de Dados de Trabalho.


DELIMITER $$ 
CREATE PROCEDURE compraCliente(IN idCliente INT,IN quantidadex INT,IN idL INT)

BEGIN 
    DECLARE idfun INT;
    DECLARE stockx INT;
    DECLARE precox Decimal(6,2);
    DECLARE datav Date;
    DECLARE vId INT;
    DECLARE fail BOOL DEFAULT 0;
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET fail=1;
    set idfun=floor(rand()*(30-1))+1;
    set stockx=(select Stock from Livro where Isbn=idL);
    set precox=(select Preco from Livro where Isbn=idL);
    set datav= date(now());
    set vId=1+ (select Count(idVenda) from Venda ) ;

    START TRANSACTION;
  if(stockx-quantidadex>=0)
  then
INSERT INTO Venda
    (idVenda,`Data`,Cupao,Total,Funcionario,Cliente)
    VALUES
        (vId,datav,0,0,idfun,idCliente);
        
  INSERT INTO LivroVenda
    (Livro,Venda,Quantidade, Preco)
    VALUES
        (idL,vId,quantidadex,precox); 
        else
        set fail =1;
     
        
    End If;
    
     
    If NOT fail Then
    select ('Venda efectuada com sucesso');
    COMMIT;
    else
    select ('Venda não efectuada');
    ROLLBACK;
    END IF;
END
$$   


--  TRANSAÇÃO PARA REGISTAR CATEGORIAS .
DELIMITER $$ 
CREATE PROCEDURE registaCategoria(IN Genre Varchar(45))
BEGIN 
    DECLARE Category INT;
    DECLARE test INT;
    DECLARE fail BOOL DEFAULT 0;
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET fail=1;    
    SET Category=1+ (select Count(idCategoria) from Categoria ) ;
    SET  test = (select count(Genero) from Categoria 
								where Genero=Genre);

    START TRANSACTION;
  if(test=0)
  then
   set fail=1;
INSERT INTO Categoria
    (idCategoria,Genero)
    VALUES
        (Category,Genre);	

    End If;
    
    If fail Then
    select ('Registo efectuado com sucesso');
    COMMIT;
    else
    select ('Categoria já existe');
    ROLLBACK;
    END IF;
END
$$   

-- TRANSACAO PARA REGISTAR ESCRITOR.

DELIMITER $$ 
CREATE PROCEDURE registaEscritor (IN Nomee Varchar(45),IN Nacionalidadee Varchar(45),IN DataNascimentoo Date,IN Biografiaa Varchar(300))
BEGIN 
    DECLARE ID INT;
    DECLARE test INT;
    DECLARE fail BOOL DEFAULT 0;
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET fail=1;    
    SET ID =1+ (select Count(idEscritor) from Escritor ) ;
    SET  test = (select count(Nome) from Escritor 
								where Nome=Nomee and DataNascimento=DataNascimentoo) ;

    START TRANSACTION;
  if(test=0) THEN
   SET fail=1;
INSERT INTO Escritor
    (idEscritor,Nome,Nacionalidade,DataNascimento,Biografia)
    VALUES
        (ID,Nomee,Nacionalidadee,DataNascimentoo,Biografiaa);	

    End If;
    
    If fail Then
    select ('Registo efectuado com sucesso');
    COMMIT;
    else
    select ('Escritor já existe');
    ROLLBACK;
    END IF;
END
$$   


-- TRANSACAO PARA REGISTAR LIVRO.

DELIMITER $$
CREATE PROCEDURE registaLivro (IN Isbnn INT, IN Tituloo Varchar(45),IN Editoraa Varchar(45),IN Edicaoo INT,IN Anoo INT,IN Stockk INT,IN Paginass INT, IN Precoo decimal (6,2),IN Idiomaa varchar(45),IN Categoriaa Varchar(45),IN Escritorr Varchar(45) )
BEGIN 
	DECLARE testIsbn INT;
    DECLARE testE INT;
	DECLARE testC  INT;
    
    DECLARE fail BOOL DEFAULT 0;
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET fail=1;    

	SET  testIsbn=(select Isbn from Livro where Isbn=Isbnn);
    SET  testE = (select idEscritor from Escritor  where Nome= Escritorr );
	SET  testC = (select idCategoria from Categoria  where Genero= Categoriaa );

	
    START TRANSACTION;
  if ((testE IS NOT NULL) AND (testC IS NOT NULL) AND testIsbn IS NULL) THEN
    SET fail =1;
INSERT INTO Livro
    (Isbn,Titulo,Editora,Edicao,Ano,Stock,Paginas,Preco,Idioma,Categoria,Escritor)
	VALUES
        (Isbnn,Tituloo,Editoraa,Edicaoo,Anoo,Stockk,Paginass,Precoo,Idiomaa,testC,testE);	

            
	End If;
     If fail Then
    select ('Registo efectuado com sucesso');
    COMMIT;
    else
    select ('VERIFICAR SE ESCRITOR,LIVRO ou CATEGORIA JÁ EXISTE!');
    ROLLBACK;
    END IF;
END $$



-- Transação para registar Cliente.

DELIMITER $$

CREATE PROCEDURE registaCliente (IN Nomee Varchar(45),IN DataNascimentoo Date,IN Localidadee Varchar(45),IN Ruaa Varchar(45),IN CodigoPostall Varchar(45),IN Telefonee Varchar(9),IN Emaill Varchar(45))
BEGIN
    DECLARE ID INT;
    DECLARE testNome INT;
    DECLARE testEmail INT;
    DECLARE fail BOOL DEFAULT 0;
    SET ID=1+ (select Count(idCliente) from Cliente);
    
    SET testNome= (select Count(idCliente) from Cliente where Nome=Nomee);
    SET testEmail= (select Count(idCliente) from Cliente where Email=Emaill);

START TRANSACTION;

if((testNome=0) AND (testEmail=0)) THEN 
    SET FAIL =1;
INSERT INTO Cliente 
    (idCliente,Nome,DataNascimento,Localidade,Rua,CodigoPostal,Telefone,Email)
    VALUES 
        (ID,Nomee,DataNascimentoo,Localidadee,Ruaa,CodigoPostall,Telefonee,Emaill);
    END IF;

    IF fail THEN 
    select ('Registo efectuado com sucesso');
    COMMIT;
    else
    select ('Cliente já está registado!');
    ROLLBACK;
    END IF;
END $$



