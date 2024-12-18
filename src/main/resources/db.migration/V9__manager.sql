CREATE TABLE manager(
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    username VARCHAR(10) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    date_of_birth TIMESTAMP NOT NULL,
    created_at NOT NULL DEFAULT CURRENT_TIMESTAMP,
    designation VARCHAR(255) NOT NULL
);