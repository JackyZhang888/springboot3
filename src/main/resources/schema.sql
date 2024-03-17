-- 测试表初始化
drop database if exists mydatabase;
create database if not exists mydatabase character set utf8;
use mydatabase;
drop table if exists user;
drop table if exists task;

-- 用户表
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(32) NOT NULL COMMENT '用户名',
  `password` varchar(64) NOT NULL COMMENT '密码',
  `role` varchar(32) NOT NULL COMMENT '角色名称',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';


-- 任务表
CREATE TABLE `task` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `taskId` varchar(64) NOT NULL DEFAULT 'null' COMMENT '任务ID',
  `taskType` varchar(32) NOT NULL  DEFAULT 'null' COMMENT '任务类型',
  `createTime` varchar(32) NOT NULL  DEFAULT 'null' COMMENT '创建时间',
  `updateTime` varchar(32) NOT NULL  DEFAULT 'null' COMMENT '更新时间',
  `progress` varchar(32) NOT NULL  DEFAULT 'null' COMMENT '任务进度',
  `status` varchar(32) NOT NULL  DEFAULT 'null' COMMENT '任务结果',
  PRIMARY KEY (`id`),
  UNIQUE KEY `taskId` (`taskId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='任务表';

-- 初始化测试数据
INSERT INTO user VALUES(1,'admin','admin', 'admin');
INSERT INTO user VALUES(2,'user','user', 'user');
