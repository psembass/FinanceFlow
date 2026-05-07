-- Already in categories table
ALTER TABLE transactions
    DROP COLUMN type;

ALTER TABLE categories
    ADD CONSTRAINT unique_cat_name UNIQUE (name);

-- Seed data for categories
INSERT INTO categories (name, type)
VALUES ('Salary', 'INCOME'),
       ('Rent', 'EXPENSE'),
       ('Groceries', 'EXPENSE'),
       ('Transport', 'EXPENSE');