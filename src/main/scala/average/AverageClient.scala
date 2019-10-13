package average

import common.Channel
import pnd.Number
import io.grpc.stub.StreamObserver
import java.util.concurrent.CountDownLatch

object AverageClient extends Channel {
  val client = AverageServiceGrpc.stub(channel)
  def main(args: Array[String]): Unit = {
    val latch = new CountDownLatch(1)

    val responseObserver = new StreamObserver[Double] {
      def onNext(value: Double): Unit = {
        println("received response from server:")
        println(value.number)
      }
      def onError(t: Throwable): Unit = ???
      def onCompleted(): Unit = {
        println("server notifies completion")
        latch.countDown()
      }
    }
    val requestObserver = client.average(responseObserver)

    (1 to 100)
      .map(Number(_))
      .foreach(requestObserver.onNext(_))
    requestObserver.onCompleted()

    latch.await()
  }
}
