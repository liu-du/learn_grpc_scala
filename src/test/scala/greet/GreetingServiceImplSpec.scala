package greet

import org.scalatest._

class GreetingServiceImplSpec extends AsyncFlatSpec with Matchers {

  it should "return correct GreetingResponse" in {
    GreetingServiceImpl.greet(GreetingRequest(Some(Greeting("x", "y")))) map {
      _ shouldBe GreetingResponse("Hello x y!")
    }

    GreetingServiceImpl.greet(GreetingRequest(None)) map {
      _ shouldBe GreetingResponse("Hello!")
    }
  }

}
