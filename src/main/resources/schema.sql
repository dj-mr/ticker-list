create schema ticker_list;

create table if not exists organization_details
(
	cik varchar(255) not null
		constraint organization_details_pkey
			primary key,
	address varchar(255),
	created_at timestamp,
	date_of_last_update varchar(255),
	fiscal_year_end varchar(255),
	irs_number varchar(255),
	name varchar(255),
	phone_number varchar(255),
	regulated_entity_type varchar(255),
	reporting_file_number varchar(255),
	sic_code varchar(255),
	state_of_incorporation varchar(255)
);

create table if not exists sic_data
(
	sic_code varchar(255) not null
		constraint sic_data_pkey
			primary key,
	industry varchar(255),
	sic_title varchar(255)
);

create table if not exists ticker_cik_map
(
	cik varchar(255) not null
		constraint ticker_cik_map_pkey
			primary key,
	name varchar(255),
	ticker varchar(255)
);

insert into ticker_list.sic_data values ('N/A', 'N/A', 'N/A');

