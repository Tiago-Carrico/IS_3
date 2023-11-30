CREATE TABLE supplier (
	id	 BIGINT,
	nome VARCHAR(512) NOT NULL,
	PRIMARY KEY(id)
);

CREATE TABLE sock (
	id		 BIGINT,
	tipo	 VARCHAR(512) NOT NULL,
	preco	 BIGINT NOT NULL,
	supplier_id BIGINT NOT NULL,
	PRIMARY KEY(id)
);

ALTER TABLE sock ADD CONSTRAINT sock_fk1 FOREIGN KEY (supplier_id) REFERENCES supplier(id);

