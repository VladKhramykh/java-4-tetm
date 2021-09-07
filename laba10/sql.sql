create database java_labs;
use java_labs;

create table users
(
    id int primary key identity(1,1),
    username nvarchar(50),
    email nvarchar(50)
);

CREATE PROCEDURE [getUserById]
    @username nvarchar(50) output,
    @id integer
AS
BEGIN
    SET NOCOUNT ON;

    SELECT top(1) @username = username FROM users
    WHERE id = @id;
END