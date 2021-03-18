# netty-rpc
## 简介

netty 实现的 rpc 项目，用于netty 框架的学习。

## 模块介绍

netty-rpc-consumer：客户端，消费者。

netty-rpc-core: 核心包，被客户端和服务端依赖。

netty-rpc-facade: 接口，被客户端和服务端依赖。

netty-rpc-server: 服务端，生产者。

netty-rpc-proxy: Java代理功能学习模块。

## 项目启动

1. 服务端直接运行 netty-rpc-server 根目录下的 ServerApplication 启动类。

![](.\images\0.png)

1. 客户端直接运行 netty-rpc-consumer 根目录下的 ConsumerApplication 启动类。

![](.\images\1.png)

## 接口调用

客户端和服务器都启动成功后，可以在浏览器中访问Swagger接口。http://localhost:8800/swagger-ui/ 进行接口测试。

![](.\images\2.png)

## 参考项目

本项目参考项目请访问：https://github.com/wangyapu/mini-rpc

## 注意

本项目不维护，如果看得上代码，请自取。

