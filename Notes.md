class App {
    App()
    start() # instantiate the top level class here
}

class Game {
    Bomb : _bomb
    Lemon : _lemon
    Pear : _pear
    Peach : _peach
    Apple : _apple
    Blade : _blade
    # this is the top level class
    onBladeContact()
    launchItem()
    startGame()
    addBalde(Blade blade)
    getChoppable() : returns Fruit or Bomb, both are of type 'Choppable' # polymorphism
}

interface Choppable {}

class Fruit implements Choppable {}

class Apple extends Fruit{}

class Pear extends Fruit{}

class Lemon extends Fruit{}

class Peach extends Fruit{}

class Bomb implements Choppable {
    explode()
}

---




