var express = require('express')
  , mongoose = require('mongoose')
  , app = express();

var Post = require('./models/posts');
var User = require('./models/users');

app.set('views', __dirname + '/views');
app.use(express.static(__dirname + '/public'));

//mongoose.connect('mongodb://localhost:27017/sanguenaveia');

app.get('/', function(req, res){ // o mesmo que app.createServer(function(req, res){});
  res.send('Olá mundo!');
});

app.listen(8080, function() {
  //http://localhost:8080/
  console.log('Server is running at localhost: 8080');
});
