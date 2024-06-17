
INSERT INTO tipoplan (id,nombre) values (1,"basic");
INSERT INTO tipoplan (id,nombre) values (2,"standard");
INSERT INTO tipoplan (id,nombre) values (3,"premium");

INSERT INTO plan (id,tipo_plan_id) values (1,1);
INSERT INTO plan (id,tipo_plan_id) values (2,2);
INSERT INTO plan (id,tipo_plan_id) values (3,3);


INSERT INTO Usuario(id, email, password, rol, activo,descripcion,nombreDeUsuario,generoFav1,generoFav2,foto) VALUES(null, 'test@unlam.edu.ar', 'test', 'ADMIN', true,'Me llamo Tomas y he sido un ávido lector desde que era niño. Mis géneros favoritos son ficcion y deporte','Tomas Pezzano','ficcion','deporte','mifoto2.jpg');
INSERT INTO Usuario(id, email, password, rol, activo,descripcion,nombreDeUsuario,generoFav1,generoFav2,foto,emailVerificado) VALUES(null, 'asd@asd.com', '123', 'user', true,'Me llamo Mati y he sido un ávido lector desde que era niño. Mis géneros favoritos son ficcion y deporte','MatiFdz','ficcion','deporte','amigo3.jpeg',true);
INSERT INTO Usuario(id, email, password, rol, activo,descripcion,nombreDeUsuario,generoFav1,generoFav2,foto,emailVerificado) VALUES(null, 'marcos@hotmail.com', '123', 'user', true,'Me llamo Marcos y he sido un ávido lector desde que era niño. Mis géneros favoritos son ficcion y deporte','Marcos123','ficcion','deporte','loroo.PNG',true);
INSERT INTO Usuario(id, email, password, rol, activo,descripcion,nombreDeUsuario,generoFav1,generoFav2,foto,emailVerificado) VALUES(null, 'qwe@qwe.com', '123', 'user', true,'Me llamo Arjona y he sido un ávido lector desde que era niño. Mis géneros favoritos son ficcion y deporte','Arjona456','ficcion','deporte','arjona.jpg',true);
INSERT INTO Usuario(id, email, password, rol, activo,descripcion,nombreDeUsuario,generoFav1,generoFav2,foto,emailVerificado) VALUES(null, 'zxc@zxc.com', '123', 'user', true,'Me llamo Daniel y he sido un ávido lector desde que era niño. Mis géneros favoritos son ficcion y deporte ','Dani789','ficcion','deporte','amigo1.jpg',true);

INSERT INTO Genero (id, nombre) VALUES
                                    (1, 'Ficcion'),
                                    (2, 'Cuento'),
                                    (3,'Novela'),
                                    (4,'Poesia');

INSERT INTO Libro(id, titulo, autor, editorial, fechaPublicacion, precio, ruta, descripcion, fechaAgregado, genero_id) VALUES
                                                                                                                           (NULL, 'El Principito', 'Stephen Hawking', 'Planeta', '1988-01-01', 5500, '/spring/images/libros/elprincipito.jpg', 'Es un cuento poético y filosófico que narra las aventuras de un joven príncipe que viaja de planeta en planeta, explorando la naturaleza humana y el amor. Es conocido por sus profundas reflexiones y su sencillez encantadora.', '2024-01-01', 1),
                                                                                                                           (NULL, 'Don Quijote de la Mancha', 'Miguel de Cervantes', 'Anaya', '1605-01-01', 8000, '/spring/images/libros/donquijote.jpg', 'Es una novela épica sobre un noble español, Don Quijote, que pierde la cordura y se convierte en un caballero andante, acompañado por su fiel escudero Sancho Panza. Es una sátira de las novelas de caballería y un comentario sobre la locura y la realidad.', '2024-02-01', 2),
                                                                                                                           (NULL, 'Martín Fierro', 'José Hernández', 'Losada', '1872-01-01', 12000, '/spring/images/libros/martinfierro.jpg', 'Es un poema épico argentino que sigue las aventuras del gaucho Martín Fierro, un hombre de la pampa que lucha contra la injusticia y la opresión en el siglo XIX. Es una obra fundamental de la literatura argentina y un símbolo de la identidad nacional.', '2024-03-01', 3),
                                                                                                                           (NULL, 'El Fantasma de Canterville', 'Oscar Wilde', 'Alianza Editorial', '1887-01-01', 20000, '/spring/images/libros/elfantasma.jpg', 'Es una comedia sobrenatural sobre un fantasma inglés, Sir Simon, que intenta asustar a una familia estadounidense moderna que se ha mudado a su mansión. La historia combina humor y crítica social.', '2024-04-01', 4),
                                                                                                                           (NULL, 'El Jardín Secreto', 'Frances Hodgson Burnett', 'Salamandra', '1911-01-01', 7300, '/spring/images/libros/eljardin.jpg', 'Es una novela clásica infantil que narra la historia de Mary Lennox, una niña huérfana que descubre un jardín abandonado en la casa de su tío. A medida que revive el jardín, ella también encuentra la amistad y la esperanza.', '2024-05-01', 4),
                                                                                                                           (NULL, 'La Sombra del Viento', 'Carlos Ruiz Zafón', 'Editorial Planeta', '2001-01-01', 25000, '/spring/images/libros/lasombra.jpg', 'Es una novela ambientada en la Barcelona de la posguerra, donde un joven llamado Daniel Sempere descubre un misterioso libro que lo lleva a desentrañar los secretos oscuros del autor y de su propia vida. Es una mezcla de thriller, romance e historia.', '2024-06-01', 2),
                                                                                                                           (NULL, 'El Chico que Bajo las Estrellas', 'Jordi Sierra i Fabra', 'Random House', '2022-01-01', 15000, '/spring/images/libros/elchico.jpg', 'Es una novela juvenil que sigue a un joven que, enfrentándose a la adversidad, logra superar sus miedos y cumplir sus sueños. Es una historia inspiradora sobre la perseverancia y el coraje.', '2024-07-01', 3),
                                                                                                                           (NULL, 'El Alquimista', 'Paulo Coelho', 'Anaya', '2010-01-01', 1000, '/spring/images/libros/elalquimista.jpg', 'Es una novela filosófica que cuenta la historia de Santiago, un joven pastor andaluz que busca un tesoro en las pirámides de Egipto. En su viaje, aprende sobre el amor, el destino y la importancia de seguir los sueños.', '2024-08-01', 1);

