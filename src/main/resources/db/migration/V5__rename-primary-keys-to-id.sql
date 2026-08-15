ALTER TABLE users    RENAME COLUMN user_id    TO id;
ALTER TABLE expenses RENAME COLUMN expense_id TO id;
ALTER TABLE incomes  RENAME COLUMN income_id  TO id;

ALTER TABLE incomes RENAME CONSTRAINT fk_income_user TO fk_incomes_user;