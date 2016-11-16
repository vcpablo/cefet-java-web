(function (window, $, app, toastr) {
    'use strict';

    var configurar = function configurar() {

        console.log(window.location.hash);
        var loc = window.location.hash;
        var acao = '';
        if (/^\#\/clientes\/?$/.test(loc)) {
            acao = 'listar';
        } else if (/^\#\/clientes\/[0-9]+\/alterar$/.test(loc)) {
            acao = 'alterar';
        } else if (/^\#\/clientes\/novo$/.test(loc)) {
            acao = 'novo';
        }

        var servCli = new app.ServicoClientes($);
        var servCid = new app.ServicoCidades($);
        var ctrl;

        if ('listar' === acao) {
            $('#titulo').html('Clientes');

            $('#cliente-form').hide();
            $('#cliente-listagem').show();

            ctrl = new app.ControladoraListagemClientes(app.Utils,
                    servCli, $, toastr, app.router, window);
            ctrl.configurar();
        } else if ('novo' === acao || 'alterar' === acao) {
             ctrl = new app.ControladoraFormCliente(
                    servCli, servCid, $, app.Utils, app.router);

            
            
            if ('novo' === acao) {
                $('#titulo').html('Novo Cliente');
                $('#cliente-form').show();
                $('#cliente-listagem').hide();
                
                ctrl.configurar();
            } else if ('alterar' === acao) {
                var id = loc.match(/[0-9]+/);
                
                ctrl.configurar(id);
                
                
                $('#titulo').html('Cliente ' + id);
                $('#cliente-form').show();
                $('#cliente-listagem').hide();
            }



           

        }

    };

    $(window.document).ready(configurar);

})(window, jQuery, app, toastr);