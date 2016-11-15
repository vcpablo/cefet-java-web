( function( app ) {
	'use strict';
	
	/**
	 * Controladora de Formulário de Clientes
	 */
	function ControladoraFormCliente( servicoClientes, $, toastr ) {
				
		var mostrarErro = function mostrarErro( jqXhr ) {
			toastr.error( jqXhr.responseJSON.join( ", ") );
		};
		
		var mostrarSucesso = function mostrarSucesso( data ) {
			toastr.success( data.join( ", " ) );
		};
				
		var registrarCliqueBotoes = function registrarCliqueBotoes() {
		};
		
		var idSelecionado = function idSelecionado() {
			return parseInt( $( '#clientes tbody .cor-linha :first' ).html() );
		};
		
		this.configurar = function configurar() {
			registrarCliqueBotoes();
		};
	}
	
	app.ControladoraFormCliente = ControladoraFormCliente;
	
} )( app );