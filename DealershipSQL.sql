------- Dealership sql

--User Table
create table users(
 username text,
 pass text,
 first_name text,
 last_name text,
 remember_me boolean,
 is_employee boolean,
 is_system boolean,
 primary key (username)
);

--Car Table
create table cars(
	car_id serial,	
	car_name text,
	brand text,
	car_type text,
	color text,
	smell integer,
	car_year integer,
	price decimal(18,2),
	is_owned boolean,
	primary key(car_id)
);

--Car Offer
create table offers(
	offer_id serial,
	username text,
	car_id integer,
	offer_amount decimal(18,2),
	accepted boolean default(false),
	rejected boolean default(false),
	foreign key (username) references users(username),
	foreign key (car_id) references cars(car_id),
	primary key(offer_id)
	
);

--Car owned
create table ownerships(
	own_id serial,
	username text,
	car_id integer unique not null,
	monthly_amount decimal(18,2) default(0.00),
	amount_remaining decimal(18,2) default(0.00),
	offer_amount decimal(18,2) default(0.00),
	purchase_date date,
	foreign key (username) references users(username),
	foreign key (car_id) references cars(car_id),
	primary key(own_id)
);

--User payments
create table payments(
	payment_id serial,
	username text,
	car_id integer,
	amount_remaining decimal(18,2) default(0.00),
	payment decimal(18,2) default(0.00),
	payment_date date not null,
	foreign key (username) references users(username),
	foreign key (car_id) references cars(car_id),
	primary key(payment_id)
);

--inserts
insert into users (username,pass,first_name,last_name,remember_me,is_employee,is_system) values ('system', 'googis', 'Dylan', 'McDonald', false, false, true);
insert into users (username,pass,first_name,last_name,remember_me,is_employee,is_system) values ('steve', 'jobs', 'Steven', 'Joben', false, true, false);
select * from users;

update users set remember_me = 'false' where username = 'gungy';

delete from users where username = 'bo';

insert into cars (car_name,brand,car_type,color,smell,car_year,price,is_owned) values ('Chungo Bungo','Tesla','Electric', 'Diarrhea Yellow', 69, 2035,1000000,false);
insert into cars (car_name,brand,car_type,color,smell,car_year,price,is_owned) values ('Forester','Subaru','SUV', 'Black', 10, 2010,15000,false);
insert into cars (car_name,brand,car_type,color,smell,car_year,price,is_owned) values ('Fit','Honda','Subcompact', 'Cyan', 3, 2016,11000,false);
insert into cars (car_name,brand,car_type,color,smell,car_year,price,is_owned) values ('Beetle','Volkswagen','Compact', 'Bright Pink', 6, 2012,14000,true);
select * from cars;

insert into offers(username, car_id, offer_amount) values ('gungy', 2, 20000);
insert into offers(username, car_id, offer_amount) values ('steve', 1, 69000);
insert into offers(username, car_id, offer_amount) values ('dungo', 1, 99000);
select * from offers;

delete from offers where username = 'bo';

update offers set accepted = true where username = 'gungy';

insert into ownerships(username, car_id, purchase_date, monthly_amount, amount_remaining, offer_amount) values ('gungy', 5, '20130401', 186.55, 0.00, 13000.00);
delete from ownerships where username = 'gungy';
select * from ownerships;

delete from ownerships where username = 'bo';

insert into payments (username, car_id, amount_remaining, payment, payment_date) values 
	('gungy',5, 13800.00, 200, '20130415');
insert into payments (username, car_id, amount_remaining, payment, payment_date) values 
	('gungy',5, 13300.00, 500, '20130515');
insert into payments (username, car_id, amount_remaining, payment, payment_date) values 
	('gungy',5, 13000.00, 300, '20130614');
insert into payments (username, car_id, amount_remaining, payment, payment_date) values 
	('gungy',5, 0.00, 13000.00, '20130703');
select * from payments;

delete from payments where username = 'bo';


delete from cars where car_id = 3;
select * from cars;

--drops
drop table users;
drop table cars;
drop table offers;
drop table ownerships;
drop table payments;

--functions
create or replace reject_offers(a integer) returns integer as $$
	begin 
		
	end
$$ language plglsql;

drop function get_ownerships;

create or replace function get_ownerships(given_user text) returns setof ownerships
as $$
	select * from ownerships where username = given_user order by purchase_date desc;
$$ language sql;

select * from get_ownerships('gungy'); 
