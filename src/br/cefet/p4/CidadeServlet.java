package br.cefet.p4;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import br.cefet.p4.collection.ColecaoCidade;
import br.cefet.p4.collection.ColecaoCliente;
import br.cefet.p4.collection.ColecaoException;
import br.cefet.p4.collection.bdr.ColecaoCidadeEmBDR;
import br.cefet.p4.collection.bdr.ColecaoClienteEmBDR;
import br.cefet.p4.collection.bdr.FabricaConexao;

/**
 * Servlet implementation class ClienteServlet
 */
@WebServlet("/api/cidades")
public class CidadeServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	ColecaoCidade colecaoCidade;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CidadeServlet() {
        super();
        FabricaConexao fabrica = new FabricaConexao();
        Connection conexao;
		try {
			conexao = fabrica.criar();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
        this.colecaoCidade = new ColecaoCidadeEmBDR( conexao );

    }

	/**
	 * @throws IOException 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String conteudo;
		try {
			conteudo = ( new Gson() ).toJson( this.colecaoCidade.todos()  );
		} catch ( Exception e ) {
			response.setStatus( 400 );
			conteudo = "[ \"" + e.getMessage() + "\"  ]";
		}
		sendJson( response, conteudo );
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO cadastrar cliente recebido
		// 1. validar entradas
		// 2. obter objeto de cidade, da colecao de cidades, pelo id
		// 3. criar cliente com id 0, nome recebido e objeto de cidade
		// 4. usar colecao de cliente para cadastrar o cliente
		// 5. enviar resposta para o cliente
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO alterar cliente recebido
		// 1. validar entradas
		// 2. obter objeto de cidade, da colecao de cidades, pelo id
		// 3. criar cliente com id e nome recebidos e objeto de cidade
		// 4. usar colecao de cliente para atualizar o cliente
		// 5. enviar resposta para o cliente		
	}	
	
		
	private void sendJson(HttpServletResponse response, String json) throws IOException {
		response.setContentType( "application/json" );
		response.getWriter().write( json );
	}

}
