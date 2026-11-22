create database Railway;
use railway;

CREATE TABLE Railway.admin (
    admin_id INT PRIMARY KEY AUTO_INCREMENT,
    admin_name VARCHAR(100) NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    email VARCHAR(100) UNIQUE,
    phone_number VARCHAR(20),
    account_status VARCHAR(20) DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


INSERT INTO admin
(admin_name, username, password_hash, email, phone_number)
VALUES
('Zeeshan', 'admin123', 'train2004', 
 'admin@railway.com', '03001234567');


create table user
( 
user_id int primary key,
user_name varchar(50),
phone_number varchar(50),
CNIC varchar(50) unique

);

CREATE TABLE ticket (
    ticket_id INT PRIMARY KEY AUTO_INCREMENT,
    booking_date DATE NOT NULL,
    status BOOLEAN DEFAULT TRUE,
    ticket_price DECIMAL(10,2) NOT NULL,
    schedule_id INT NOT NULL,
    seat_id INT NOT NULL,
    user_id INT NOT NULL,

    FOREIGN KEY (user_id) REFERENCES user(user_id),
    FOREIGN KEY (schedule_id) REFERENCES schedule(schedule_id),
    FOREIGN KEY (seat_id) REFERENCES seat(seat_id),

    UNIQUE (schedule_id, seat_id)
);






create table train
(
 train_id int primary key,
 train_name varchar(50),
 train_type varchar(50)

); 

create table seat
(
	seat_id int primary key auto_increment,
    seat_no int not null,
    class varchar(50) not null,
    train_id int not null,
    
    foreign key (train_id) references train(train_id),
    UNIQUE (train_id, seat_no)
);


INSERT INTO route (route_name)
VALUES
('Sukkur to Multan'),
('Multan to Sukkur');



CREATE TABLE station (
    station_id INT PRIMARY KEY AUTO_INCREMENT,
    station_name VARCHAR(100) NOT NULL,
    city VARCHAR(100) NOT NULL
);


CREATE TABLE Junction (
    route_id INT NOT NULL,
    station_id INT NOT NULL,
    station_order INT NOT NULL,

    PRIMARY KEY (route_id, station_id),

    UNIQUE (route_id, station_order),

    FOREIGN KEY (route_id)
        REFERENCES route(route_id),

    FOREIGN KEY (station_id)
        REFERENCES station(station_id)
);

create table schedule
(
 schedule_id int primary key auto_increment,
 departure_time time not null,
 arrival_time time not null,
 travel_date date not null,
   train_id int not null,
   route_id int not null,

   foreign key(train_id) references train(train_id),
   foreign key(route_id) references route(route_id)
  

);

CREATE TABLE route (
    route_id INT PRIMARY KEY AUTO_INCREMENT,
    route_name VARCHAR(100) NOT NULL UNIQUE
);


INSERT INTO admin
(admin_name, username, password_hash, email, phone_number)
VALUES
('Zeeshan', 'admin123', 'train2004',
 'admin@railway.com', '03001234567');
 
 INSERT INTO train (train_id, train_name, train_type)
VALUES
(101, 'Green Line Express', 'Express'),
(102, 'Karachi Express', 'Express'),
(103, 'Tezgam Express', 'Express'),
(104, 'Awam Express', 'Passenger'),
(105, 'Khyber Mail', 'Express');

INSERT INTO route (route_name)
VALUES
('Sukkur to Multan'),
('Multan to Sukkur'),
('Karachi to Lahore'),
('Lahore to Karachi'),
('Karachi to Hyderabad'),
('Hyderabad to Karachi'),
('Sukkur to Karachi'),
('Karachi to Sukkur');



INSERT INTO station (station_name, city)
VALUES
('Sukkur Railway Station', 'Sukkur'),
('Rohri Railway Station', 'Rohri'),
('Rahim Yar Khan Railway Station', 'Rahim Yar Khan'),
('Bahawalpur Railway Station', 'Bahawalpur'),
('Multan Cantt', 'Multan'),
('Lahore Junction', 'Lahore'),
('Karachi Cantt', 'Karachi'),
('Hyderabad Junction', 'Hyderabad'),
('Nawabshah Railway Station', 'Nawabshah'),
('Khanewal Junction', 'Khanewal');

-- Sukkur to Multan (Route ID 1)
INSERT INTO Junction (route_id, station_id, station_order)
VALUES
(1, 1, 1),
(1, 2, 2),
(1, 3, 3),
(1, 4, 4),
(1, 5, 5);

-- Multan to Sukkur (Route ID 2)
INSERT INTO Junction (route_id, station_id, station_order)
VALUES
(2, 5, 1),
(2, 4, 2),
(2, 3, 3),
(2, 2, 4),
(2, 1, 5);

-- Karachi to Lahore (Route ID 3)
INSERT INTO Junction (route_id, station_id, station_order)
VALUES
(3, 7, 1),
(3, 8, 2),
(3, 9, 3),
(3, 10, 4),
(3, 6, 5);

-- Lahore to Karachi (Route ID 4)
INSERT INTO Junction (route_id, station_id, station_order)
VALUES
(4, 6, 1),
(4, 10, 2),
(4, 9, 3),
(4, 8, 4),
(4, 7, 5);

INSERT INTO schedule
(departure_time, arrival_time, travel_date, train_id, route_id)
VALUES
('08:00:00', '15:00:00', '2026-12-01', 101, 1),
('09:00:00', '16:00:00', '2026-12-01', 102, 3),
('10:00:00', '17:00:00', '2026-12-02', 103, 2),
('12:00:00', '19:00:00', '2026-12-02', 104, 4),
('14:00:00', '21:00:00', '2026-12-03', 105, 1),
('06:00:00', '13:00:00', '2026-12-03', 101, 2),
('16:00:00', '23:00:00', '2026-12-04', 102, 4),
('18:00:00', '23:00:00', '2026-12-04', 103, 3);


INSERT INTO seat (seat_no, class, train_id)
VALUES
(1, 'Economy', 101),
(2, 'Economy', 101),
(3, 'Economy', 101),
(4, 'Business', 101),
(5, 'Business', 101),

(1, 'Economy', 102),
(2, 'Economy', 102),
(3, 'Economy', 102),
(4, 'Business', 102),
(5, 'Business', 102),

(1, 'Economy', 103),
(2, 'Economy', 103),
(3, 'Economy', 103),
(4, 'Business', 103),
(5, 'Business', 103),

(1, 'Economy', 104),
(2, 'Economy', 104),
(3, 'Economy', 104),
(4, 'Business', 104),
(5, 'Business', 104),

(1, 'Economy', 105),
(2, 'Economy', 105),
(3, 'Economy', 105),
(4, 'Business', 105),
(5, 'Business', 105);

INSERT INTO user
(user_id, user_name, phone_number, CNIC)
VALUES
(1, 'Ali Ahmed', '03001111111', '12345-1234567-1'),
(2, 'Ahmed Khan', '03002222222', '12345-1234567-2'),
(3, 'Usman Ali', '03003333333', '12345-1234567-3'),
(4, 'Bilal Hussain', '03004444444', '12345-1234567-4'),
(5, 'Hassan Raza', '03005555555', '12345-1234567-5');

INSERT INTO ticket
(booking_date, status, ticket_price, schedule_id, seat_id, user_id)
VALUES
('2026-11-21', TRUE, 2500.00, 1, 1, 1),
('2026-11-21', TRUE, 3000.00, 2, 6, 2),
('2026-11-21', TRUE, 2000.00, 3, 11, 3),
('2026-11-21', TRUE, 3500.00, 4, 16, 4),
('2026-11-21', FALSE, 2500.00, 5, 21, 5);




select * from seat;
INSERT INTO seat (seat_no, class, train_id)
VALUES
-- Train 101
(1, 'Economy', 101),
(2, 'Economy', 101),
(3, 'Economy', 101),
(4, 'Business', 101),
(5, 'Business', 101),

-- Train 102
(1, 'Economy', 102),
(2, 'Economy', 102),
(3, 'Economy', 102),
(4, 'Business', 102),
(5, 'Business', 102),

-- Train 103
(1, 'Economy', 103),
(2, 'Economy', 103),
(3, 'Economy', 103),
(4, 'Business', 103),
(5, 'Business', 103),

-- Train 104
(1, 'Economy', 104),
(2, 'Economy', 104),
(3, 'Economy', 104),
(4, 'Business', 104),
(5, 'Business', 104),

-- Train 105
(1, 'Economy', 105),
(2, 'Economy', 105),
(3, 'Economy', 105),
(4, 'Business', 105),
(5, 'Business', 105);
USE Railway;

INSERT INTO ticket
(booking_date, status, ticket_price, schedule_id, seat_id, user_id)
VALUES
('2026-11-21', TRUE, 2500.00, 1, 1, 1),
('2026-11-21', TRUE, 3000.00, 2, 6, 2),
('2026-11-21', TRUE, 2000.00, 3, 11, 3),
('2026-11-21', TRUE, 3500.00, 4, 16, 4),
('2026-11-21', FALSE, 2500.00, 5, 21, 5);

select * from seat;
