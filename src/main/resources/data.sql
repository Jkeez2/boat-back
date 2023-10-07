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

-- Insert test values
INSERT INTO user_account VALUES (1, 'Cebrail', 'Yalcin', 'test@123.com', 'test');
INSERT INTO boat VALUES (1, 'Fast Boat', 'Lorem ipsum dolor sit amet, ' ||
                                         'consectetur adipiscing elit. Etiam ' ||
                                         'ac mauris sit amet mi cursus aliquet ' ||
                                         'sed et nulla. Proin consequat sagittis ipsum. ' ||
                                         'Curabitur maximus magna leo, non pellentesque nisl ' ||
                                         'fringilla non. Praesent luctus dapibus velit. Vivamus in ' ||
                                         'felis sit amet sapien scelerisque pulvinar quis viverra orci. ' ||
                                         'Vivamus eget tincidunt mauris. Nunc pellentesque vehicula justo volutpat ' ||
                                         'viverra. Vivamus at congue turpis, ut pretium odio. Donec commodo quis ' ||
                                         'libero vel lacinia. Sed vestibulum eros a sapien fringilla eleifend at ' ||
                                         'non orci. Nullam massa enim, tristique nec massa ac, sagittis elementum ' ||
                                         'tortor. Curabitur tincidunt congue tortor quis ullamcorper. '
                        , 1);
INSERT INTO boat VALUES (2, 'Normal boat', 'short description', 1);
