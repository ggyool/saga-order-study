create table if not exists shipment (
   id binary(16) primary key,
   order_id binary(16),
   product_id int,
   customer_id int,
   quantity int,
   status varchar(50),
   delivery_date timestamp
);
