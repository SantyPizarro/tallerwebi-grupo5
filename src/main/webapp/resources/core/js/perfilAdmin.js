document.addEventListener('DOMContentLoaded', function () {
    document.querySelectorAll('.btn-delete').forEach(function (button) {
        button.addEventListener('click', function () {
            const imageName = this.getAttribute('data-image');
            if (confirm('¿Estás seguro de que deseas eliminar esta imagen?')) {
                fetch('/eliminarSliderHero', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    },
                    body: new URLSearchParams({imageName: imageName})
                }).then(response => {
                    if (response.ok) {
                        location.reload();
                    } else {
                        alert('Error al eliminar la imagen');
                    }
                }).catch(error => {
                    console.error('Error:', error);
                    alert('Error al eliminar la imagen');
                });
            }
        });
    });
});