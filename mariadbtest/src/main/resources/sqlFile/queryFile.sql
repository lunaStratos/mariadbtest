create table mariadb_account(
uid varchar(20) primary key,
password varchar(20),
name varchar(20)
);

select uid, password, name from mariadb_account;

insert into mariadb_account values('orion3','orion3','한글');

show variables like 'c%';

delete from mariadb_account;

drop table mariadb_account;