# Introduction #

Add your content here.
# Models #

### Board (or Game) ###

---Methods---

---Data Members---

array of 40 spaces

Dice

2 queues of cards

playerTurn

scholarshipFund (money pot in center)

### Player (state pattern) ###

---Methods---

movePiece()

updateCash(int value)


---Data Members---

name

cash

ArrayList of Properties

getOutofJail

positionOnBoard

pieceImage

### Dice (just 2 random number generators 1-6) ###

---Methods---

int roll()

### Space ###

---Methods---

abstract landOn(Player p)

---Data Members---

name

### TaxSpace extends Space ###

---Methods---

landOn(Player p)

---Data Members---

fee

percentage


### PropertySpace extends Space  (think about statePattern) ###

---Methods---

landOn(Player p)

mortgageProperty()

renovate()

sellRenovation()

---Data Members---

Enum color

purchasePrice

mortgageValue

renovationLevel

array[5](5.md) of rentValues

isOwned

isMortgaged


### CornerSpace extends Space ###

---Methods---

landOn(Player p)

### CardSpace extends Space ###

---Methods---

landOn(Player p)