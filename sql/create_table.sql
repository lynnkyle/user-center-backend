-- auto-generated definition
create table user
(
    id          bigint auto_increment comment '用户id'
        primary key,
    name        varchar(256)                       null comment '用户昵称',
    account     varchar(256)                       null comment '用户账号',
    password    varchar(512)                       null comment '用户密码',
    avatar_url  varchar(1024)                      null comment '用户头像',
    gender      tinyint                            null comment '用户性别',
    phone       varchar(128)                       null comment '手机号码',
    email       varchar(512)                       null comment '用户邮箱',
    role        tinyint  default 0                 null comment '用户角色(0-普通用户,1-管理员)',
    status      tinyint  default 0                 null comment '用户状态(0-正常)',
    code        varchar(256)                       null comment '校验编码',
    is_delete   tinyint  default 0                 null comment '逻辑删除(1-删除)',
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '用户表' engine = InnoDB;

