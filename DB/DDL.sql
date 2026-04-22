CREATE DATABASE IF NOT EXISTS voting_db;
USE voting_db;

CREATE TABLE Vote_Item (
    item_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    item_name VARCHAR(100) NOT NULL,
    item_created_time TIMESTAMP NOT NULL DEFAULT NOW(),
    item_updated_time TIMESTAMP NOT NULL DEFAULT NOW() ON UPDATE NOW(),
    is_enable BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE Voter_Info (
    voter_id CHAR(36) NOT NULL PRIMARY KEY,
    voter_name VARCHAR(100) NOT NULL
);

CREATE TABLE Vote_Record (
    voter_id CHAR(36) NOT NULL,
    item_id INT NOT NULL,
    voted_at TIMESTAMP NOT NULL DEFAULT NOW(),
    FOREIGN KEY (voter_id) REFERENCES Voter_Info(voter_id),
    FOREIGN KEY (item_id) REFERENCES Vote_Item(item_id),
    PRIMARY KEY (voter_id, item_id)
);
