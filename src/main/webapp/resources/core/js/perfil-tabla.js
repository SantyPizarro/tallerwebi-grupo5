document.addEventListener('DOMContentLoaded', function() {
    mostrarContenido('misLibros');
});

function mostrarContenido(opcion) {
    let contenidoHTML = '';
    const misLibros = [
        {
            titulo: "El principito",
            imagen: "images/elprincipito.webp"
        },
        {
            titulo: "Martin Fierro",
            imagen: "images/martin-fierro.jpg"
        },
        {
            titulo: "El Pollo Pepe",
            imagen: "images/elchico.jpg"
        },
        {
            titulo: "La Sombra del Viento",
            imagen: "images/lasombra.webp"
        },
        {
            titulo: "El jardin Secreto",
            imagen: "images/eljardin.jpg"
        }
    ];

    const misAmigos = [
        {
            nombre: "Juan Carlos",
            imagen: "images/amigo1.jpg"
        },
        {
            nombre: "Justin Holiday",
            imagen: "images/amigo2.webp"
        },
        {
            nombre: "Julieta Sullivan",
            imagen: "images/amigo3.jpeg"
        }
    ];

    const librosFavoritos = [
        {
            titulo: "El principito",
            imagen: "images/elprincipito.webp"
        },
        {
            titulo: "Martin Fierro",
            imagen: "images/martin-fierro.jpg"
        },
        {
            titulo: "El jardin Secreto",
            imagen: "images/eljardin.jpg"
        }
    ];



    const librosDeseados = [
        {
            titulo: "Don Quijote",
            imagen: "images/donquijote.jpg"
        }
    ];


    const facturas = [
        {
            orden: "1",
            titulo: "El principito",
            precio: "$5.99",
            fecha:  "20/12/2023"
        },
        {
            orden: "2",
            titulo: "Martin Fierro",
            precio: "$6.99",
            fecha:  "10/4/2024"
        },
        {
            orden:"3",
            titulo: "El jardin Secreto",
            precio: "$10.99",
            fecha: "12/2/2024"
        }
    ];
    switch (opcion) {
        case 'misLibros':
            contenidoHTML = misLibros.map(libro =>
                `<div class="card" style="width: 18rem;">
                    <img src="${libro.imagen}" class="card-img-top" alt="${libro.titulo}">
                    <div class="card-body detalle-tarjetas">
                        <h5 class="card-title">${libro.titulo}</h5>
                        <a href="#" class="btn btn-primary">Detalles</a>
                    </div>
                </div>`
            ).join('');
            break;

        case 'amigos':
            contenidoHTML = misAmigos.map(amigo =>
                `<div class="card mb-3" style="max-width: 540px;">
                    <div class="row g-0">
                        <div class="col-md-4">
                            <img src="${amigo.imagen}" class="img-fluid rounded-start" alt="${amigo.titulo}">
                        </div>
                        <div class="col-md-8">
                            <div class="card-body">
                                <h5 class="card-title">${amigo.titulo}</h5>
                                <p class="card-text">This is a wider card with supporting text below as a natural lead-in to additional content. This content is a little bit longer.</p>
                                <p class="card-text"><small class="text-body-secondary">Last updated 3 mins ago</small></p>
                            </div>
                            <span class="material-symbols-outlined">start</span>
                            <span class="material-symbols-outlined">sync_alt</span>
                        </div>
                    </div>
                </div>`
            ).join('');
            break;

        case 'favoritos':
            contenidoHTML = librosFavoritos.map(favorito =>
                `<div class="card" style="width: 18rem;">
                    <img src="${favorito.imagen}" class="card-img-top" alt="${favorito.titulo}">
                    <div class="card-body detalle-tarjetas">
                        <h5 class="card-title">${favorito.titulo}</h5>
                        <a href="#" class="btn btn-primary">Detalles</a>
                    </div>
                </div>`
            ).join('');
            break;
        case 'deseados':
            contenidoHTML = librosDeseados.map(deseado =>
                `<div class="card" style="width: 18rem;">
                    <img src="${deseado.imagen}" class="card-img-top" alt="${deseado.titulo}">
                    <div class="card-body detalle-tarjetas">
                        <h5 class="card-title">${deseado.titulo}</h5>
                        <a href="#" class="btn btn-primary">Detalles</a>
                    </div>
                </div>`
            ).join('');
            break;

        case 'facturas':
            contenidoHTML = `<table class="table">
                        <thead>
                            <tr>
                                <th>Orden</th>
                                <th>titulo</th>
                                <th>Precio</th>
                                <th>Fecha de Compra</th>
                            </tr>
                        </thead>
                        <tbody>`;
            facturas.forEach(factura => {
                contenidoHTML += `<tr>
                            <td>${factura.orden}</td>
                            <td>${factura.titulo}</td>
                            <td>${factura.precio}</td>
                            <td>${factura.fecha}</td>

                        </tr>`;
            });
            contenidoHTML += `</tbody>
                    </table>`;
            break;

        default:
            contenidoHTML = 'Opción no válida';
    }

    document.getElementById('contenido').innerHTML = contenidoHTML;
}



document.addEventListener("DOMContentLoaded", function() {
    var navLinks = document.querySelectorAll('.nav-link');

    navLinks.forEach(function(link) {
        link.addEventListener('click', function(event) {

            navLinks.forEach(function(link) {
                link.classList.remove('active');
            });


            event.target.classList.add('active');
        });
    });
});
