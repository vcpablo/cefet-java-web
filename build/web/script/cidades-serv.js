( function( app ) {
	'use strict';
	
	/**
	 * Serviço de Clientes
	 */
	function ServicoCidades( $ ) {
		
		var urlBase = '/java/api/cidades'; 
		
		/**
		 * Retorna uma promessa da qual as cidades
		 * podem ser obtidas.
		 */
		this.cidades = function cidades() {
			return $.ajax( {
				url: urlBase,
				method: 'GET'
			} );
		};
		
		
		
	}
	
	app.ServicoCidades = ServicoCidades; 
	
} )( app );