-- Q1
CREATE TABLE InsuranceCo
(
    name VARCHAR(255) PRIMARY KEY,
    phone INT
);

CREATE TABLE Vehicle
(
    licensePlate VARCHAR(255) PRIMARY KEY,
    year INT,
    maxLiability REAL,
    insuranceCoName VARCHAR(255),
    FOREIGN KEY (insuranceCoName) REFERENCES InsuranceCo(name)
);

CREATE TABLE Car
(
    licensePlate VARCHAR(255) PRIMARY KEY,
    make VARCHAR(255),
    FOREIGN KEY (licensePlate) REFERENCES Vehicle(licensePlate)
);

CREATE TABLE Truck
(
    licensePlate VARCHAR(255) PRIMARY KEY,
    capacity INT,
    FOREIGN KEY (licensePlate) REFERENCES Vehicle(licensePlate)
);

CREATE TABLE Person
(
    ssn INT PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE Driver
(
    ssn INT PRIMARY KEY,
    driverID INT,
    FOREIGN KEY (ssn) REFERENCES Person(ssn)
);

CREATE TABLE Drives
(
    driverSSN INT,
    vehicleLicensePlate VARCHAR(255),
    PRIMARY KEY (driverSSN, vehicleLicensePlate),
    FOREIGN KEY (driverSSN) REFERENCES Driver(ssn),
    FOREIGN KEY (vehicleLicensePlate) REFERENCES Vehicle(licensePlate)
);

CREATE TABLE NonProfessionalDriver
(
    driverID INT PRIMARY KEY,
    FOREIGN KEY (driverID) REFERENCES Driver(driverID)
);

CREATE TABLE ProfessionalDriver
(
    driverID INT PRIMARY KEY,
    medicalHistory VARCHAR(255),
    FOREIGN KEY (driverID) REFERENCES Driver(driverID)
);

-- Q2
-- The relation that represents insures in my schema is the foreign key `insuranceCoName` from Vehicle table
-- As it establishes relationship between insurance company and vehicles they insured

-- Q3
-- Drives represents a many-to-many relationship between drivers and vehicles
-- In simple word, a driver can drive multiple vehicles and each vechiles can be driven by multiple drivers
-- Operates represent the relationship between professional drivers and specifically trucks
-- In simple word, only professional drivers are allowed to operate trucks
-- In real life, "drives" reflect general driving, while "operates" would be qualified driving for specific vehicles