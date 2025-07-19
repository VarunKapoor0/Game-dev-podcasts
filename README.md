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

