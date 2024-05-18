document.addEventListener('DOMContentLoaded', () => {
    const incrementButtons = document.querySelectorAll('.boton-compra');
    const numeroDiv = document.querySelector('.numero');

    // Cargar el valor almacenado en localStorage
    let numeroActual = sessionStorage.getItem('numero');
    if (numeroActual === null) {
        numeroActual = 0;
    } else {
        numeroActual = parseInt(numeroActual);
    }
    numeroDiv.textContent = numeroActual;

    incrementButtons.forEach(button => {
        button.addEventListener('click', () => {
            numeroActual++;
            numeroDiv.textContent = numeroActual;

            // Guardar el nuevo valor en localStorage
            sessionStorage.setItem('numero', numeroActual);
        });
    });
});
