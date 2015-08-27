# IndexedCartoon

The project aimed to create an application that display a cartoon based on a category chosen by the user.
The cartoon can be screen scraped from thisisindex.com. The service should return cartoon at different sizes according 
to the device used. 

The project is hosted on Google App Engine(http://1-dot-indexedcartoon-1044.appspot.com/IndexedCartoon).
The source code is located on github.

# Technical Focus
The solution focuses on back-end, with a simple front-end accepting the catogory parameter and 
present the cartoon fetched from thisisindex.com.

## Technology Stack
BackEnd: Java
Framework: MVC

## Reasoning:
Languages: I chose JAVA to develop the web service since I'm more proficient with it.
Framework: I utilized the Model-View-Controller framework as it helps to seperate the business logic from the interface,
which improves maintainability.

The servlet will be acting as the controller. There are two views: prompt.jsp and result.jsp.
The controller will decide which view to show by determining whether there is a search parameter or not.
If there is no parameter, then the prompt.jsp will be used. In case when there is a search parameter,
then it searches for a picture and uses the result.jsp view.

The model will take care of fetching photos from the artist's website, choosing a random one, and getting the photo
in the approriate size for display on PC or phone.

## Future optimization

Add more artists' websites and set it as a parameter, and adjust the categories accordingly.
Select pictures from different pages from the original website instead of just the first page.
Add UI feedback if the call to artist's website is slow.
Tests.

