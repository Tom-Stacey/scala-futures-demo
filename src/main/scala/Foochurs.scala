

object Foochurs {

  def main(args: Array[String]): Unit = {

    val first = slowAddition(1, 1)
    val second = slowAddition(2, 2)
    val third = slowerAddition(3, 3)

    println(s"first: $first")
    println(s"second: $second")
    println(s"third: $third")

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
