# DROP TABLE IF EXISTS `post`;
# DROP TABLE IF EXISTS `comment`;
# DROP TABLE IF EXISTS `user`;

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



insert into user(email, password, refresh_token, status, user_id) values ('abc@gmail.com', '$2a$10$NlRQVxRfc/bdaRnznu3rJuXGi3m021kQbimWALl5atb26H7zqIirG', 'Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJVU0VSMTIzNDU2Nzg5MCIsImV4cCI6MTcyMTIwODg0OCwiaWF0IjoxNzE5OTk5MjQ4fQ.MCNky5eJhMstKqXk3qikHVYYUu-g5FJYjtDqDeV_VIc', 'UNVERIFIED','USER1234567890');
insert into post(content, user_id)  values ('aaa',1);
insert into post(content, user_id)  values ('aaa',1);
insert into comment(content, user_id, post_id) values ('abc', 1, 2)