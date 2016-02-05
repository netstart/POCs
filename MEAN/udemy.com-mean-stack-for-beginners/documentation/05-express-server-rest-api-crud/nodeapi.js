var application_root = __dirname,
    express = require("express"),
    path = require("path"), //This module contains utilities for handling and transforming file paths.
    mongoose = require('mongoose'),
    fs = require('fs'),  //File I/O is provided 
    cors = require('cors'); //enable Cross Origin Resource Sharing
 

 
var app = express();
 app.use(cors());
// Database
 
mongoose.connect('mongodb://localhost/testdb');
mongoose.connection.once('connected', function() {
                console.log("Connected to database")
});
var User = mongoose.model('users', { name: String ,age: Number,title: String});
// Config
//http://localhost:3000/people/
app.get('/people', function (req, res) {
    User.find().exec(function(err, people) {
        console.log(people);
        if (err) {
            res.render('error', {
                status: 500
            });
        } else {
            console.log(people);
            res.json(people);
            //or
            //res.send(people);
        }
    });
});


//http://localhost:3000/people/jason
app.get('/people/:name', function (req, res) {
    User.find({"name":req.params.name}).exec(function(err, people) {
        console.log(people);
        if (err) {
            res.render('error', {
                status: 500
            });

        } else {
            res.jsonp(people);
        }
    });
});



//http://localhost:3000/update?name=tyler&change[name]=david
app.get('/update', function (req, res) {
    console.log(req.query);
    var changeName = req.query.change.name;
    
    User.findOne({"name" : changeName}, function (err, people){
        console.log(people);
         if (people) {
             people.name = changeName;
             people.save(function (err) {
                if(err) {
                    console.error('ERROR!');
                } else {
                    res.redirect('/people/' + changeName);
                }
            });
          } else {
            res.send('a null value tried to update - please try changeing the url above to a name in the database');
          };
    })
});

//http://localhost:3000/create?name=stephen&title=ftg&age=98
app.get('/create', function (req, res) {
    console.log(req.query);
    var name = req.query.name;
    var title = req.query.title;
    var age = req.query.age;
    var create = {"name":name , "age":age, "title":title}

    var Usercreate =new User(create);
    Usercreate.save(function(err) {
        if (err) {
            console.error('ERROR!');
           
        } else {
            res.redirect('/people/');
        }
    });
 
});

//http://localhost:3000/delete?name=david
app.get('/delete', function (req, res) {
    console.log(req.query);
    var name = req.query.name;

    User.findOne({"name":name}, function (err, people){
      if (people) {
        console.log(people);     
        people.remove(function (err) {
            if(err) {
                console.error('ERROR!');
            }else{
                res.redirect('/people/');
            }
        });
      }else{
        res.send('a null value tried to delete - please try changeing the url above to a name in the database');
      };
    })
});


//http://localhost:3000/multiplevariables?order=desc&shoe[color]=blue&shoe[type]=converse
app.get('/multiplevariables', function (req, res) {
    User.findOne({"name":req.params.name}, function (err, people){
        res.jsonp(req.query.order+ req.query.shoe.color +req.query.shoe.type);
    })
});
 

//http://localhost:3000/api
app.get('/api', function (req, res) {
  res.send('Location API is running');
});
//http://localhost:3000/api/example12345
app.get('/api/:id', function(req, res){
  res.send('api saw this parameter = ' + req.params.id);
});
//anything else
app.all('*', function(req, res){
  res.sendFile(application_root +"/indexnodeapi.html")
})
 
 
app.listen(3000);
console.log('Server running at http://localhost:3000/');

