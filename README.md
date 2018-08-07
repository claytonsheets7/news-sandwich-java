# news-sandwich-java
A spring boot web service that pulls data from all sources available from the google news API and selects the top headlines.  After the articles are gather, articles are ranked for how positive they appear to be based on CSV files of positive and negative media words.  Articles are sorted in descending order from most to least positive.  The web service will be used by a React Native application to produce a "News Sandwich", a list of articles that has positive articles at the beginning and end with the normal top headlines in the middle.

## Notable libraries used
AsyncHtttpClient
SLF4J for logging
TestNG for both acceptance and unit tests
Mockito
