(function (app) {
    'use strict';

    /**
     * Controladora de Formulário de Clientes
     */
    function ControladoraFormCliente(servicoClientes, servicoCidades, $, utils, router) {
        var validarCliente = function validarCliente(cliente) {
            var erros = [];

            if (cliente.nome == '') {
                erros.push("O nome é obrigatório");
            }

            if (cliente.cidade == '') {
                erros.push("A cidade é obrigatória");
            }

            return (erros.length > 0) ? utils.mostrarErro(erros) : true;
        };

        var salvarCliente = function salvarCliente() {

            var cliente = {
                id: $("#id").val(),
                nome: $("#nome").val(),
                cidade: $("#cidade").val()
            };

            if (validarCliente(cliente)) {
                var promessa = servicoClientes.salvar(cliente);

                promessa.done(function (data) {

                    utils.mostrarSucesso(data);
                    router.navigate('/clientes');
                    
                });
                promessa.fail(function (jqXhr) {
                    console.log(jqXhr);
                    utils.mostrarErro(jqXhr.responseJSON);
                });
            }


        };

        var listarCidades = function (data, id) {
            var html = '', cidade;


            if (data.length > 0) {
                html += '<option value=""> Selecione uma opção</option>';
                for (var i in data) {
                    cidade = data[i];
                    html += '<option value="' + cidade.id + '"> ' + cidade.nome + '</option>';
                }

            }

            $("#cidade").html(html);

            if (typeof id !== 'undefined') {
                $("#cidade").val(id);
            }
        };

        var listarCliente = function listarCliente(cliente) {
            $("#id").val(cliente.id);
            $("#nome").val(cliente.nome);
            $("#cidade").val(cliente.cidade.id);
        };

        var registrarCliqueBotoes = function registrarCliqueBotoes() {
            $('#salvar').click(salvarCliente);
        };


        this.configurar = function configurar(id) {
            registrarCliqueBotoes();

            var promessaCidades = servicoCidades.cidades();
            

            if (typeof id !== 'undefined') { // Atualizar
                var promessaCliente = servicoClientes.clientes(id);
                
                promessaCidades.done(function(data) {
                    listarCidades(data);
                    promessaCliente.done(listarCliente);
                });
                
                promessaCliente.fail(utils.mostrarErro);

            } else {
                promessaCidades.done(listarCidades);
            }

            promessaCidades.fail(utils.mostrarErro);
        };
    }

    app.ControladoraFormCliente = ControladoraFormCliente;

})(app);