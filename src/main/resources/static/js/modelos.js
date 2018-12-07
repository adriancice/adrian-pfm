var init = function() {
	$('#marcas').change(
			function() {
				var marca = $("#marcas").val();
				$('#modelos').children().remove()
				$.post('/cargarModelos/{id_marca}', {
					"id_marca" : marca
				}, function(modelos) {
					for (i = 0; i < modelos.length; i++) {
						$('#modelos').append(
								new Option(modelos[i].modelo, modelos[i].modelo, true));
					}
				})
			})
}
$().ready(init);