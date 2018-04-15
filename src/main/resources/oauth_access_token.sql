-- auto-generated definition
create table oauth_access_token
(
  token_id          varchar(255),
  token             bytea,
  authentication_id varchar(255) not null
    constraint oauth_access_token_pkey
    primary key,
  user_name         varchar(255),
  client_id         varchar(255),
  authentication    bytea,
  refresh_token     varchar(255)
);