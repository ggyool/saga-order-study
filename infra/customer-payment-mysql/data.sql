create table if not exists customer (
   id int auto_increment primary key,
   name varchar(50) NOT NULL,
   balance int
);

create table if not exists customer_payment (
   payment_id binary(16) primary key,
   order_id binary(16),
   customer_id int,
   status varchar(50),
   amount int,
   foreign key (customer_id) references customer(id)
);

--insert into customer(name, balance)
--    values
--        ('sam', 100),
--        ('mike', 100),
--        ('john', 100);