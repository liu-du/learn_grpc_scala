package average
import io.grpc.stub.StreamObserver
import pnd.Number

object AverageServiceImpl extends AverageServiceGrpc.AverageService {
  def average(
      responseObserver: StreamObserver[Double]
  ): StreamObserver[Number] = {
    val requestObserver = new StreamObserver[Number] {
      var sum = 0L
      var n = 0

      def onNext(value: Number): Unit = {
        println("received a streaming request from client")
        println(value.number)

        sum += value.number
        n += 1
      }
      def onError(t: Throwable): Unit = {}
      def onCompleted(): Unit = {
        println("client notify completion")
        responseObserver.onNext(Double(sum.toDouble / n.toDouble))
        responseObserver.onCompleted()
      }
    }
    requestObserver
  }
}
