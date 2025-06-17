create table if not exists purchase_order (
   order_id binary(16) primary key,
   customer_id int,
   product_id int,
   quantity int,
   unit_price int,
   amount int,
   status varchar(20),
   delivery_date timestamp
);

create table if not exists order_workflow_action (
   id binary(16) primary key,
   order_id binary(16),
   action varchar(100),
   created_at timestamp,
   foreign key (order_id) references purchase_order(order_id)
);