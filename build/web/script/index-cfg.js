( function( document, $, app ) {
	'use strict';
	
	var router = app.router;
	var conteudo = $( '#conteudo' ); 
	
	var carregarHome = function carregarHome( e ) {
		//e.preventDefault();
		conteudo.html( '' );
	};
	
	var carregarPagina = function carregarPagina( pagina, fn ) {
		return conteudo.html( '' ).load( pagina, fn );
	};
	
	var configurarRotas = function configurarRotas() {
		
		router.get( '/', function( req, e ) {
		    carregarHome();
		} );		
		
		router.get( '/clientes/?*', function( req, e ) {
                    carregarPagina( 'clientes.html' );
		} );		
	
		router.get( '/*', function( req, e ) {
			if ( e.parent() ) { return; }
			carregarPagina( '404.html' );
		} );
		
	};
	
	var configurarIndex = function configurarIndex() {
		configurarRotas();
	};
	
	$( document ).ready( configurarIndex );
	
} )( document, jQuery, app );