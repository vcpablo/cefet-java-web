package br.cefet.p4.collection.bdr;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.cefet.p4.Cidade;
import br.cefet.p4.collection.ColecaoCidade;
import br.cefet.p4.collection.ColecaoException;

public class ColecaoCidadeEmBDR implements ColecaoCidade {

	private Connection conexao;

	public ColecaoCidadeEmBDR(Connection conexao) {
		this.conexao = conexao;
	}

	@Override
	public Cidade comId(long id) throws ColecaoException {
		String sql = "SELECT id, nome FROM cidade WHERE id = ?";
		PreparedStatement ps;
		try {
			ps = conexao.prepareStatement( sql );
			ps.setLong( 1, id );
			ResultSet rs = ps.executeQuery();
			if ( rs.next() ) {
				return criarCidade(rs);
			}			
		} catch (SQLException e) {
			throw new ColecaoException( e );
		}
		return null;
	}

	@Override
	public List<Cidade> todos() throws ColecaoException {
		String sql = "SELECT id, nome FROM cidade";
		PreparedStatement ps;
		List< Cidade > cidades = new ArrayList< Cidade >();
		try {
			ps = conexao.prepareStatement( sql );
			ResultSet rs = ps.executeQuery();
			while ( rs.next() ) {
				cidades.add( criarCidade(rs) );
			}
		} catch (SQLException e) {
			throw new ColecaoException( e );
		}
		return cidades;
	}
	
	private Cidade criarCidade(final ResultSet rs) throws SQLException {
		return new Cidade(
			rs.getLong( "id" ),
			rs.getString( "nome" )
			);
	}
}
