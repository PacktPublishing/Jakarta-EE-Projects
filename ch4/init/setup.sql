# Create schema holding tenant master and config tables for tenant management
CREATE SCHEMA `tenant-mgmt-sys`;

CREATE TABLE `tenant-mgmt-sys`.`tenant_master` (
  `id` varchar(255) NOT NULL,
  `schema_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `tenant-mgmt-sys`.`tenant_config` (
  `tenant_id` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `config_value` varchar(255) DEFAULT NULL,  
  PRIMARY KEY (`tenant_id`,`name`),
  CONSTRAINT `fk_tenant` FOREIGN KEY (`tenant_id`) REFERENCES `tenant_master` (`id`)
);

# Register some tenants

insert into `tenant-mgmt-sys`.`tenant_master` (id, schema_name) values ('ACME_123', 'acme');
insert into `tenant-mgmt-sys`.`tenant_master` (id, schema_name) values ('INFI_456', 'infi');

insert into `tenant-mgmt-sys`.`tenant_config` (tenant_id, name, config_value) VALUES ('ACME_123', 'brand','Acme Motors Inc');
insert into `tenant-mgmt-sys`.`tenant_config` (tenant_id, name, config_value) VALUES ('ACME_123', 'theme','acme');
insert into `tenant-mgmt-sys`.`tenant_config` (tenant_id, name, config_value) VALUES ('ACME_123', 'domain','acme.localhost');


insert into `tenant-mgmt-sys`.`tenant_config` (tenant_id, name, config_value) VALUES ('INFI_456', 'brand','Infi Cars Ltd');
insert into `tenant-mgmt-sys`.`tenant_config` (tenant_id, name, config_value) VALUES ('INFI_456', 'theme','infi');
insert into `tenant-mgmt-sys`.`tenant_config` (tenant_id, name, config_value) VALUES ('INFI_456', 'domain','infi.localhost');

# Create schema for tenants

CREATE SCHEMA `acme`;
CREATE SCHEMA `infi`;

# Create table within the tenant's schema
CREATE TABLE `infi`.`user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

insert into `infi`.`user` (id, email, password) values (1, 'admin@infi.org', 'secret');

CREATE TABLE `infi`.`user_role` (
  `user_id` bigint(20) NOT NULL,
  `role` varchar(255) NOT NULL,
  CONSTRAINT `fk_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);

insert into `infi`.`user_role` (user_id, role) values (1, 'ADMIN');

CREATE TABLE `infi`.`spare_part` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `desc` varchar(255) DEFAULT NULL,
  `price` decimal(19,2) DEFAULT NULL,
  `brand` varchar(255) DEFAULT NULL,
  `category` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);



# Create table within the tenant's schema

CREATE TABLE `acme`.`user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

insert into `acme`.`user` (id, email, password) values (1, 'admin@acme.org', 'secret');

CREATE TABLE `acme`.`user_role` (
  `user_id` bigint(20) NOT NULL,
  `role` varchar(255) NOT NULL,
  CONSTRAINT `fk_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);

insert into `acme`.`user_role` (user_id, role) values (1, 'ADMIN');

CREATE TABLE `acme`.`spare_part` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `desc` varchar(255) DEFAULT NULL,
  `price` decimal(19,2) DEFAULT NULL,
  `brand` varchar(255) DEFAULT NULL,
  `category` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);


INSERT INTO `infi`.`spare_part` (`desc`,`name`,`price`,`brand`,`category`)
VALUES ( 'AIR FILTER FOR MARUTI 800CC', 'Air Filter', 190, 'Bosch','Filters'),
        ('AIR FILTER FOR TATA SUMO',    'Air Filter',             253,   'Bosch','Filters'),
        ('INSTRUMENT CLUSTERS FOR MAHINDRA', 'Instrument cluster', 4500, 'Mahindra Genuine Parts','Body Parts'),
        ('FENDER ASSEMBLY RIGHT HAND SIDE',  'Fender Assembly', 2990, 'Mahindra Genuine Parts','Body Parts');


INSERT INTO `acme`.`spare_part` (`desc`,`name`,`price`,`brand`,`category`)
VALUES ('Wiper Blades Set for Chevrolet Tavera', 'Wiper', 2450, 'Bosch','Wiper & Blades'),
        ('Wiper Blades Set for Fiat Siena', 'Wiper',             385,   'Bosch','Wiper & Blades');