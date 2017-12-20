# Springnote

## CRUD and functional operations.

| URL for api        | Methods     | Description          |
| ----------------------------------------------- |:-----------:| --------------------:|
| /users             | GET         | Get all users        |
| /user              | POST, PUT   | Create or update     |
| /user/{id}         | GET, DELETE | Get by id, or delete |
| ----------------------------------------------- | ----------- | -------------------- |
|                    |             |                      |
| /user/{id}         |             | Before use notebooks |
|                    |             |                      |
| /notebooks         | GET         | Get all notebooks    |
| /notebook          | POST, PUT   | Create or update     |
| /notebook/{id}     | GET, DELETE | Get by id, or delete |
| ----------------------------------------------- | ----------- | -------------------- |
|                    |             |                      |
| */notebook/{id}    |             | u/{id}/nb/{id}       |
|                    |             |                      |
| /notes             | GET         | All notes by NB & U  |
| /note              | POST, PUT   | Create or update     |
| /note/{id}         | GET, DELETE | Get by id, or delete |
| ----------------------------------------------- | ----------- | -------------------- |
|                    |             |                      |
| /user/{id}         |             | Marks by user        |
|                    |             |                      |
| /marks             | GET         | All marks by user    |
| /mark              | POST, PUT   | Create or update     |
| /mark/{id}         | GET, DELETE | Get by id, or delete |
|                                                     |          |                      |
| /mark/{id}/notebook/{id}/note/{id}/set    | GET     | Set mark on note      |
| /mark/{id}/notebook/{id}/note/{id}/remove | GET     | Remove mark from note |


