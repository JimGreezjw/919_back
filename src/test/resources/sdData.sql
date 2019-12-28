create table owners
(
    id         int(4) unsigned auto_increment
        primary key,
    first_name varchar(30)  null,
    last_name  varchar(30)  null,
    address    varchar(255) null,
    city       varchar(80)  null,
    telephone  varchar(20)  null
);

create index last_name
    on owners (last_name);

create table specialties
(
    id   int(4) unsigned auto_increment
        primary key,
    name varchar(80) null
);

create index name
    on specialties (name);

create table types
(
    id   int(4) unsigned auto_increment
        primary key,
    name varchar(80) null
);

create index name
    on types (name);

create table user
(
    id            int auto_increment
        primary key,
    username      varchar(255) not null,
    password      varchar(255) not null,
    nick_name     varchar(255) null,
    sex           int(1)       null,
    register_date datetime     not null,
    constraint user_nick_name_uindex
        unique (nick_name),
    constraint user_username_uindex
        unique (username)
);

create table bills
(
    id          int(4) unsigned auto_increment
        primary key,
    name        varchar(30)     null,
    create_date datetime        null,
    type_id     int(4) unsigned not null,
    user_id     int             not null,
    constraint bills_ibfk_2
        foreign key (type_id) references types (id),
    constraint bills_user_id_fk
        foreign key (user_id) references user (id)
);

create index name
    on bills (name);

create index type_id
    on bills (type_id);

create table vets
(
    id         int(4) unsigned auto_increment
        primary key,
    first_name varchar(30) null,
    last_name  varchar(30) null
);

create table vet_specialties
(
    vet_id       int(4) unsigned not null,
    specialty_id int(4) unsigned not null,
    constraint vet_id
        unique (vet_id, specialty_id),
    constraint vet_specialties_ibfk_1
        foreign key (vet_id) references vets (id),
    constraint vet_specialties_ibfk_2
        foreign key (specialty_id) references specialties (id)
);

create index specialty_id
    on vet_specialties (specialty_id);

create index last_name
    on vets (last_name);

create table visits
(
    id          int(4) unsigned auto_increment
        primary key,
    bill_id     int(4) unsigned not null,
    visit_date  date            null,
    user_id     int             not null,
    description varchar(255)    null,
    constraint visits_user_id_uindex
        unique (user_id),
    constraint visits_ibfk_1
        foreign key (bill_id) references bills (id),
    constraint visits_user_id_fk
        foreign key (user_id) references user (id)
);

create index pet_id
    on visits (bill_id);

