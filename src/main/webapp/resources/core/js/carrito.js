const incrementButtons = document.querySelectorAll('.boton-compra');
const numeroDiv = document.getElementById('numero');

incrementButtons.forEach(button => {
    button.addEventListener('click', () => {
        let numeroActual = parseInt(numeroDiv.textContent);

        numeroActual++;

        numeroDiv.textContent = numeroActual;
    });
});