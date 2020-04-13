--
-- Unidade Curricular de Bases de Dados.
-- Sistemas de Dados Relacionais.
--
-- T R I G G E R S
--
-- Exemplos de Aplicação.
-- Costa, E., 2018.
--

-- Base de Dados de Trabalho.



DELIMITER $$ 
Create Trigger actualizaStock After Insert on LivroVenda
For Each Row
Begin 
Update Livro set  stock= stock-New.Quantidade
where Isbn=New.Livro;
End;
$$



DELIMITER $$
Create Trigger actualizaTotal After Insert on LivroVenda
For Each Row
Begin
Update Venda set Total=Total+(New.Preco*New.Quantidade)-(New.Preco*New.Quantidade)*Cupao
where idVenda=New.Venda;
End;
$$