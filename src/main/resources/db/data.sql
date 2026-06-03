INSERT INTO employees (
    first_name,
    last_name,
    email,
    department,
    position,
    salary,
    hire_date,
    status
)
SELECT *
FROM (
    VALUES
    ('Alice', 'Johnson', 'alice.johnson@company.com', 'Engineering', 'Senior Software Engineer', 95000.00, DATE '2021-03-15', 'ACTIVE'),
    ('Bob', 'Williams', 'bob.williams@company.com', 'Engineering', 'Junior Developer', 62000.00, DATE '2023-06-01', 'ACTIVE'),
    ('Carol', 'Smith', 'carol.smith@company.com', 'HR', 'HR Manager', 78000.00, DATE '2020-01-10', 'ACTIVE'),
    ('David', 'Brown', 'david.brown@company.com', 'Finance', 'Financial Analyst', 72000.00, DATE '2022-09-20', 'ACTIVE'),
    ('Eva', 'Davis', 'eva.davis@company.com', 'Marketing', 'Marketing Lead', 85000.00, DATE '2019-11-05', 'ACTIVE'),
    ('Frank', 'Miller', 'frank.miller@company.com', 'Engineering', 'DevOps Engineer', 88000.00, DATE '2021-07-22', 'ON_LEAVE'),
    ('Grace', 'Wilson', 'grace.wilson@company.com', 'Design', 'UI/UX Designer', 80000.00, DATE '2022-02-14', 'ACTIVE'),
    ('Henry', 'Moore', 'henry.moore@company.com', 'Finance', 'CFO', 130000.00, DATE '2018-04-01', 'ACTIVE'),
    ('Iris', 'Taylor', 'iris.taylor@company.com', 'HR', 'Recruiter', 58000.00, DATE '2023-01-16', 'ACTIVE'),
    ('Jack', 'Anderson', 'jack.anderson@company.com', 'Engineering', 'Engineering Manager', 115000.00, DATE '2019-08-12', 'ACTIVE'),
    ('Karen', 'Thomas', 'karen.thomas@company.com', 'Marketing', 'Content Strategist', 68000.00, DATE '2022-05-30', 'INACTIVE'),
    ('Leo', 'Jackson', 'leo.jackson@company.com', 'Design', 'Graphic Designer', 63000.00, DATE '2023-03-07', 'ACTIVE')
) AS v(
    first_name,
    last_name,
    email,
    department,
    position,
    salary,
    hire_date,
    status
)
WHERE NOT EXISTS (
    SELECT 1 FROM employees LIMIT 1
);