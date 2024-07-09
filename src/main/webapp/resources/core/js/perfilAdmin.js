document.addEventListener('DOMContentLoaded', function () {
    let sliderHeroes = JSON.parse(localStorage.getItem('sliderHeroes')) || [];

    function toggleSelection(button, imageName) {

        let imageNameString = typeof imageName === 'object' ? imageName.imageName : imageName;


        if (button.classList.contains('btn-primary')) {
            fetch('/spring/select', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ imageName: imageName })
            })
                .then(response => {
                    if (response.ok) {
                        sliderHeroes.push(imageName);
                        localStorage.setItem('sliderHeroes', JSON.stringify(sliderHeroes));
                        console.log('sliderHeroes:', sliderHeroes);
                        button.classList.remove('btn-primary');
                        button.classList.add('btn-danger');
                        button.textContent = 'Deseleccionar';
                    }
                })
                .catch(error => console.error('Error al seleccionar el héroe:', error));
        } else {
            fetch('/spring/remove', {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ imageName: imageName })
            })
                .then(response => {
                    if (response.ok) {
                        sliderHeroes = sliderHeroes.filter(hero => hero !== imageName);
                        localStorage.setItem('sliderHeroes', JSON.stringify(sliderHeroes));
                        console.log('sliderHeroes:', sliderHeroes);
                        button.classList.remove('btn-danger');
                        button.classList.add('btn-primary');
                        button.textContent = 'Seleccionar';
                    }
                })
                .catch(error => console.error('Error al deseleccionar el héroe:', error));
        }
    }

    document.querySelectorAll('.btn-select').forEach(function (button) {
        button.addEventListener('click', function () {
            const imageName = this.getAttribute('data-image');
            toggleSelection(this, imageName);
        });
    });


    document.querySelectorAll('.btn-delete').forEach(function (button) {
        button.addEventListener('click', function () {
            const imageName = this.getAttribute('data-image');
            if (confirm('¿Estás seguro de que deseas eliminar esta imagen?')) {
                fetch('/spring/eliminarSliderHero', {
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