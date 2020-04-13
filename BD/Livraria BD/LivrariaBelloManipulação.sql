--
-- Unidade Curricular de Bases de Dados.
-- Sistemas de Dados Relacionais.
--
-- M A N I P U L A Ç Ã O
--
-- Exemplos de Aplicação.
-- Costa, E., 2018.
--

-- Base de Dados de Trabalho.


-- Total Faturado + Quantidade total de livros vendidos.
select * from (select sum(Total) as `Total Venda` from Venda) as x, (select sum(Quantidade) as `Quantidade Vendida` from LivroVenda) as y ;


 /*Quais foram os livros comprados durante o passado mês de ‘FEVEREIRO? */

select Titulo,Editora,Idioma,E.Nome,V.`Data` from Livro
inner join Escritor as E on E.idEscritor=Livro.Escritor
inner join LivroVenda as Lv on Livro.Isbn=Lv.Livro
 inner join Venda as V on V.idVenda=Lv.Venda
 where  Month(V.`Data`)='2';


-- Funcionários com os respectivos números de vendas + Total.
select F.Nome,count(V.Funcionario) as `Vendas Efectuadas`,sum(V.Total) as `Total Facturado` from Funcionario as F
INNER JOIN Venda as V on V.Funcionario=F.idFuncionario
INNER JOIN LivroVenda as Lv on Lv.Venda=V.idVenda
group by F.Nome
order by `Vendas Efectuadas` desc;


-- Conjunto de livros que ainda não foram adquiridos.
Select * from Livro
	where Isbn NOT IN ( select Distinct Livro from LivroVenda)
    order by Isbn;




DELIMITER $$ 
Create Procedure WritterXCustomerXEmployee(IN esc varchar(45),IN cli varchar(45),IN fun varchar(45))
begin
select distinct L.Isbn,L.Titulo,C.Genero,E.Nome,Cl.Nome,F.Nome from Livro as L
INNER JOIN Escritor as E on E.idEscritor=L.Escritor
INNER JOIN Categoria as C on C.idCategoria=L.Categoria
INNER JOIN LivroVenda as LV on LV.Livro=L.Isbn
INNER JOIN Venda as V on V.idVenda=Lv.Venda
INNER JOIN Funcionario as F on F.idFuncionario=V.Funcionario
INNER JOIN Cliente as Cl on Cl.idCliente=V.Cliente
where (E.Nome=esc and  Cl.Nome=cli and F.Nome=fun)
order by L.Isbn asc;
End $$

call WritterXCustomerXEmployee('Pepetela','Rita Maria Ferreira','Maria da Conceição Cruz');

-- Escritor mais vendido na localidade X
DELIMITER $$
Create procedure BestWritterinTownX (IN place varchar(45))
Begin
select E.Nome,count(E.idEscritor) as `Vendidos`  from Escritor as E
INNER JOIN Livro as L on L.Escritor=E.idEscritor
INNER JOIN LivroVenda as Lv on Lv.Livro=L.Isbn
INNER JOIN Venda as V on V.idVenda=Lv.Venda
INNER JOIN Cliente as C on C.idCliente=V.Cliente
where C.Localidade=place
group by E.Nome
order by Vendidos desc
Limit 1;
End $$

call BestWritterinTownX('Lisboa');




-- Top N livro mais vendido.
Delimiter $$ 
Create procedure TopSellers (IN N int)
Begin
select L.Isbn,L.Titulo,sum(Lv.Quantidade) as `Quantidade Vendida`,sum(V.Total) as `Total Facturado` from Livro as L
INNER JOIN LivroVenda as Lv on Lv.Livro=L.Isbn
INNER JOIN Venda as V on V.idVenda=Lv.Venda
group by L.Isbn
order by `Quantidade Vendida` desc
Limit N;
End $$

call TopSellers(10);

-- Top N dos escritores com mais vendas
DELIMITER $$
create procedure Topwritters (In N int)
 Begin
select E.* ,count(Lv.Livro) as `Vendas` from Escritor as E
INNER JOIN Livro as L on E.idEscritor=L.Escritor
INNER JOIN LivroVenda as Lv on L.Isbn=Lv.Livro
group by E.idEscritor
order by `Vendas` desc
Limit N;
END $$





-- Livros com Stock abaixo de X 
DELIMITER $$
Create Procedure StockAboveX(IN valor int )
Begin
select  * from Livro 
where (stock < valor)
order by Isbn;
END $$ 

call stockAboveX(6) ;

-- Pesquisa de Livro por palavra
Delimiter $$
Create Procedure Searchbyword (IN chave varchar(45))
Begin
select * from Livro 
where locate(chave,Titulo);
END $$

call Searchbyword('Palavrinha');

-- Pesquisa de Livro por Isbn
Delimiter $$
Create Procedure SearchbyIsbn (IN chave int )
Begin
select * from Livro
where Isbn=chave;
END $$

call SearchbyIsbn(6);


-- Procura de cliente por data de nascimento.
Delimiter $$
Create Procedure SearchbyCustomer (IN birthday date)
select * from Cliente
 where DataNascimento=birthday
 order by Cliente.Nome;
 END $$
 

 
 
 -- Pesquisa de vendas ao cliente X entre datas ( Falta acrescentar alguns campos e talvez fazer o group by)
 Delimiter $$
 create procedure SalesToCustomerXBetweenDates (IN customer int, IN inicio date,IN fim date )
 Begin
 select V.idVenda,L.Titulo,C.Nome,V.`Data` from Livro as L
 INNER JOIN LivroVenda as Lv on Lv.Livro=L.Isbn
 INNER JOIN Venda as V on Lv.Venda=V.idVenda
 INNER JOIN Funcionario as F on V.Funcionario=F.idFuncionario
 INNER JOIN Cliente as C on V.Cliente=C.idCliente
 where (C.idCliente=customer and (V.Data Between inicio and fim))
 order by V.idVenda;
 END $$


    