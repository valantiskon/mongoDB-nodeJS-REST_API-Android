# Use Instructions  
Build a REST API in Node.js with mongoDB and then call the REST services from your Android application.  

##### Create a database and a collection on mongoDB  
**Database name:** mongoDB_clients  
**Collection name:** clients_app  

##### Open mongoDB  
To do that open a Node.js command prompt and then type

		`mongod`  
    
##### Create connection to mongoDB on port 3000  
Open another Node.js command prompt, change directory to server_app file and run command

		`nodemon`  
This is the server part up and running. Afterwards, the android app will be fully functional.  

##### Android Application's functions (CRUD - create, read, update, delete)
- [x] Create a new client
- [x] Delete an existing client
- [x] Update client's info
- [x] Display all clients  

Read function is used to recover client's info once change of orientation occurs.  
After each change (create, update, delete) in the Android App, data refresh is needed in the main page for changes to show (top to bottom swipe).  

##### Running the Android Application on an emulator  
use http://10.0.2.2:3000/api/clients_app/ (for Genymotion use 10.0.3.2)  

##### Running the Android Application on a real device   
replace 10.0.2.2 with your local IP address (http:// (YOUR IP ADDRESS) :3000/api/clients_app/)  

Solarized dark              |  Solarized Ocean          |  Solarized dark           |  Solarized Ocean              |  Solarized Ocean              |  Solarized Ocean              |  Solarized Ocean              
|:-------------------------:|:-------------------------:|:-------------------------:|:-------------------------:|:-------------------------:|:-------------------------:|:-------------------------:
<img src="https://github.com/valantiskon/mongoDB-nodeJS-REST_API-Android/blob/app-showcase/images/empty_home_screen.png" width="300">  | <img src="https://github.com/valantiskon/mongoDB-nodeJS-REST_API-Android/blob/app-showcase/images/add_screen.png" width="300">  | 
<img src="https://github.com/valantiskon/mongoDB-nodeJS-REST_API-Android/blob/app-showcase/images/add-edit_client1.png" width="300">  | <img src="https://github.com/valantiskon/mongoDB-nodeJS-REST_API-Android/blob/app-showcase/images/home_screen1.png" width="300">  | 
<img src="https://github.com/valantiskon/mongoDB-nodeJS-REST_API-Android/blob/app-showcase/images/add-edit_client2.png" width="300">  | <img src="https://github.com/valantiskon/mongoDB-nodeJS-REST_API-Android/blob/app-showcase/images/home_screen2.png" width="300">  | 
<img src="https://github.com/valantiskon/mongoDB-nodeJS-REST_API-Android/blob/app-showcase/images/Read_Delete_Update-Screen.png" width="300">

