Integration with Spring, DBUnit, LiquidBase, Hibernate and H2 to demonstrate an architecture testable

Arquitetura de referência
----------------------------------------

Demonstrando como fazer as ferramentas a baixo trabalhem em conjunto para realizar testes automátizados.

	* Hibernate - Realiza o mapeamento objeto relacional
	* H2 - Banco de dados em memória, carregado quando carrega o driver JDBC, isto quer dizer que a cada vez que o carrega o contexto do spring, o banco está no ar
	* LiquidBase - Utilizado para criar as tabelas no banco de dados durante a execução dos testes
	* DBUnit - Insere dados no banco de dados apartir de um .xml
	* Spring - Integra os diversos frameworks, e oferece facilidades diversas como injeção de dependência.


Author:	
	http://www.linkedin.com/in/claytonpassos
	http://claytonpassos.branded.me