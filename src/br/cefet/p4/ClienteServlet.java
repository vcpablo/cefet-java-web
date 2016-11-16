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
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet implementation class ClienteServlet
 */
@WebServlet("/api/clientes")
public class ClienteServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    ColecaoCliente colecaoCliente;
    ColecaoCidade colecaoCidade;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClienteServlet() {
        super();
        FabricaConexao fabrica = new FabricaConexao();
        Connection conexao;
        try {
            conexao = fabrica.criar();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        this.colecaoCidade = new ColecaoCidadeEmBDR(conexao);
        colecaoCliente = new ColecaoClienteEmBDR(
                conexao, this.colecaoCidade);
    }

    /**
     * @throws IOException
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String conteudo;

        try {
            String idCliente = request.getParameter("id");
            if (null != idCliente && "" != idCliente) {
                conteudo = (new Gson()).toJson(colecaoCliente.comId(Integer.parseInt(idCliente)));
            } else {
                conteudo = (new Gson()).toJson(colecaoCliente.todos());
            }

            response.setStatus(200);
        } catch (Exception e) {
            response.setStatus(400);
            conteudo = "[ \"" + e.getMessage() + "\"  ]";
        }

        sendJson(response, conteudo);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> conteudo = new ArrayList<String>();
        try {
            String nome = request.getParameter("nome");
            String idCidade = request.getParameter("cidade");

            if (null == nome || nome == "" || "" == idCidade) {
                response.setStatus(400);
                conteudo.add("Nome e cidade são obrigatórios");
            } else {
                Cidade cidade = this.colecaoCidade.comId(Integer.parseInt(idCidade));
                Cliente cliente = new Cliente(0, nome, cidade);

                this.colecaoCliente.adicionar(cliente);

                response.setStatus(200);
                conteudo.add("Cliente adicionado com sucesso");
            }

        } catch (Exception e) {
            response.setStatus(400);
            conteudo.add(e.getMessage());
        }
        sendJson(response, (new Gson()).toJson(conteudo));

        // 1. validar entradas
        // 2. obter objeto de cidade, da colecao de cidades, pelo id
        // 3. criar cliente com id 0, nome recebido e objeto de cidade
        // 4. usar colecao de cliente para cadastrar o cliente
        // 5. enviar resposta para o cliente
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO alterar cliente recebido
        // 1. validar entradas
        
        List<String> conteudo = new ArrayList<String>();
        String id = request.getParameter("id");
        
        try {
            
            String nome = request.getParameter("nome");
            String idCidade = request.getParameter("cidade");
            
            
            
            if (null == id || "" == id) {
                conteudo.add("Id não enviado");
            }

            if (null == nome || "" == nome) {
                conteudo.add("Nome não enviado");
            }

            if (null == idCidade || "" == idCidade) {
                conteudo.add("Cidade não enviada");
            }

            if (conteudo.size() > 0) {
                response.setStatus(400);

            } else {
                Cidade cidade = this.colecaoCidade.comId(Integer.parseInt(idCidade));
                Cliente cliente = new Cliente(Integer.parseInt(id), nome, cidade);

                this.colecaoCliente.atualizar(cliente);

                response.setStatus(200);
                conteudo.add("Cliente atualizado com sucesso.");

            }
        } catch (ColecaoException e) {
            response.setStatus(400);
            conteudo.add("Erro ao atualizar o cliente com id " + id + ": " + e.getMessage());
        }

        sendJson(response, (new Gson()).toJson(conteudo));

        // 2. obter objeto de cidade, da colecao de cidades, pelo id
        // 3. criar cliente com id e nome recebidos e objeto de cidade
        // 4. usar colecao de cliente para atualizar o cliente
        // 5. enviar resposta para o cliente		
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        List<String> conteudo = new ArrayList<String>();

        if (null == idStr) {
            response.setStatus(400);

            conteudo.add("Id não enviado");
            sendJson(response, (new Gson()).toJson(conteudo));
            return;
        }

        long id = Long.parseLong(idStr);
        try {
            colecaoCliente.remover(id);
            conteudo.add("Id " + id + " excluído");

        } catch (ColecaoException e) {
            response.setStatus(400);
            conteudo.add("Erro removendo cliente com id " + id + ".");
        }
        sendJson(response, (new Gson()).toJson(conteudo));
    }

    private void sendJson(HttpServletResponse response, String json) throws IOException {
        response.setContentType("application/json");
        response.getWriter().write(json);
    }

}
