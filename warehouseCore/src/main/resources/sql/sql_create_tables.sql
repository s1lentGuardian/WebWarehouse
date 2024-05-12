DROP TABLE IF EXISTS transportation, car,  warehouse_of_goods, warehouse, goods, transportation_goods, supplier;

CREATE TABLE supplier(
	supplier_id BIGSERIAL,
	country TEXT,
	city TEXT,
	phone_number VARCHAR(13) NOT NULL, 
	
	CONSTRAINT pk_supplier PRIMARY KEY (supplier_id)
);

CREATE TABLE goods(
	goods_id BIGSERIAL,
	type_of_goods TEXT,
	name TEXT NOT NULL,
	price NUMERIC(10,2) NOT NULL,
	supplier_id INTEGER,
	
	CONSTRAINT pk_goods PRIMARY KEY (goods_id),
	
	CONSTRAINT fk_supplier_goods FOREIGN KEY (supplier_id) REFERENCES supplier (supplier_id)
	ON DELETE CASCADE
	ON UPDATE CASCADE
); 

CREATE TABLE warehouse(
	warehouse_id BIGSERIAL,
	name TEXT,
	number_places INTEGER,
	country TEXT,
	city TEXT,
	address_location TEXT NOT NULL,
	
	CONSTRAINT pk_warehouse PRIMARY KEY (warehouse_id)
);

CREATE TABLE warehouse_of_goods(
	warehouse_of_goods_id BIGSERIAL,
	warehouse_id INTEGER,
	goods_id INTEGER,
	
	CONSTRAINT pk_warehouse_of_goods PRIMARY KEY (warehouse_of_goods_id),
	
	CONSTRAINT fk_warehouse_warehouse_of_goods FOREIGN KEY (warehouse_id) REFERENCES warehouse (warehouse_id)
	ON DELETE CASCADE
	ON UPDATE CASCADE,
	
	CONSTRAINT fk_goods_warehouse_of_goods FOREIGN KEY (goods_id) REFERENCES goods (goods_id)
	ON DELETE CASCADE
	ON UPDATE CASCADE
);

CREATE TABLE car(
	car_id BIGSERIAL,
	make TEXT,
	type_of_car TEXT,
	license_plate_number VARCHAR(9) NOT NULL,
	
	CONSTRAINT pk_car PRIMARY KEY (car_id)
);

CREATE TABLE transportation(
	transportation_id BIGSERIAL,
	car_id INTEGER,
	pick_up_from_warehouse_id INTEGER,
	bring_to_warehouse_id INTEGER,
	goods_count INTEGER,
	date TIMESTAMP,
	
	CONSTRAINT pk_transportation PRIMARY KEY (transportation_id),
	
	CONSTRAINT fk_car_transportation FOREIGN KEY (car_id) REFERENCES car (car_id)
	ON DELETE CASCADE
	ON UPDATE CASCADE,
	
	CONSTRAINT fk_pick_up_warehouse_transportation FOREIGN KEY (pick_up_from_warehouse_id) REFERENCES warehouse (warehouse_id)
	ON DELETE CASCADE
	ON UPDATE CASCADE,
	
	CONSTRAINT fk_bring_to_warehouse_transportation FOREIGN KEY (bring_to_warehouse_id) REFERENCES warehouse (warehouse_id)
	ON DELETE CASCADE
	ON UPDATE CASCADE
);

CREATE TABLE transportation_goods(
	transportation_goods_id BIGSERIAL,
	transportation_id INTEGER,
	goods_id INTEGER,
	
	CONSTRAINT pk_transportation_goods PRIMARY KEY (transportation_goods_id),
	
	CONSTRAINT fk_transportation_transportation_goods FOREIGN KEY (transportation_id) REFERENCES transportation (transportation_id)
	ON DELETE CASCADE
	ON UPDATE CASCADE,
	
	CONSTRAINT fk_goods_transportation_goods FOREIGN KEY (goods_id) REFERENCES goods (goods_id)
	ON DELETE CASCADE
	ON UPDATE CASCADE
);
