Vending Machine
===============

In this exercise, you will build the brains of a vending machine. It will accept
money, make change, maintain inventory, and dispense products. All the things
that you might expect a vending machine to accomplish.

A machine operator loads the machine with products for sale. A customer places
orders by inserting coins into the machine and selecting a product. Then, the
selected product gets dispensed to the customer and the remaining amount gets
returned. At the end of the day, the machine operator collects all the income.

All the hardware will be built separately, and it will communicate with your
code via simple Kotlin interfaces defined by yourself.
For example, when a sensor detects that a coin has been inserted, it might
call your vending machine with `coinInserted(coin)`, or when the machine
decides to dispense a product, it might call the listening code with
`dispenseProduct(product)`. Building the product dispenser or other adapters
is out of scope of this exercise.


Installation
------------

You will need Java 21.
You might use https://sdkman.io/ and install the correct version by running:

```
sdk env install
```

To compile, lint and test the code, run:

```
./gradlew build
```
