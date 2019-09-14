personInKitchen(P) :- not isTvOn(P,true); isCouch(P,false).
noSleeping(P) :- isCouch(P,true); isTvOn(P,true).
noSleeping(P) :- isTvOn(P, false); isCouch(P,false).
sleeping(P) :- isTvOn(P, false); isCouch(P,true).

isTvOn(pierre,true).
isCouch(pierre, true).
isTvOn(mathilde,false).
isCouch(mathilde, false).

isTvOn(lise, false).
isCouch(lise, true).