var app = require('express')();
var http = require('http').Server(app);
var io = require('socket.io')(http);

/**
app.get('/', function(req, res){
  res.sendfile('index-2.html');
});**/


var socket_list = {};

//客户端连接
io.on('connection', function(socket){
  console.log('a user connected');

  //客户端连接时，保存socketId和当前连接
  var socketId = socket.id;
  socket_list[socketId] = {
  	socket: socket
  };

  socket.on('login message', function(username){
  	//保存当前用户信息
  	socket_list[socketId].username = username;

  	//广播消息至其他用户
  	socket.broadcast.emit('chat message', username + ' say hi');
  });

  socket.on('chat message', function(msg){
  	console.log('#' +socket_list[socketId].username + '#' +' chat message:' + msg);
  	//广播消息至全部用户
  	//io.emit('chat message', 'for everyone: ' + msg);
  	socket.broadcast.emit('chat message', 'for everyone: ' + msg);
  });

  //客户端断开连接
  socket.on('disconnect', function(){
  	console.log('user disconnected: '+ socket_list[socketId].username);

  	//广播消息至其他用户
  	socket.broadcast.emit('chat message', '#' + socket_list[socketId].username + '#' + ' say goodbye');

  	delete socket_list[socketId];

  });

});

//端口监听
http.listen(8888, function(){
  console.log('listening on *:8888');
});