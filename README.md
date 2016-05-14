#Android web grep tool

The app provides only a single service -- GrepService.
The service is used for downloading a little part of the webpage into another application that needs some content.

## Service input

Specified by Intent. 

Intent details:

Package: com.invertisment.webgrep

Service class: com.invertisment.webgrep.GrepService

###Intent extra parameters:

"url" -- url to download the webpage from

"grep" -- array of regex strings

## Service output

Output from regex is specified by first regex group.

Output is returned to other applications by native Android broadcast.

###Broadcast details
Action string:
com.invertisment.webgrep.GrepService.RESULT_ACTION

Extras:
"matches" -- Array of strings containing found results from the webpage. The order and length is the same as in the "grep" array from the input.

"error" -- String value of error in the application

## Bugs
It does not work well with Unicode characters.
