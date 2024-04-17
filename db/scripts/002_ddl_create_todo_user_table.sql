CREATE TABLE todo_user (
   id SERIAL PRIMARY KEY,
   name varchar not null,
   login varchar unique not null,
   password varchar not null
);