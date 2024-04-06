DROP TABLE IF EXISTS transportation, car,  warehouse_of_goods, warehouse, goods, supplier;

CREATE TABLE supplier(
	id BIGSERIAL,
	country TEXT,
	city TEXT,
	phone_number VARCHAR(12) NOT NULL, 
	
	CONSTRAINT pk_supplier PRIMARY KEY (id)
);

CREATE TABLE goods(
	id BIGSERIAL,
	type_of_goods TEXT,
	name TEXT NOT NULL,
	price NUMERIC(10,2) NOT NULL,
	supplier_id INTEGER,
	
	CONSTRAINT pk_goods PRIMARY KEY (id),
	
	CONSTRAINT fk_supplier_goods FOREIGN KEY (id) REFERENCES supplier (id)
	ON DELETE CASCADE
	ON UPDATE CASCADE
); 

CREATE TABLE warehouse(
	id BIGSERIAL,
	name TEXT,
	number_places INTEGER,
	country TEXT,
	city TEXT,
	address_location TEXT NOT NULL,
	
	CONSTRAINT pk_warehouse PRIMARY KEY (id)
);

CREATE TABLE warehouse_of_goods(
	id BIGSERIAL,
	warehouse_id INTEGER,
	goods_id INTEGER,
	
	CONSTRAINT pk_warehouse_of_goods PRIMARY KEY (id),
	
	CONSTRAINT fk_warehouse_warehouse_of_goods FOREIGN KEY (warehouse_id) REFERENCES warehouse (id)
	ON DELETE CASCADE
	ON UPDATE CASCADE,
	
	CONSTRAINT fk_goods_warehouse_of_goods FOREIGN KEY (goods_id) REFERENCES goods (id)
	ON DELETE CASCADE
	ON UPDATE CASCADE
);

CREATE TABLE car(
	id BIGSERIAL,
	make TEXT,
	type_of_car TEXT,
	license_plate_number VARCHAR(9) NOT NULL,
	
	CONSTRAINT pk_car PRIMARY KEY (id)
);

CREATE TABLE transportation(
	id BIGSERIAL,
	car_id INTEGER,
	goods_id INTEGER,
	pick_up_from_warehouse_id INTEGER,
	bring_to_warehouse_id INTEGER,
	date TIMESTAMP,
	
	CONSTRAINT pk_transportation PRIMARY KEY (id),
	
	CONSTRAINT fk_car_transportation FOREIGN KEY (car_id) REFERENCES car (id)
	ON DELETE CASCADE
	ON UPDATE CASCADE,
	
	CONSTRAINT fk_goods_transportation FOREIGN KEY (goods_id) REFERENCES goods (id)
	ON DELETE CASCADE
	ON UPDATE CASCADE,
	
	CONSTRAINT fk_pick_up_warehouse_transportation FOREIGN KEY (pick_up_from_warehouse_id) REFERENCES warehouse (id)
	ON DELETE CASCADE
	ON UPDATE CASCADE,
	
	CONSTRAINT fk_bring_to_warehouse_transportation FOREIGN KEY (bring_to_warehouse_id) REFERENCES warehouse (id)
	ON DELETE CASCADE
	ON UPDATE CASCADE
);
