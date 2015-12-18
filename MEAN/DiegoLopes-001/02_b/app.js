var express = require('express')
  , mongoose = require('mongoose')
  , app = express();

var Post = require('./models/posts');
var User = require('./models/users');

app.set('views', __dirname + '/views');
app.use(express.static(__dirname + '/public'));

mongoose.connect('mongodb://localhost:27017/mean')

app.get('/', function(req, res){ // o mesmo que app.createServer(function(req, res){});
  res.send('Olá mundo!');
});

//http://localhost:8080/posts/
app.get('/posts', function(req, res){

    Post.find(function(err, posts){
      res.send(posts);
    });
});


//http://localhost:8080/posts/98765434567890
app.get('/posts/:id', function(req, res) {
  Post.find({_id: req.params.id}, function(err, post){
    res.send(post);
  });
});

//http://localhost:8080/users
app.get('/users', function(req, res){
    User.find(function(err, users){
      if(err){
        console.log(err);
      }

      res.send(users);
    });
  //  res.send('Lista de posts')
});

app.listen(8080, function() {
  //http://localhost:8080/
  console.log('Server is running at localhost: 8080');
});
