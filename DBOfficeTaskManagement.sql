CREATE TABLE EmployeeList (
    employee_id INT PRIMARY KEY,
    name VARCHAR(100),
    position VARCHAR(50),
	address varchar(100),
    email VARCHAR(100),
    phone VARCHAR(20)
);

CREATE TABLE ManagerList (
    manager_id INT PRIMARY KEY,
    employee_id INT REFERENCES EmployeeList(employee_id)
);

CREATE TABLE TaskList (
    task_id INT PRIMARY KEY,
    task_name VARCHAR(100),
    status VARCHAR(20),
    assigned_to INT REFERENCES TeamList(team_id),
    assigned_by INT REFERENCES ManagerList(manager_id),
    deadline DATE
);

CREATE TABLE TeamList (
    team_id INT PRIMARY KEY,
    team_name VARCHAR(100),
    manager_id INT REFERENCES ManagerList(manager_id)
);

CREATE TABLE TeamMemberList (
    team_id INT REFERENCES TeamList(team_id),
    employee_id INT REFERENCES EmployeeList(employee_id),
    PRIMARY KEY (team_id, employee_id)
);

select * from EmployeeList;
select * from ManagerList;
select * from TaskList;
select * from TeamList;
select * from TeamMemberList;
