# DoggoBreedApp

Shape Android Open Assignment
The task
Your task is to develop an that works as a dog breed image catalog with data fetched from
https://dog.ceo/dog-api
The following features should be included:
A list of dog breeds
As a user you should be able to:
- See dog breeds
- Tap on a dog breed and go to that dog breeds screen
A dog breed screen
As a user you should be able to:
- See multiple images of that specific dog breed
- Tap on a button on a dog breed image to like/unlike it
A favorite images screen
As a user you should be able to:
- See liked dog breed images including what dog breed it belongs to
- Filter images by selecting a breed

The rules
The most important thing is that you show your understanding of code structure, network
handling, state management, ui, and layout.
- You should use the following API: https://dog.ceo/dog-api
- Use no longer than 1 day on this project. If you don’t complete all the tasks don’t worry.
- You can use any third party libraries that you find suitable.
- Save your project on github for review (please use a private repo and give us access -
so other candidates don’t find it and “cheat”).
Tips
- Fetching data from an API is not always successful. You should consider handling error
scenarios when fetching data.
- The focus is not on design, but it’s of course valued if you decide to make some
interesting UI and pay attention to the details

-----------------------------------------------------------------------------------------------

Some basic information about functionality of an app:

* I used MVVM architecture
* Decided to preserve the data in Room database
* Even though I am more skilled and confident in Java, I have decided to do the assignment in Kotlin as in order to use any opportunity to learn/practice new things
* Used Pagination from Jetpack libraries in order to ease down on loading of data and fully completed the MVVM backbone of the project
* UI being fully update from LiveData 
* Considered using Retrofit for API to the server but decided that Volley library was a simple choice to go
* App has 3 screens as required from the assignment - (MainActivity - list of dog breeds , BreedPictursActivity - list of specific breed pictures , FavoritesActivity - list of favorite pictures)
* App might look overcomplicated but it would be also easier to scale the app from current solution e.g. using BoundaryCallback when scroling to the end of the list
* Some functionalities could have been done much better but given the limited time horizon, I tried to get it done without too much thinking (as in Hammer to Nail strategy)
* UI is very simplistic as it was recommended

* Please comment on anything that could have been done better, any ideas, advices and so on, as it is important part of growing and learning. Thank you !

