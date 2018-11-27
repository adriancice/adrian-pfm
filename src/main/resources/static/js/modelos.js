var init = function() {
	$('#provincias').change(
			function() {

				var marca = $("#provincias").val();
				$('#localidades').children().remove()
				$.post('/cargarlocalidades/{id_marca}', {
					"codigo_provincia" : marca
				}, function(modelos) {
					for (i = 0; i < modelos.length; i++) {
						$('#localidades').append(
								new Option(modelos[i].modelo, true, true));
					}
				})
			})
}
$().ready(init);