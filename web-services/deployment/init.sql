CREATE SEQUENCE employee_id_seq
    START WITH 1
    INCREMENT BY 1;

CREATE TABLE employee (
    id INT NOT NULL DEFAULT NEXTVAL('employee_id_seq'),
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    hire_date DATE,
    department VARCHAR(50),
    PRIMARY KEY (id)
);

INSERT INTO employee (first_name, last_name, email, hire_date, department)
VALUES ('John', 'Doe', 'john.doe@example.com', '2022-01-15', 'Human Resources');
INSERT INTO employee (first_name, last_name, email, hire_date, department)
VALUES ('Jane', 'Smith', 'jane.smith@example.com', '2023-02-20', 'Finance');
INSERT INTO employee (first_name, last_name, email, hire_date, department)
VALUES ('Emily', 'Jones', 'emily.jones@example.com', '2021-03-18', 'Marketing');
INSERT INTO employee (first_name, last_name, email, hire_date, department)
VALUES ('Michael', 'Brown', 'michael.brown@example.com', '2020-04-25', 'Sales');
INSERT INTO employee (first_name, last_name, email, hire_date, department)
VALUES ('Emma', 'Taylor', 'emma.taylor@example.com', '2022-05-10', 'IT');
INSERT INTO employee (first_name, last_name, email, hire_date, department)
VALUES ('Oliver', 'Williams', 'oliver.williams@example.com', '2021-06-30', 'Human Resources');
INSERT INTO employee (first_name, last_name, email, hire_date, department)
VALUES ('Sophia', 'Davis', 'sophia.davis@example.com', '2023-07-11', 'Finance');
INSERT INTO employee (first_name, last_name, email, hire_date, department)
VALUES ('James', 'Wilson', 'james.wilson@example.com', '2019-08-19', 'Marketing');
INSERT INTO employee (first_name, last_name, email, hire_date, department)
VALUES ('Ava', 'Martinez', 'ava.martinez@example.com', '2020-09-24', 'Sales');
INSERT INTO employee (first_name, last_name, email, hire_date, department)
VALUES ('Liam', 'Anderson', 'liam.anderson@example.com', '2022-10-15', 'IT');
INSERT INTO employee (first_name, last_name, email, hire_date, department)
VALUES ('Charlotte', 'Thomas', 'charlotte.thomas@example.com', '2023-11-05', 'Human Resources');
INSERT INTO employee (first_name, last_name, email, hire_date, department)
VALUES ('Elijah', 'Jackson', 'elijah.jackson@example.com', '2021-12-22', 'Finance');
INSERT INTO employee (first_name, last_name, email, hire_date, department)
VALUES ('Amelia', 'White', 'amelia.white@example.com', '2020-01-09', 'Marketing');
INSERT INTO employee (first_name, last_name, email, hire_date, department)
VALUES ('Noah', 'Harris', 'noah.harris@example.com', '2022-02-28', 'Sales');
INSERT INTO employee (first_name, last_name, email, hire_date, department)
VALUES ('Mia', 'Martin', 'mia.martin@example.com', '2023-03-14', 'IT');
INSERT INTO employee (first_name, last_name, email, hire_date, department)
VALUES ('Lucas', 'Lee', 'lucas.lee@example.com', '2019-04-07', 'Human Resources');
INSERT INTO employee (first_name, last_name, email, hire_date, department)
VALUES ('Isabella', 'Perez', 'isabella.perez@example.com', '2021-05-16', 'Finance');
INSERT INTO employee (first_name, last_name, email, hire_date, department)
VALUES ('Mason', 'Thompson', 'mason.thompson@example.com', '2022-06-23', 'Marketing');
INSERT INTO employee (first_name, last_name, email, hire_date, department)
VALUES ('Evelyn', 'Garcia', 'evelyn.garcia@example.com', '2023-07-18', 'Sales');
INSERT INTO employee (first_name, last_name, email, hire_date, department)
VALUES ('Aiden', 'Martinez', 'aiden.martinez@example.com', '2020-08-29', 'IT');