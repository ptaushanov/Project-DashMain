# BusinessProjectSap (DashMain)

### System build on trust

Manage all activities regarding your sales and ease through troublesome tasks in a matter of minutes.

## Built With:

* [IntelliJ Idea](https://www.jetbrains.com/idea/) - *The IDE used*
* [Gradle](https://gradle.org/) - *Gradle Build Tool*

## Made with:

* [Spring Boot](https://start.spring.io/) - *Spring Boot*
    * Spring Web
    * Spring Data MongoDB
    * Validation (Hibernate)

* [Thymeleaf](https://www.thymeleaf.org/) - *Thymeleaf (Template engine)*

#### Styles provided by:

* [MaterializeCSS](https://materializecss.com) - *MaterializeCSS*
* [AnimateCSS](https://animate.style/) - *AnimateCSS*

## Authors:

* **Petar Taushanov** - [ptaushanov](https://github.com/ptaushanov)

## Building the project

Install Java SE Development Kit 8 (if not installed)  
https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html

* Clone the GitHub repository:  
  ``
  git clone https://github.com/ptaushanov/BusinessProjectSAP.git
  ``

* Change directory:  
  `` cd BusinessProjectSAP``

* Run Gradle's build command:  
  ``./gradlew build`` (Linux/Mac)  
  ``gradlew.bat build`` (Windows)

Wait for the process to finish and we are done!

## Running the project

In the project's root directory run the following command:  
``./gradlew bootRun``(Linux/Mac)  
``gradlew.bat bootRun`` (Windows)

## Setting up a local database

By default the project comes with a **MongoDB** database hosted on **MongoDB Atlas** cloud database, but that can be
changed easily.

* Download and Install **MongoDB Compass**:  
  https://www.mongodb.com/products/compass

* Create a local database from MongoDB Compass:

Remember the port of the local database. We will use it later.

* Create a database called ***"ApplicationDB"***

Create the following collections inside the new database:

* Admins
* Customers (optional)
* Products (optional)
* PromoEvents
* SalesReps (optional)

Create a new Admin inside the collection **Admins**:

* ADD DATA > Import Document
* Paste the following:

  ``{
  "firstName": "Admin",
  "lastName": "Admin",
  "username": "admin"
  }``

* Then **Insert**

Setting up **ttl (Time to live)**  on **PromoEvents** collection:

* Open the **PromoEvents** collection panel
* From the tabs above select **"Indexes"**
* Then create an index with the **[CREATE INDEX]** button
* For an index Name type: **"endDateIndex"**
* For a field name select **"endDate"**
* Use the dropdown to the right of the field name and choose: `1 (ascending)`
* Under options check the box **"Create TTL"**
* Finally create the index with the **[CREATE INDEX]** button

All of this is needed so that if a promotion expires **MongoDB** automatically removes the expired promotion from the
database.

Changing to the local database inside **application.properties**:

* Find and open  **application.properties** under:

```
BusinessProjectSap   
│
└─── src
     │
     └─── main
	     │
	     └─── resources
		   │
		   └─── application.properties
```

* Change **spring.data.mongodb.uri** to:  
  ``spring.data.mongodb.uri=mongodb://localhost:27017/ApplicationDB``  
  Change the port to the port of your local **MongoDB** database if needed.  
  (Replace **27017** with the correct port)

* Save the changes to the file
* Rebuild and rerun the project

## License

This project is licensed under the MIT License - *see
the* [LICENSE.md](https://github.com/ptaushanov/BusinessProjectSAP/blob/master/LICENSE) *file for details.*