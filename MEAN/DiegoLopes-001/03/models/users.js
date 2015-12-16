var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var userSchema = new Schema({ name: String, user: String});

var User = mongoose.model('users', userSchema);

module.exports = User;
