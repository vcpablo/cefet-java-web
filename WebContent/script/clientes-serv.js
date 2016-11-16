(function (app) {
    'use strict';

    /**
     * Serviço de Clientes
     */
    function ServicoClientes($) {

        var urlBase = '/java/api/clientes';

        /**
         * Retorna uma promessa da qual os clientes
         * podem ser obtidos.
         */
        this.clientes = function clientes(id) {
            

            var options = {
                url: urlBase,
                method: 'GET'
            };
            
            if (typeof id !== 'undefined') {
                options.data = [{name:"id", value:id}];
            }

            return $.ajax(options);
        };



        this.remover = function remover(id) {
            return $.ajax({
                url: urlBase + '?id=' + id,
                method: 'DELETE'
            });
        };

        var adicionar = function adicionar(cliente) {
            return $.ajax({
                url: urlBase,
                data: cliente,
                method: 'POST'
            });
        };

        var atualizar = function atualizar(cliente) {
            return $.ajax({
                url: urlBase + "?id=" + cliente.id + "&nome=" + cliente.nome + "&cidade=" + cliente.cidade,
                method: 'PUT'
            });
        };

        this.salvar = function salvar(cliente) {
            return (isNaN(parseInt(cliente.id))) ? adicionar(cliente) : atualizar(cliente);
        };

    }

    app.ServicoClientes = ServicoClientes;

})(app);