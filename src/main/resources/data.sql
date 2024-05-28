INSERT INTO Usuario(id, email, password, rol, activo,descripcion,nombreDeUsuario,generoFav1,generoFav2,foto) VALUES(null, 'test@unlam.edu.ar', 'test', 'ADMIN', true,'Me llamo Tomas y he sido un ávido lector desde que era niño. Mis géneros favoritos son ficcion y deporte','Tomas Pezzano','ficcion','deporte','mifoto2.jpg');

INSERT INTO Genero (id, nombre) VALUES
                                    (1, 'Ficcion'),
                                    (2, 'Cuento'),
                                    (3,'Novela'),
                                    (4,'Poesia');

INSERT INTO Libro(id, titulo, autor, editorial, fechaPublicacion, precio, ruta, descripcion, genero_id) VALUES
                                                                                    (NULL, 'El Principito', 'Stephen Hawking', 'Planeta', '01-01-1988', 5500, '/spring/images/libros/elprincipito.jpg', 'Es un cuento poético y filosófico que narra las aventuras de un joven príncipe que viaja de planeta en planeta, explorando la naturaleza humana y el amor. Es conocido por sus profundas reflexiones y su sencillez encantadora.', 1),
                                                                                    (NULL, 'Don Quijote de la Mancha', 'Miguel de Cervantes', 'Anaya', '01-01-1605', 8000, '/spring/images/libros/donquijote.jpg', 'Es una novela épica sobre un noble español, Don Quijote, que pierde la cordura y se convierte en un caballero andante, acompañado por su fiel escudero Sancho Panza. Es una sátira de las novelas de caballería y un comentario sobre la locura y la realidad.', 2),
                                                                                    (NULL, 'Martín Fierro', 'José Hernández', 'Losada', '01-01-1872', 12000, '/spring/images/libros/martinfierro.jpg', 'Es un poema épico argentino que sigue las aventuras del gaucho Martín Fierro, un hombre de la pampa que lucha contra la injusticia y la opresión en el siglo XIX. Es una obra fundamental de la literatura argentina y un símbolo de la identidad nacional.', 3),
                                                                                    (NULL, 'El Fantasma de Canterville', 'Oscar Wilde', 'Alianza Editorial', '01-01-1887', 20000, '/spring/images/libros/elfantasma.jpg', 'Es una comedia sobrenatural sobre un fantasma inglés, Sir Simon, que intenta asustar a una familia estadounidense moderna que se ha mudado a su mansión. La historia combina humor y crítica social.', 4),
                                                                                    (NULL, 'El Jardín Secreto', 'Frances Hodgson Burnett', 'Salamandra', '01-01-1911', 7300, '/spring/images/libros/eljardin.jpg', 'Es una novela clásica infantil que narra la historia de Mary Lennox, una niña huérfana que descubre un jardín abandonado en la casa de su tío. A medida que revive el jardín, ella también encuentra la amistad y la esperanza.', 4),
                                                                                    (NULL, 'La Sombra del Viento', 'Carlos Ruiz Zafón', 'Editorial Planeta', '01-01-2001', 25000, '/spring/images/libros/lasombra.jpg', 'Es una novela ambientada en la Barcelona de la posguerra, donde un joven llamado Daniel Sempere descubre un misterioso libro que lo lleva a desentrañar los secretos oscuros del autor y de su propia vida. Es una mezcla de thriller, romance e historia.', 2),
                                                                                    (NULL, 'El Chico que Bajo las Estrellas', 'Jordi Sierra i Fabra', 'Random House', '01-01-2022', 15000, '/spring/images/libros/elchico.jpg', 'Es una novela juvenil que sigue a un joven que, enfrentándose a la adversidad, logra superar sus miedos y cumplir sus sueños. Es una historia inspiradora sobre la perseverancia y el coraje.', 3),
                                                                                    (NULL, 'El Alquimista', 'Paulo Coelho', 'Anaya', '01-01-2010', 1000, '/spring/images/libros/elalquimista.jpg', 'Es una novela filosófica que cuenta la historia de Santiago, un joven pastor andaluz que busca un tesoro en las pirámides de Egipto. En su viaje, aprende sobre el amor, el destino y la importancia de seguir los sueños.' ,1);
