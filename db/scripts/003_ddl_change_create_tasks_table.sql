ALTER TABLE tasks
ADD COLUMN user_id int not null references todo_user(id);