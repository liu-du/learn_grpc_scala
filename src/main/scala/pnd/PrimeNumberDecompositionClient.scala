package pnd
import common.Channel

object PrimeNumberDecompositionClient extends Channel {

  val client = PrimeNumberDecompositionServiceGrpc.blockingStub(channel)

  def main(args: Array[String]): Unit = {
    val response =
      client.primeNumberDecomposition(Number((1L to 15L).reduce(_ * _)))
    response foreach { x =>
      print(x.number)
      Console.out.flush()
      print(" ")
      Console.out.flush()
    }
    println()
  }
}
