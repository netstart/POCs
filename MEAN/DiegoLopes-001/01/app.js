var express = require('express')
  , app = express();

app.set('views', __dirname + '/views');
app.set('view engine', 'jade');
app.use(express.static(__dirname + '/public'));

app.get('/', function(req, res){ // o mesmo que app.createServer(function(req, res){});
  res.send('Olá mundo!');
});

app.get('/posts', function(req, res){
    res.send('Lista de posts')
});

app.listen(8080, function() {
  console.log('Server is running at localhost: 8080');
});
