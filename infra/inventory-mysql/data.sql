create table if not exists product (
   id int auto_increment primary key,
   description varchar(50),
   available_quantity int
);

create table if not exists order_inventory (
   inventory_id binary(16) primary key,
   order_id binary(16),
   product_id int,
   status varchar(50),
   quantity int,
   foreign key (product_id) references product(id)
);

--insert into product(description, available_quantity)
--    values
--        ('book', 10),
--        ('pen', 10),
--        ('rug', 10);