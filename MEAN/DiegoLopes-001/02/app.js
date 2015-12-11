var express = require('express')
  , mongoose = require('mongoose')
  , app = express();

app.set('views', __dirname + '/views');
app.set('view engine', 'jade');
app.use(express.static(__dirname + '/public'));

mongoose.connect('mongodb://localhost:27017/mean')

app.get('/', function(req, res){ // o mesmo que app.createServer(function(req, res){});
  res.send('Olá mundo!');
});

mongoose.model('posts', {title: String});
mongoose.model('users', {name: String});

http://localhost:8080/posts/
app.get('/posts', function(req, res){

    mongoose.model('posts').find(function(err, posts){
      res.send(posts);
    });
  //  res.send('Lista de posts')
});


http://localhost:8080/posts/98765434567890
app.get('/posts/:id', function(req, res) {
  mongoose.model('posts').find({_id: req.params.id}, function(err, post){
    res.send(post);
  });
});

//http://localhost:8080/users
app.get('/users', function(req, res){
    mongoose.model('users').find(function(err, users){
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
