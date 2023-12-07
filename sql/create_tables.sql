CREATE TABLE IF NOT EXISTS supplier (
	id	 BIGINT,
	nome VARCHAR(512) NOT NULL,
	PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS sock (
	id		 BIGINT,
	tipo	 VARCHAR(512) NOT NULL,
	preco	 BIGINT NOT NULL,
	supplier_id BIGINT NOT NULL,
	PRIMARY KEY(id)
);

ALTER TABLE sock ADD CONSTRAINT sock_fk1 FOREIGN KEY (supplier_id) REFERENCES supplier(id);

INSERT INTO supplier (id, nome) VALUES (1, 'Sock World');
INSERT INTO supplier (id, nome) VALUES (2, 'Sock Enterprise');
INSERT INTO supplier (id, nome) VALUES (3, 'Distributors of socks');
INSERT INTO supplier (id, nome) VALUES (4, 'Global Manufacturing and Distribution Services of Socks Inc.');
INSERT INTO supplier (id, nome) VALUES (5, 'Socks Socks Socks');

INSERT INTO sock (id, tipo, preco, supplier_id) VALUES (1, 'Cotton Socks', 5.99, 2);
INSERT INTO sock (id, tipo, preco, supplier_id) VALUES (2, 'Woolen Socks', 6.99, 2);
INSERT INTO sock (id, tipo, preco, supplier_id) VALUES (3, 'Ankle Socks', 4.99, 1);
INSERT INTO sock (id, tipo, preco, supplier_id) VALUES (4, 'Sports Socks', 12.99, 3);
INSERT INTO sock (id, tipo, preco, supplier_id) VALUES (5, 'Dress Socks', 15.99, 3);
INSERT INTO sock (id, tipo, preco, supplier_id) VALUES (6, 'Compression Socks', 6.99, 5);
INSERT INTO sock (id, tipo, preco, supplier_id) VALUES (7, 'Crew Socks', 4.99, 4);
INSERT INTO sock (id, tipo, preco, supplier_id) VALUES (8, 'Thermal Socks', 7.99, 3);
INSERT INTO sock (id, tipo, preco, supplier_id) VALUES (9, 'No-Show Socks', 5.99, 2);
INSERT INTO sock (id, tipo, preco, supplier_id) VALUES (10, 'Hiking Socks', 8.99, 1);
