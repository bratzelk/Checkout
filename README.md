Checkout System
=======================

[![Build Status](https://travis-ci.org/bratzelk/Checkout.svg?branch=master)](https://travis-ci.org/bratzelk/Checkout)

Table of Contents
-----------------

- [Intro](#intro)
- [Prerequisites](#prerequisites)
- [Building and Testing](#building-and-testing)
- [Where do I start](#where-do-i-start)
- [Design and Implementation](#design-and-implementation)
- [Tests](#tests)

Intro
-------------

This is my implementation of the DiUS Checkout System.

The requirements for this project can be found at: https://gist.github.com/codingricky/2913880

Prerequisites
-------------

- Java 8
- Maven

Building and Testing
---------------

To build the application and run the tests, simply navigate to the project's root directory and run:

```
mvn clean verify
```

Where do I start?
---------------
Since the project doesn't contain a main method the best place to start would be the acceptance tests.: ```src/test/java/com/dius/checkout/acceptance/AcceptanceTest.java```
This file sets up and runs the scenarios from the spec. It serves as a good usage example to consumers of the Checkout class.


Design and Implementation
---------------
This section briefly describes the important interfaces/classes of the system and the design decisions behind them.


##### ItemRepository
   This provides an interface for accessing information about the items available at the shop.
   
##### Order
   This holds information about a person's order. An order is made up of Rows. Each row is grouped by SKU (much like an order receipt from a shop) and contains the total quantity, original unit price, final unit price, etc. This means later on we could easily tell the user how much money they've saved, or how many free items they've received.
   
##### Rule
   The rule interface allows us to swap in different pricing rule implementations before applying them to the order. This meets the requirement for rules to be flexible. For example, the SimpleBundleRule could trivially be extended to allow different and more complex bundle combinations.

Tests
---------------
There are a number of unit tests which cover pretty much everything that isn't a trivial getter/setter. 

