const mongoose = require('mongoose');

const CustomerSchema = mongoose.Schema({
	id: {
		type: String
	 },
	name: {
		type: String
	},
	last_name: {
		type: String
	},
	age: {
		type: Number
	}
}, {
		collection: 'clients_app',
        timestamps: false
    });

var customer = module.exports = mongoose.model('mongoDB_clients', CustomerSchema);

//Get all customers
module.exports.findAll = function(callback, limit){
	customer.find(callback).limit(limit);
}

//Get a customer
module.exports.findOnee = function(id, callback){
	customer.findById(id, callback);
}

//Add a customer
module.exports.createe = function(cust, callback){
	customer.create(cust, callback);
}

//Edit a customer
module.exports.update = function(id, cust, options, callback){
	var query = {_id: id};
	var update = {
		id: cust.name + cust.last_name + cust.age,
		name: cust.name,
		last_name: cust.last_name,
		age: cust.age
	}
	customer.findOneAndUpdate(query, update, options, callback);
}

//Delete a customer
module.exports.delete = function(id, callback){
	var query = {_id: id};
	customer.remove(query, callback);
}
