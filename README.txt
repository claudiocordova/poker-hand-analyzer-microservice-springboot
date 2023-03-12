
POST JSON below in Postman to: http://localhost:8080/isstraight
ContenType: application/json


[{ "value" : "ACE", "suit" : "HEARTS"},
{ "value" : "QUEEN", "suit" : "DIAMONDS"},
{ "value" : "JACK", "suit" : "CLUBS"},
{ "value" : "10", "suit" : "SPADES"},
{ "value" : "KING", "suit" : "HEARTS"},
{ "value" : "6", "suit" : "DIAMONDS"},
{ "value" : "2", "suit" : "CLUBS"}]


Expected Response:

{"status":"VALID_HAND","message":"It is a straight!"}

Run Unit tests.