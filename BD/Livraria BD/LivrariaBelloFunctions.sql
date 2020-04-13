--
-- Unidade Curricular de Bases de Dados.
-- Sistemas de Dados Relacionais.
--
-- F U N C T I O N S
--
-- Exemplos de Aplicação.
-- Costa, E., 2018.
--

-- Base de Dados de Trabalho.

 -- Facturação num intervalo de tempo
 Delimiter $$
 Create function BillingBetweenDates 
	(inicio date, fim date)
			returns decimal (6,2) deterministic
    Begin
 Declare valor decimal (6,2);
select Sum(Total) Into valor from Venda
     where
       Data Between inicio and fim;
       Return valor;
End $$