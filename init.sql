CREATE TABLE IF NOT EXISTS `t_config` (
	`project` VARCHAR (60),
	`prop_key` VARCHAR (120),
	`prop_value` VARCHAR (300),
	`flag` TINYINT (4),
	`modify_time` DATETIME 
);
INSERT INTO `t_config` (`project`, `prop_key`, `prop_value`, `flag`, `modify_time`) VALUES
('schedule','zookeeper.address','localhost:2181','1','2017-04-13 21:59:36'),
('schedule.master','master.port','5566','1','2017-04-13 21:59:36'),
('schedule.master','master.swagger.address','localhost:5566','1','2017-04-13 21:59:36'),
('schedule.master','master.swagger.basePath','/master-rpc/api','1','2017-04-13 21:59:36'),
('schedule.master','schedule.node.timeout','20000','1','2017-04-13 21:59:36'),
('schedule.slave','schedule.heartbeat.interval','10000','1','2017-04-13 21:59:36'),
('schedule.slave','slave.swagger.address','localhost:5577','1','2017-04-13 21:59:36'),
('schedule.slave','slave.swagger.basePath','/slave-rpc/api','1','2017-04-13 21:59:36');
