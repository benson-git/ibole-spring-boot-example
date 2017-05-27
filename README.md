# ibole-spring-boot-example

Spring Boot启动类包的问题，启动类必须外置；因为Spring Boot默认扫描的是启动类包、以及包内子目录。

MongoDB Auth:


1）不带--auth参数启动数据库，所以不需要帐号即可连上MongoDB。

2）新建一个角色，比如叫 sysadmin，需要先切换到admin库进行如下操作：

> use admin
> db.createRole({role:'sysadmin',roles:[],
privileges:[
{resource:{anyResource:true},actions:['anyAction']}
]})

3）然后，新建一个用户，使用这个角色，注意，这个角色的db是admin，操作如下：

> use test
> db.createUser({
user:'test',
pwd:'test123',
roles:[
{role:'sysadmin',db:'admin'
]})


好了现在重启启动数据库带上 --auth 就可以正常执行了.