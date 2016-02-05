var mongoose = require("mongoose");
mongoose.connect('mongodb://localhost/testdb');

var Cat = mongoose.model('Cat', {name: String});

var kitty = new Cat({name: 'Zildjian'});

kitty.save(function(err){
	if(err){
		console.log("Meow");
	}

});
// on mongo console execute
// mongo
// use testdb
// db.cats.find();