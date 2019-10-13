package pnd

import io.grpc.stub.StreamObserver

object PrimeNumberDecompositionServiceImpl
    extends PrimeNumberDecompositionServiceGrpc.PrimeNumberDecompositionService {

  def pnd(x: Long, k: Long = 2): LazyList[Long] = {
    if (x <= 1) {
      LazyList.empty[Long]
    } else if (x % k == 0) {
      Thread.sleep(500L)
      k #:: pnd(x / k, k)
    } else {
      pnd(x, k + 1) // seems scala compiler can optimized tail call *by branch*
    }
  }

  def primeNumberDecomposition(
      request: Number,
      responseObserver: StreamObserver[Number]
  ): Unit = {
    print("serving a PrimeNumberDecomposition request by streaming ")
    pnd(request.number) foreach { x =>
      print(".")
      responseObserver.onNext(Number(x))
    }
    println()
    responseObserver.onCompleted()
  }
}
