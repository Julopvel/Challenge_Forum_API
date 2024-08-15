create table topics(

	id bigint not null auto_increment,
	title varchar(200) not null unique,
	message varchar(1000) not null,
	creation_date datetime not null,
	topic_status varchar(30) not null,
	author varchar(100) not null,
	course varchar(100) not null,
	active boolean not null,

	primary key(id)

);