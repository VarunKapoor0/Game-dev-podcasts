# Game-dev-podcasts
This app groups all the game dev podcasts in one place. 

Work done, and work remaining:

[BRANCH: [https://github.com/VarunKapoor0/Game-dev-podcasts/tree/feature_RoomDB_dev]

17th July:
(Missed the updates for the first few days of development) Also check out the Github project for GameDevPodcasts.
TODO:
1. Need to make sure that when an episode is selected, a new page opens up with the episode details.
2. Episode list UI and data needs to match the actual data(verification).
3. Each episode needs to have one card. Currently episodes cards and data are duplicated. [NEED TO FIX].
4. API Design for processing data and sending it to the device. 

Done:
1. Room integration.
2. Hilt integration.
3. Storing the incoming data from the rss links into a room database.
4. Dependency injection using Hilt already in use.
5. Using XMLPullParser to analyse the data from the rss feeds.
6. Project structure defined for MVVM protocol.


17th July End:

Getting stuck at obtaining podcast title name from each feed. Error thrown in RSSFeedRepository.kt for index out of bounds. Have to fix the following tomorrow:

1. Use of Sets and arraylists for removing duplicates. Either remove while inserting values or via room, no need of converting to sets.
2. Fix the use of insideChannel variable for getting podcast title. Check if it is working properly and find root cause of the index out of bounds error.


[18th July START]:

Worked on episode details, episode list and podcast list pages yesterday. 
1. Episode list page works for now.
2. Episode details page shows the selected episode's details and url for listening.
3. Podcast list page is throwing errors due to my handling of the rss feeds response parsing in RSSFeedsRepository.kt.

First order of business is to work on the error in RSSFeedsRepository.kt, which is acting as a blocker for obtaining the podcast list and displaying it.


[18th July END]:

Fixed the initial repo error.

1. Podcast list is now visible.
2. Added a new screen for viewing podcast details, with a button which leads to the episode list screen.
3. In the flow, the episode list screen is currently empty(was working earlier). This has happened after I integrated the new screen.
4. Created a new table for Podcast details. Currently the only connection between the Podcast and Episode table is the podcast name.
5. Have to add a foreign key concept to connect the two tables.
6. Fixed the issue of duplicates.
7. Have to properly handle the seasons issue. Some podcasts have seasons and some dont.

TODO:
1. Fix the episode list screen.
2. Handle seasons for podcasts.
3. Add foreign key concept for the 2 tables.

And any other bugs which will come up(as they do XD)

[30th July START]:

Came back after a few days. The episode list screen is working. Will put handling seasons for podcasts on backlog for now. Action points for today:

TODO:

1. Fix the UI for Home Screen, Podcast list screen, podcast screen, episode list screen and episode screen. 
2. UI needs to feel smooth and proper positioning of buttons and text. Have to figure out the color too.
3. Need to analyse and understand if the application requires top/bottom navigation. NOT top priority for now.
4. Use coil for loading images for podcasts and episodes.
5. Build loading bars for when data has to be loaded. Currently, the UI blocks until data is obtained / pulled.

[BACKLOG]:

1. Handle seasons in Room and UI.
2. Add more podcasts. - See point 5
3. Add some form of genAI for podcast/episode summary.
4. Analyse the requirement of top/side/bottom navigation for the application.
5. Possibly use web scraping to obtain rss feeds of more game dev podcasts.

[30th July END]:

Done:

1. Modified the UI for Home Screen, podcast list screen, podcast screen, episode list screen and episode screen.
2. Now has a Splash screem with a logo.
3. Podcast screen now has a tab navigation with overview and Episodes tabs.
4. Episode list screen is now moved to the Episodes tab under podcast screen.
5. Overview tab contains the description of the podcast.
6. Episode screen now has description of the episode.
7. Images now loaded using coil for podcasts.
8. Added 4 more podcast rss links.

[BACKLOG]:

1. Handle seasons in Room and UI.
2. Add more podcasts. - See point 5
3. Add some form of genAI for podcast/episode summary.
4. Analyse the requirement of top/side/bottom navigation for the application.
5. Possibly use web scraping to obtain rss feeds of more game dev podcasts.
6. Build loading bars for when data has to be loaded. Currently, the UI blocks until data is obtained / pulled.


