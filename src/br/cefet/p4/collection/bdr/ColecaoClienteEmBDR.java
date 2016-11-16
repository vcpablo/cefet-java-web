package br.cefet.p4.collection.bdr;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.cefet.p4.Cliente;
import br.cefet.p4.Cidade;
import br.cefet.p4.collection.ColecaoCidade;
import br.cefet.p4.collection.ColecaoCliente;
import br.cefet.p4.collection.ColecaoException;

public class ColecaoClienteEmBDR implements ColecaoCliente {

    private Connection conexao;
    private ColecaoCidade colecaoCidade;

    public ColecaoClienteEmBDR(
            Connection conexao,
            ColecaoCidade colecaoCidade
    ) {
        this.conexao = conexao;
        this.colecaoCidade = colecaoCidade;
    }

    @Override
    public Cliente comId(long id) throws ColecaoException {
        String sql = "SELECT id, nome, cidade_id FROM cliente WHERE id = ?";
        PreparedStatement ps;
        try {
            ps = conexao.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return criarCliente(rs);
            }
        } catch (Exception e) {
            throw new ColecaoException(e);
        }
        return null;
    }

    @Override
    public List<Cliente> todos() throws ColecaoException {
        String sql = "SELECT id, nome, cidade_id FROM cliente";
        PreparedStatement ps;
        List< Cliente> cidades = new ArrayList< Cliente>();
        try {
            ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cidades.add(criarCliente(rs));
            }
        } catch (Exception e) {
            throw new ColecaoException(e);
        }
        return cidades;
    }

    @Override
    public void adicionar(Cliente c) throws ColecaoException {
        String sql = "INSERT INTO cliente (nome, cidade_id) VALUES (?,?)";
        
        PreparedStatement ps;
        
        try {
            ps = conexao.prepareStatement(sql);
            
            ps.setString(1, c.getNome());
            ps.setLong(2, c.getCidade().getId());
            
            ps.execute();
        } catch (SQLException e) {
            throw new ColecaoException(e);
        }

    }

    @Override
    public void atualizar(Cliente c) throws ColecaoException {
        String sql = "UPDATE cliente SET nome = ?, cidade_id = ? WHERE id = ?";
        
        PreparedStatement ps;
        
        try {
            ps = conexao.prepareStatement(sql);
            
            ps.setString(1, c.getNome());
            ps.setLong(2, c.getCidade().getId());
            ps.setLong(3, c.getId());
            
            ps.execute();
        } catch (SQLException e) {
            throw new ColecaoException(e);
        }
    }

    @Override
    public void remover(long id) throws ColecaoException {
        String sql = "DELETE FROM cliente WHERE id = ?";
        PreparedStatement ps;
        try {
            ps = conexao.prepareStatement(sql);
            ps.setLong(1, id);
            ps.execute();
        } catch (SQLException e) {
            throw new ColecaoException(e);
        }
    }

    private Cliente criarCliente(final ResultSet rs) throws Exception {
        return new Cliente(
                rs.getLong("id"),
                rs.getString("nome"),
                colecaoCidade.comId(rs.getLong("cidade_id"))
        );
    }

    public Cidade cidadeComId (int id) throws ColecaoException {
        return this.colecaoCidade.comId(id);
    }

}
