create table car
(
    id          bigint auto_increment
        primary key,
    vin         varchar(25)          not null,
    maker       varchar(25)          not null,
    model       varchar(25)          not null,
    model_year  int                  not null,
    price       decimal(15, 2)       not null,
    showroom_id bigint               not null,
    deleted     tinyint(1) default 0 not null,
    constraint vin
        unique (vin),
    constraint car_showroom_id_fk
        foreign key (showroom_id) references showroom (id)
            on delete cascade
);

create table role
(
    id   bigint auto_increment
        primary key,
    name varchar(50) not null,
    constraint name
        unique (name)
);

create table showroom
(
    id                             bigint auto_increment
        primary key,
    name                           varchar(100)         not null,
    commercial_registration_number bigint               not null,
    manager_name                   varchar(100)         null,
    contact_number                 bigint               not null,
    address                        varchar(255)         null,
    deleted                        tinyint(1) default 0 not null,
    constraint commercial_registration_number
        unique (commercial_registration_number),
    constraint commercial_registration_number_2
        unique (commercial_registration_number)
);

create table user
(
    id       bigint auto_increment
        primary key,
    username varchar(50)          not null,
    password varchar(100)         not null,
    email    varchar(100)         not null,
    enabled  tinyint(1) default 1 null,
    role_id  bigint               null,
    constraint email
        unique (email),
    constraint username
        unique (username),
    constraint user_ibfk_1
        foreign key (role_id) references role (id)
);

create index role_id
    on user (role_id);

create table user_roles
(
    user_id bigint not null,
    role_id bigint not null,
    primary key (user_id, role_id),
    constraint fk_user_id
        foreign key (user_id) references user (id),
    constraint fk_role_id
        foreign key (role_id) references role (id)
);


