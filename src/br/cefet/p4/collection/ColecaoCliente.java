package br.cefet.p4.collection;

import java.util.List;

import br.cefet.p4.Cliente;

public interface ColecaoCliente {
	
	Cliente comId(final long id) throws ColecaoException;
	
	List< Cliente > todos() throws ColecaoException;
	
	void adicionar(Cliente c)  throws ColecaoException;
	
	void atualizar(Cliente c)  throws ColecaoException;
	
	void remover(final long id)  throws ColecaoException;

}
