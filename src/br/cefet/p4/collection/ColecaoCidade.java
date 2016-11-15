package br.cefet.p4.collection;

import java.util.List;

import br.cefet.p4.Cidade;

public interface ColecaoCidade {

	Cidade comId(long id) throws ColecaoException;

	List<Cidade> todos() throws ColecaoException;

}
