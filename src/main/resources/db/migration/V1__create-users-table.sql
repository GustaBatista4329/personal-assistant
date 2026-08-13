CREATE TABLE users (
                       user_id      UUID         PRIMARY KEY,
                       name         VARCHAR(120) NOT NULL,
                       birthdate    DATE,
                       phone_number VARCHAR(11),
                       email        VARCHAR(255) NOT NULL,
                       password     VARCHAR(255) NOT NULL,

                       CONSTRAINT uk_users_email UNIQUE (email)
);