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

    first.onComplete {
      case Success(firstResult) => println(s"first: $firstResult")
      case Failure(ex) => println(s"Failed! Reason: ${ex.getMessage}")
    }

    second.onSuccess {
      case secondResult => println(s"second: $secondResult")
    }
    second.onFailure {
      case ex => println(s"Failed! Reason: ${ex.getMessage}")
    }

    third.map {
      case thirdResult => println(s"third: $thirdResult")
    } recover {
      case ex => println(s"Failed! Reason: ${ex.getMessage}")
    }

    Thread.sleep(4000)

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
