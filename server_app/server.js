const express=require('express');
const app=express();
const bodyParser = require('body-parser');
const mongoose=require('mongoose');

app.use(bodyParser.json());

//Require Customer model and functions
const Course = require('./models/customer.model.js');

//app.use(bodyParser.urlencoded({extended:true}));



mongoose.Promise=global.Promise;

// Connecting to the database
const dbConfig=require('./config/database.config.js');
mongoose.connect(dbConfig.url, { useNewUrlParser: true })
.then(() => {
	console.log(mongoose.connection.readyState);

    console.log("Successfully connected to the database");    
}).catch(err => {
    console.log('Could not connect to the database. Exiting now...');
    process.exit();
});


// define simple route
app.get('/',(req,res)=>{
    res.json({'message': "Welcome"})
});


//Return all customers
app.get('/api/test_app', function(req, res){
	Course.findAll(function(err, customers){
		if(err){
			throw err;
		}
		
		res.json(customers);
	});
});

//Add a customer
app.post('/api/test_app', function(req, res){
	var customer = req.body;
	
	Course.createe(customer, function(err, customer){
		if(err){
			throw err;
		}
		res.json(customer);
	});
});

//Edit a customer
app.put('/api/test_app/:id', function(req, res){
	var id = req.params.id;  //Use customer.id and move it below customer if this line doesn't work
	var customer = req.body;
	Course.update(id, customer, {}, function(err, customer){
		if(err){
			throw err;
		}
		res.json(customer);
	});
});

//Delete one customer
app.delete('/api/test_app/:id', function(req, res){
	var id = req.params.id;
	Course.delete(id, function(err, customer){
		if(err){
			throw err;
		}
		res.json(customer);
	});
});

//Find one customer
app.get('/api/test_app/:id', function(req, res){
	Course.findOnee(req.params.id, function(err, customer){
		if(err){
			throw err;
		}
		res.json(customer);
	});
});


app.listen(80, () => {
    console.log("Server is listening on port 80");
});