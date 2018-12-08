// Initialize tooltip component
$(function() {
	$('[data-toggle="tooltip"]').tooltip()
})

// Initialize popover component
$(function() {
	$('[data-toggle="popover"]').popover()
})

// popover estado suscripcion
$('#popoverActivo').popover({
	trigger : "hover"
});
$('#popoverDesactivo').popover({
	trigger : "hover"
});