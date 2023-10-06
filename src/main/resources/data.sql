-- Users Table
CREATE TABLE user_account (
                              user_account_id IDENTITY PRIMARY KEY,
                              first_name VARCHAR(255) NOT NULL,
                              last_name VARCHAR(255) NOT NULL,
                              email VARCHAR(255) UNIQUE NOT NULL,
                              password VARCHAR(255) NOT NULL
);

-- Boats Table
CREATE TABLE boat (
                      boat_id IDENTITY PRIMARY KEY,
                      name VARCHAR(255) NOT NULL,
                      description TEXT,
                      user_account_id INT,
                      FOREIGN KEY (user_account_id) REFERENCES user_account(user_account_id)
);
