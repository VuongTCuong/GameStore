create database GameStore;
use GameStore;
create table user (
	userID integer not null auto_increment,
    fullName varchar(30),
    account varchar(20),
    password varchar(255),
    email varchar(40),
    role varchar(20),
    state varchar(10),
    primary key (userID)
);

create table game (
	gameID integer not null auto_increment,
    gameName varchar(50),
    poster varchar(255),
    genre varchar(50),
    gameDescription text,
    platform varchar(20),
    publisher varchar(30),
    releaseDate date,
    stockQuantity integer,
    price float,
    primary key (gameID)
);

create table gamekey(
	keyID integer not null auto_increment,
	keyValue varchar(255),
    status varchar(30),
    gameID integer,
    primary key (keyID),
    foreign key (gameID) references game(gameID) on delete cascade
);

create table review(
	reviewID integer not null auto_increment,
    reviewContent text,
    reviewDate date,
    userID integer,
    gameID integer,
    primary key (reviewID),
    foreign key (userID) references user(userID), 
    foreign key (gameID) references game(gameID) on delete cascade
);

create table cart(
	cartID integer not null auto_increment,
    updateDate date,
    userID integer,
    primary key (cartID), 
    foreign key (userID) references user(userID) on delete cascade
);

create table cartdetail(
	cartID integer not null,
    gameID integer not null,
    priceAtPurchase float,
    quantity integer,
    
    primary key (cartID, gameID),
    foreign key (cartID) references cart(cartID) on delete cascade,
    foreign key (gameID) references game(gameID) on delete cascade
);

create table orders(
	orderID integer not null auto_increment,
    totalAmount float,
    orderDate date,
    deliveryDate date,
    state varchar(20),
    userID integer,
    primary key (orderID),
    foreign key (userID) references user(userID)
);

create table orderdetail(
	orderID integer not null,
    gameID integer not null,
    priceAtPurchase float,
    quantity integer,
    primary key (orderID,gameID),
    foreign key (orderID) references orders(orderID) on delete cascade,
    foreign key (gameID) references game(gameID)
)