package common

import io.grpc.ManagedChannelBuilder
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder
import io.grpc.netty.shaded.io.grpc.netty.GrpcSslContexts
import java.io.File

abstract class Channel {
  val channel =
    NettyChannelBuilder
      .forAddress("localhost", 50051)
      .sslContext(
        GrpcSslContexts.forClient().trustManager(new File("ssl/ca.crt")).build()
      )
      .build()
  // ManagedChannelBuilder
  //   .forAddress("localhost", 50051)
  //   .usePlaintext()
  //   .build()
}
