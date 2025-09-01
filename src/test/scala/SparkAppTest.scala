package com.example

import org.scalatest.funsuite.AnyFunSuite

class SparkAppTest extends AnyFunSuite:
  
  test("Basic Scala 3 functionality works"):
    val data = Seq(("test", 1), ("data", 2))
    assert(data.length == 2)
    assert(data.head._1 == "test")
    assert(data.last._2 == 2)
  
  test("Case classes and pattern matching work"):
    case class Person(name: String, age: Int)
    val people = Seq(Person("Alice", 25), Person("Bob", 30))
    
    val adults = people.filter(_.age >= 18)
    assert(adults.length == 2)
    
    val names = people.map {
      case Person(name, age) if age > 25 => s"$name (senior)"
      case Person(name, _) => name
    }
    assert(names == Seq("Alice", "Bob (senior)"))
