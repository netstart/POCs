var express = require('express');
var app = express();
var mongoose = require('mongoose');
//require mongoose node module
//connect to local mongodb database
var db = mongoose.connect('mongodb://localhost/testdb');
 
// mongoose.connect(uri, options);
// The following option keys are available:

//  db      - passed to the connection db instance
//  server  - passed to the connection server instance(s)
//  replset - passed to the connection ReplSet instance
//  user    - username for authentication (if not specified in uri)
//  pass    - password for authentication (if not specified in uri)
//  auth    - options for authentication
//  mongos  - Boolean - if true, enables High Availability support for mongos

//attach lister to connected event
mongoose.connection.once('connected', function() {
                console.log("Connected to database")
});

app.get('/', function (req, res) {
  res.send('Hello World!');
});

var server = app.listen(3000, function () {

  var host = server.address().address;
  var port = server.address().port;

  console.log('Example app listening at http://%s:%s', host, port);

});
