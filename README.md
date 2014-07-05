OSMTileMachine
==============

Render at tileset of any region of the world using openstreetmap data. No database or unix knowledge required. Runs on Windows.

Will be scable and able to render a region of any size. Only limited by CPU time and disk space, not system RAM. 

Project status: 
Working proof of concept.

----------------------
Input:
command line arguments specifying which area to render

Dependencies: 
wget
osmupdate
osmconvert
maperitive

System requirements:
fast internet connection
100+ GB HDD space
a few days of CPU time on a modern PC

Output:
Openlayers compatible tileset for any geographic area, ready to be served as static png files with any web server. 

-----------
