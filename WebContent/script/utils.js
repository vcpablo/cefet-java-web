( function( app, toastr ) {
	'use strict';
	
	/**
	 * Serviço de Clientes
	 */
	var Utils = {
            
                
		
		mostrarErro: function mostrarErro( message, delimiter ) {
                    if(undefined === delimiter) {
                        delimiter = "<br/>";
                    }
            
                    if(typeof message == 'string') {
                        toastr.error(message);
                    } else if(typeof message.join == 'function'){
                        toastr.error(message.join(delimiter));
                    }
                    
		},
		
		mostrarSucesso: function mostrarSucesso( message, delimiter ) {
			if(typeof message == 'string') {
                        toastr.success(message);
                    } else if(typeof message.join == 'function'){
                        toastr.success(message.join(delimiter));
                    }
		},
                
                idSelecionado: function idSelecionado() {
                    return parseInt( $( '#clientes tbody .cor-linha :first' ).html() );
                }
		
	};
	
	app.Utils = Utils; 
	
} )( app, toastr );