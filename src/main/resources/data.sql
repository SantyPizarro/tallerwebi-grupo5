INSERT INTO tipoplan (id, nombre, descripcion) VALUES (1, 'free', '* Comprar libros ilimitados<br>* Agregar amigos');
INSERT INTO tipoplan (id, nombre, descripcion) VALUES (2, 'basic', '* Comprar libros ilimitados<br>* Agregar amigos<br>* Código de descuento de regalo para tu compra<br>* Intercambiá libros con tus amigos');
INSERT INTO tipoplan (id, nombre, descripcion) VALUES (3, 'standard', '* Comprar libros ilimitados<br>* Agregar amigos<br>* Código de descuento de regalo para tu compra<br>* Intercambiá libros con tus amigos<br>* 2 libros de regalo<br>*A la cuarta compra cada dos compras realizadas tenes un nuevo cupón de descuento');
INSERT INTO tipoplan (id, nombre, descripcion) VALUES (4, 'premium', '* Comprar libros ilimitados<br>* Agregar amigos<br>* Código de descuento de regalo para tu compra<br>* Intercambiá libros con tus amigos<br>* 2 libros de regalo<br>* A la cuarta compra cada dos compras realizadas tenes un nuevo cupón de descuento<br>* Plan canje! entrega tu libro como forma de pago y llevate uno nuevo');
INSERT INTO plan (id,tipo_plan_id) values (1,1);
INSERT INTO plan (id,tipo_plan_id) values (2,2);
INSERT INTO plan (id,tipo_plan_id) values (3,3);
INSERT INTO plan (id,tipo_plan_id) values (4,4);

INSERT INTO codigodescuento(id) values (1);

INSERT INTO Usuario(id, email, password, rol, activo,descripcion,nombreDeUsuario,generoFav1,generoFav2,foto) VALUES(null, 'test@unlam.edu.ar', 'test', 'ADMIN', true,'Me llamo Tomas y he sido un ávido lector desde que era niño. Mis géneros favoritos son ficcion y deporte','Tomas Pezzano','ficcion','deporte','mifoto2.jpg');
INSERT INTO Usuario(id, email, password, rol, activo,descripcion,nombreDeUsuario,generoFav1,generoFav2,foto,emailVerificado, plan_id) VALUES(null, 'asd@asd.com', '123', 'user', true,'Me llamo Mati y he sido un ávido lector desde que era niño. Mis géneros favoritos son ficcion y deporte','MatiFdz','ficcion','deporte','amigo3.jpeg',true, 1);
INSERT INTO Usuario(id, email, password, rol, activo,descripcion,nombreDeUsuario,generoFav1,generoFav2,foto,emailVerificado, plan_id) VALUES(null, 'marcos@hotmail.com', '123', 'user', true,'Me llamo Marcos y he sido un ávido lector desde que era niño. Mis géneros favoritos son ficcion y deporte','Marcos123','ficcion','deporte','loroo.PNG',true, 1);
INSERT INTO Usuario(id, email, password, rol, activo,descripcion,nombreDeUsuario,generoFav1,generoFav2,foto,emailVerificado, plan_id) VALUES(null, 'qwe@qwe.com', '123', 'user', true,'Me llamo Arjona y he sido un ávido lector desde que era niño. Mis géneros favoritos son ficcion y deporte','Arjona456','ficcion','deporte','arjona.jpg',true, 1);
INSERT INTO Usuario(id, email, password, rol, activo,descripcion,nombreDeUsuario,generoFav1,generoFav2,foto,emailVerificado, plan_id) VALUES(null, 'zxc@zxc.com', '123', 'user', true,'Me llamo Daniel y he sido un ávido lector desde que era niño. Mis géneros favoritos son ficcion y deporte ','Dani789','ficcion','deporte','amigo1.jpg',true, 1);

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
                                                                                                                           (NULL, 'El Alquimista', 'Paulo Coelho', 'Anaya', '2010-01-01', 1000, '/spring/images/libros/elalquimista.jpg', 'Es una novela filosófica que cuenta la historia de Santiago, un joven pastor andaluz que busca un tesoro en las pirámides de Egipto. En su viaje, aprende sobre el amor, el destino y la importancia de seguir los sueños.', '2024-08-01', 1),
                                                                                                                           (NULL, 'Cien Años de Soledad', 'Gabriel García Márquez', 'Sudamericana', '1967-01-01', 15000, '/spring/images/libros/cienanios.jpg', 'Es una obra maestra del realismo mágico que sigue a la familia Buendía a lo largo de varias generaciones en el pueblo ficticio de Macondo.', '2024-09-01', 1),
                                                                                                                           (NULL, 'La Casa de los Espíritus', 'Isabel Allende', 'Debolsillo', '1982-01-01', 13000, '/spring/images/libros/lacasa.jpg', 'Es una saga familiar que mezcla elementos de realismo mágico y política, siguiendo la vida de la familia Trueba en un país sudamericano imaginario.', '2024-10-01', 2),
                                                                                                                           (NULL, 'El Gran Gatsby', 'F. Scott Fitzgerald', 'Scribner', '1925-01-01', 9000, '/spring/images/libros/gatsby.jpg', 'Es una novela sobre el sueño americano y la decadencia moral, centrada en el misterioso millonario Jay Gatsby y su obsesión con la bella Daisy Buchanan.', '2024-11-01', 3),
                                                                                                                           (NULL, 'Matar a un Ruiseñor', 'Harper Lee', 'J.B. Lippincott & Co.', '1960-01-01', 8000, '/spring/images/libros/ruisenor.jpg', 'Es una novela sobre la injusticia racial y la pérdida de la inocencia en el sur de Estados Unidos, vista a través de los ojos de Scout Finch, una niña.', '2024-12-01', 4),
                                                                                                                           (NULL, '1984', 'George Orwell', 'Secker & Warburg', '1949-01-01', 10000, '/spring/images/libros/1984.jpg', 'Es una novela distópica que describe un futuro totalitario donde el gobierno controla todos los aspectos de la vida y la privacidad es inexistente.', '2024-12-01', 1),
                                                                                                                           (NULL, 'Rayuela', 'Julio Cortázar', 'Sudamericana', '1963-01-01', 12000, '/spring/images/libros/rayuela.jpg', 'Es una novela experimental que invita al lector a leerla de diversas maneras, explorando el amor, el arte y la existencia.', '2024-10-01', 3),
                                                                                                                           (NULL, 'El Amor en Tiempos de Cólera', 'Gabriel García Márquez', 'Oveja Negra', '1985-01-01', 14000, '/spring/images/libros/amorcolera.jpg', 'Es una historia de amor que sigue a Florentino Ariza y Fermina Daza durante más de cincuenta años en un Caribe vibrante y en transformación.', '2024-09-01', 4),
                                                                                                                           (NULL, 'Los Miserables', 'Victor Hugo', 'A. Lacroix, Verboeckhoven & Cie.', '1862-01-01', 18000, '/spring/images/libros/miserables.jpg', 'Es una novela épica que narra la vida de varios personajes en Francia durante la primera mitad del siglo XIX, centrada en la redención del ex convicto Jean Valjean.', '2024-08-01', 1),
                                                                                                                           (NULL, 'El Conde de Montecristo', 'Alexandre Dumas', 'Penguin Classics', '1844-01-01', 20000, '/spring/images/libros/montecristo.jpg', 'Es una novela de aventuras que narra la historia de Edmundo Dantés, quien es traicionado y encarcelado injustamente, y su posterior venganza.', '2024-07-01', 2);
