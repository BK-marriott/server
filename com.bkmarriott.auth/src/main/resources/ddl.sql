create table m_user
(
    id         bigint      not null auto_increment,
    email      varchar(45) not null,
    name       varchar(45)  not null,
    password   varchar(255) not null,
    role       enum ('CUSTOMER','MANAGER','MASTER','STAFF') not null,

    is_deleted bit         not null,
    created_at datetime(6) not null,
    created_by bigint      not null,
    updated_at datetime(6) not null,
    updated_by bigint      not null,
    deleted_at datetime(6),
    deleted_by bigint,

    primary key (id)
) engine = InnoDB;
alter table m_user add constraint UK_user_email unique (email)