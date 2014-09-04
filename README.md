SocketIODemo
============

android and socketio demo


1.介绍
结合android 和 socketio
项目使用android studio 
服务端nodejs + express

2.使用
 1）进入server文件夹，执行 npm install 下载所需模块
 2）在nodejs环境下 执行server.js文件
 3）运行android程序，点击 "CONNECT"，则nodejs控制台会打印 “a user connected”，表示客户端（手机）与服务端（nodejs）已建立连接

3.注意项
项目支持socketio 1.0+，对于其他的版本（1.0以下）貌似不太兼容，会出现握手协议出错等问题
