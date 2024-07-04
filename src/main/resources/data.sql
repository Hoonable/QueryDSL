DROP TABLE IF EXISTS `post`;
DROP TABLE IF EXISTS `comment`;
DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS `post_like`;
DROP TABLE IF EXISTS `comment_like`;

create table post(
                           id bigint auto_increment primary key ,
                           user_id bigint not null,
                           content varchar(255),
                           created_at datetime not null default CURRENT_TIMESTAMP,
                           modified_at datetime not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP
);

create table comment(
                     id bigint auto_increment primary key ,
                     user_id bigint not null,
                     post_id bigint not null,
                     content varchar(255),
                     created_at datetime not null default CURRENT_TIMESTAMP,
                     modified_at datetime not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP
);

create table user(
                        id bigint auto_increment primary key ,
                        user_id varchar(255) not null,
                        created_at datetime not null default CURRENT_TIMESTAMP,
                        modified_at datetime not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
                        email varchar(255) not null,
                        introduction varchar(255),
                        name varchar(255),
                        password varchar(255) not null,
                        pictureurl varchar(255),
                        refresh_token varchar(255),
                        status enum('DELETED', 'UNVERIFIED', 'VERIFIED')
);

create table post_like(
                          id bigint auto_increment primary key ,
                          user_id bigint not null,
                          post_id bigint not null
);

create table comment_like(
                             id bigint auto_increment primary key ,
                             user_id bigint not null,
                             comment_id bigint not null
);



insert into user(email, password, refresh_token, status, user_id) values ('abc@gmail.com', '$2a$10$NlRQVxRfc/bdaRnznu3rJuXGi3m021kQbimWALl5atb26H7zqIirG', 'Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJVU0VSMTIzNDU2Nzg5MCIsImV4cCI6MTcyMTIwODg0OCwiaWF0IjoxNzE5OTk5MjQ4fQ.MCNky5eJhMstKqXk3qikHVYYUu-g5FJYjtDqDeV_VIc', 'UNVERIFIED','USER1234567890');
insert into user(email, password, refresh_token, status, user_id) values ('abcd@gmail.com', '$2a$10$NlRQVxRfc/bdaRnznu3rJuXGi3m021kQbimWALl5atb26H7zqIirG', 'Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJVU0VSMTIzNDU2Nzg5MCIsImV4cCI6MTcyMTIwODg0OCwiaWF0IjoxNzE5OTk5MjQ4fQ.MCNky5eJhMstKqXk3qikHVYYUu-g5FJYjtDqDeV_VIc', 'UNVERIFIED','TESTUSER123456');

insert into post(content, user_id)  values ('aaa',1);
insert into post(content, user_id, created_at)  values ('aaa',2, '2024-07-01 21:22:22');
insert into post(content, user_id)  values ('bbb',2);
insert into post(content, user_id, created_at)  values ('ccc',2, '2024-07-03 21:22:22');
insert into post(content, user_id, created_at)  values ('ddd',2, '2024-07-02 21:22:22');
insert into post(content, user_id)  values ('eee',2);

insert into comment(content, user_id, post_id) values ('abc', 1, 2);
insert into comment(content, user_id, post_id) values ('abc', 2, 2);