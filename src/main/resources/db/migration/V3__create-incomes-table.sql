CREATE TABLE incomes (
                        income_id        UUID           PRIMARY KEY,
                        name             VARCHAR(120)   NOT NULL,
                        money            NUMERIC(10, 2) NOT NULL,
                        transaction_date DATE           NOT NULL,
                        user_id          UUID           NOT NULL,
                        category         VARCHAR(50)    NOT NULL,

                        CONSTRAINT fk_income_user
                            FOREIGN KEY (user_id) REFERENCES users (user_id)
                                ON DELETE CASCADE
);