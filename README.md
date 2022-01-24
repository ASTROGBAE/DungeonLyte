## Summary

DungeonLyte is a text adventure game interpreter and compiler. Using a simple to understand syntax, write text adventure games with branching choices, items and conditions.

The interpreter and underlying structure is written in Java. The front-end language for writing an adenture game is called Dracoysh. Write down your epic adventure in Dracoysh script and run it with the Java code!

## How to make a game

Simply write your adventure in the Dracolysh language (see section 'Adventure game code: Dracoysh') in a text file. Name that file 'game.txt', place it in the /data file and run the Java code. 

## Adventure game code: Dracoysh

Since the dawn of the world, ancient dragons have written in their infernal tongue, Dracoysh. 

The basic syntax (called a 'drake') is themed around the body of a dragon. Each body acts to return a data object from the compiler and usually exists within a linear context with other drake objects. Syntax as follows:

> $HEADER[Title]: tail

- HEADER: the return data type of the drake, which in capitals and is a defined set specified by Dracolysh. For now this set includes a ROOM and DOOR object (see the 'Game objects' section for more details). 

- Title: unless stated otherwise, this is the name of the returned object, which is stored for further reference by other drakes. An exception to this rule is the DOOR object, which will store a connected door reference (the other door is defined by whatever was the most recently created door in the linear sequence of code). 

- tail: text that will apear when the engine encounters the said drake object. When a ROOM is encountered, its whole description is this tail. Same for a DOOR object and vice versa. Can include newlines and all other types of text. 

An example of this syntax in action is shown below, which will create two Room objects with descriptions, as well as a Door object with a description and references to the aforementioned Rooms:

> $ROOM[spooky corridoor]: You enter a long, dark corridoor. It is covered in cobwebs.
> $DOOR[Dragon throne room]: Before you is a huge metal door with engravings of twisting tails and fire.
> $ROOM[Dragon throne room]: You feel a sudden wave of heat as a dragon stands before you!

## Game objects

As shown in the above section ('Adventure game code: Dracoysh'), current game objects include a Door and Room object. Both objects contain a text description. Room objects will contain a list of connected Door objects and Door objects will link two Room objects together. 

Of course, Room and Door objects do not need to be literal in your story - they represent choices and branching options. 
