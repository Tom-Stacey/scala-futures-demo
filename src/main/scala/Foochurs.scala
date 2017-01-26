import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
object Foochurs {

  def main(args: Array[String]): Unit = {

    println("")
    val first = Future {
      slowAddition(1, 1)
    }
    val third = Future {
      slowerAddition(3, 3)
    }
    val second = Future {
      slowAddition(2, 2)
    }
    val fourth = Future {
      additionError(4, 4)
    }

    val fMap = first.flatMap {
      case result1 => second.flatMap {
        case result2 => third.map { //fourth.map {
          case result3 => result1 + result2 + result3
        }
      }
    } recover {
      case ex => 4392834
    }

    fMap.onSuccess {
      case total => println(s"3 more than $total is ${total + 3}")
    }

    val forComp = for {
      a <- first
      b <- second
      c <- third
    } yield {
      a + b + c
    }

    forComp.onSuccess {
      case grandTotal => println(s"Grand total: $grandTotal")
    }

    val slowerForComp = for {
      a <- first
      b <- second
      c <- futureAddition(a, b)
    } yield {
      a + b + c
    }

    slowerForComp.onSuccess {
      case grandTotal => println(s"takes 4 seconds to add $grandTotal")
    }

    fourth.onFailure {
      case ex => println(s"Failed! Reason: ${ex.getMessage}")
    }

    Thread.sleep(3000)

  }


  def futureAddition(a: Int, b: Int): Future[Int] = Future {
    println(s"future adding $a & $b")
    Thread.sleep(2000)
    a + b
  }

  def slowAddition(a: Int, b: Int): Int = {
    println(s"slowly adding $a & $b")
    Thread.sleep(2000)
    a + b
  }

  def slowerAddition(a: Int, b: Int): Int = {
    println(s"more slowly adding $a & $b")
    Thread.sleep(3000)
    a + b
  }

  def additionError(a: Int, b: Int): Int = {
    println(s"failing to add $a & $b")
    Thread.sleep(2000)
    throw new RuntimeException(s"Couldn't add $a & $b!")
  }
}
