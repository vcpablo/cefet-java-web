( function( app ) {
	'use strict';
	
	/**
	 * Controladora de Listagem de Clientes
	 */
	function ControladoraListagemClientes(
                        utils, 
			servicoClientes,
			$,
			toastr,
			router,
			window
			) {
		
		var listarClientes = function listarClientes( data ) {
			var tbody = $( '#clientes tbody' );
			tbody.empty();
			for ( var i in data ) {
				var obj = data[ i ];
				tbody.append(
					'<tr>' +
						'<td>' + obj.id + '</td>' +
						'<td>' + obj.nome + '</td>' +
						'<td>' + obj.cidade.nome + '</td>' +
					'</tr>'
					);
			}
			
			registrarCliqueEmLinhas();
		};
		
		
		
		var registrarCliqueEmLinhas = function registrarCliqueEmLinhas() {
			$( '#clientes tbody tr' ).click( function linhaClick() {
				$( '#clientes tbody tr' ).removeClass( 'cor-linha' );
				$( this ).toggleClass( 'cor-linha' );
			} );
		};
		
		var removerCliente = function removerCliente() {
			var id = utils.idSelecionado();
			if ( ! id ) {
				toastr.info( 'Selecione um cliente.' );
				return;
			}
			var ok = window.confirm( "Remover o cliente " + id + " ?" );
			if ( ! ok ) { return; }
			var promessa = servicoClientes.remover( id );
			promessa.done( function ( data ) {
				utils.mostrarSucesso( data );
				$( '#clientes tbody .cor-linha' ).remove();
			} );
			promessa.fail( utils.mostrarErro );
		};
		
		var alterarCliente = function alterarCliente() {
			var id = utils.idSelecionado();
			if ( ! id ) {
				toastr.info( 'Selecione um cliente.' );
				return;
			}
			router.navigate( '/clientes/' + id + '/alterar' ); 
		};
		
		var novoCliente = function novoCliente() {
			router.navigate( '/clientes/novo' );
		};
		
		var registrarCliqueBotoes = function registrarCliqueBotoes() {
			$( '#remover' ).click( removerCliente );
			$( '#alterar' ).click( alterarCliente );
			$( '#novo' ).click( novoCliente );
		};
		
		
		
		this.configurar = function configurar() {
                    var promessa = servicoClientes.clientes();
                    promessa.done( listarClientes );
                    promessa.fail( utils.mostrarErro );

                    registrarCliqueEmLinhas();
                    registrarCliqueBotoes();
		};
	}
	
	app.ControladoraListagemClientes = ControladoraListagemClientes;
	
} )( app );