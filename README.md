# MyMovieMemoir
Android Application that creates memories of users from their movie experiences (Final Assignment)

This assignment (server-side and client-side) aim towards building a mobile and distributed, personalised diary application for keeping 
track of movies you watched and their details. The mobile app will be a movie memoir that allows people to create, delete, add or view a collection of memories 
that you had about the movies.
The mobile app will interact with the RESTful web service, and retrieve some useful and related information from public web APIs.

The following tasks were implemented in accordance to the application specifications as mentioned below:

1. Sign in (with a Sign-up option) :

The sign-in screen enables existing users to sign in, and allows a new user to sign up. Use only ONE screen here to sign in and a 
button to navigate to the sign-up screen. The sign-in screen will require a username (email) and a password. 
This data will be verified with the data stored in the server-side database during the login.

2. Sign up form (User Profile) Screen :

The sign-up form will enable the new user to enter their information (i.e. first name, surname, gender, DoB, address, state, postcode, email and password).
In this screen, use a radio button for the gender, and for the state, use a spinner. Here our assumption is the users are from Australia. 
For DoB, use a date picker (a calendar widget to pick a date).

3. Home screen :

The home screen should welcome the user by their first name and display the app title, a related and meaningful picture, the current date, 
and the top five movie names, their release dates and rating scores given by the user for the current year (2020)
The main page will use fragments and navigation drawer to navigate to other screens including Movie Search, Movie Memoir, Watchlist, Reports and Maps

4. Movie Search :

The Movie Search screen will enable the user to enter a movie name (case insensitive), and will display a list of matching movies with 
some basic information including the name, the release year, and an image (from Google or any other API).

5. Movie View :

  a) The movie view screen will display the following information for the movie selected in the Movie Search screen 
(in addition to the movie name and release date that will be passed from the Movie Search screen): the genre, the cast, the release date, the country, 
the name of the director(s), a synopsis/plot summary/storyline (from a movie API), rating scores given by the public (from a movie API), and displaying scores as 
stars instead of numbers (supporting both half and full stars)

  b) This screen should also provide two options/buttons for adding the movie to Watchlist or the Memoir.
     i)  When the user clicks the button to add the movie to Watchlist, first you need to check if the movie already exists in the local database. 
         If it exists, you should provide a message about it. If it does not exist, the movie will be added to the Watchlist table described in Task 5, 
         and display a relevant message.
     ii) When the user clicks on Add to Memoir (it means the user has watched it), the user will be directed to the Add to Memoir screen to enter more information 
     (refer to Task 7). 
     
6. Database and Watchlist Screen :
  
  a) In addition to the data that is stored on the server-side database, you will also store data about the watchlist locally using Android Room.
The database will have only one table. The Watchlist table will store the movies that the user has searched, selected in the Movie View screen, and then added to 
Watchlist so they can watch in future. The table should store the name of the movie, the release date (in case it is a remake), and the date and time it was added 
to Watchlist. The other information about the movie is not stored because it can be dynamically retrieved anytime using the APIs. 

  b) The Watchlist screen will show all the movies stored in the local database (the movie name, release date, and the date and time added to Watchlist) in a list 
such that they can be selected. The data in the list will be updated after any change through implementation of the observer pattern.The screen should provide 
two options for the selected movie from the list: 1) Delete and 2) View.

  c) In the Watchlist screen, after selecting a movie from the list, if the View button is clicked, the user will be redirected to the Movie View screen where 
it will show the details of the selected movie (the same details that the Movie View shows for the selected movie from the Search screen, refer to Task 5).
In the Movie View screen, the Add to Watchlist button must be disabled because the movie already exists in the local database. The purpose here is to allow 
the user to add the Watchlist movie to their memoir if they watched it.

  e) In the Watchlist screen, after selecting a movie from the list, if the Delete button is clicked, the movie will be deleted. Provide a confirmation message 
before delete. After the user confirmation, the selected movie must be deleted from the local database, and the watchlist screen must be refreshed.

7. Add to Memoir:
a) In Task 5, it was mentioned that after the selecting a movie, the user could add the movie to his/her movie memoir by clicking on the Add to Memoir 
button that redirects the user to the Add to Memoir screen. This screen will show some data passed from the previous movie view screen including the movie 
name and its release date, plus a related image (from Google or any other API), and asks for further information.

b) The screen allows the user to enter more information about their movie memoir. This includes:
   The date that the user watched it (use a date picker) and time.
   The cinema name and its suburb or postcode (e.g. Hoyts Chadstone or Hoyts 3148). You could need to use a spinner listing the cinema names and their 
  suburbs or postcodes that are stored on the server-side database most popular suburbs/postcodes where cinemas are.
   The user must be able to add a new cinema if it is not listed in the spinner and does not exist in the server-side database. You can use an additional 
  screen to obtain the cinema details from the user. After the cinema is added (requires using a POST method), the spinner list needs to be updated.
   A short comment about their memory, opinion and how they felt after watching the movie
   A rating score for the movie provided by the user using stars. You need to come up with a creative, dynamic and efficient way to support full and half 
  stars for rating.
  
c) After all the information is entered, and the user clicks the submit/add button, the movie memory details and cinema id details must be added to the memoir 
and cinema table at the server side 

8. Movie Memoir:

  a) This screen will display a list of all movies stored on the server-side database. This list will show the movie name, the release date, 
  an image (from Google or any other API), and the date that the user watched it, cinema suburb/postcode, user comment/memory, and the user’s rating score 
  as stars. It requires calling a GET method to retrieve all these movies from the server-side database. 
  
  b) The screen will provide three sorting options (i.e. based on the date, the user given rating scores as stars, and public review scores (from a movie API) 
  as stars. The filter options can include different genres (use a spinner).

  c) This screen will be scrollable. Each movie can be selected. When a movie is selected, further details about that movie should be retrieved from the APIs
  including the official trailer. The details will be similar to the results of Movie View (Task 5). 

9. Report:

The Report screen will provide the user with two types of reports that will require querying the movie memoir table calling the right REST methods.
  
  a) Pie chart: the top section of the screen will include a date picker to allow the user to enter a starting date and an ending date, and a pie chart 
  that will display the total number of movies watched per suburb/postcode (as percentage %) for the selected period. The labels and percentages should be 
  shown on the pie chart.
  
  b) Bar graph: the bottom section of the screen will include a spinner to allow the user to select a year (2015 to 2020), and a bar graph to show the total number of movies watched per month for the selected year. For your interview, make sure you have sufficient data for a few months in a year. (5 marks)

10. Map:

  a) The map screen will show the user’s home location. You need to programmatically convert the full address of the user based on their address and 
  postcode into latitude and longitude values (using either Google Geocoding API or Android built-in libraries like Geocoder).Then use the latitude and longitude 
  values for displaying the location on the map. This requires a GET method to retrieve the user information from the server-side database.

  b) Similar to the task 10 (a), convert the cinema suburbs/postcodes into latitude and longitude values and show them on the map. This requires a GET 
  method to retrieve the user information from the server-side database.
  
  c) When the user taps on a cinema marker on the map, display its name (e.g. Village Cinemas Doncaster).
  
  d) The marker for the user location and cinemas should have a different colour. 





