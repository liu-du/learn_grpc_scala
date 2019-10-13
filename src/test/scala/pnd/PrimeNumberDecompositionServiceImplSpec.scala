package pnd
import org.scalatest.FlatSpec
import org.scalatest.Matchers

class PrimeNumberDecompositionServiceImplSpec extends FlatSpec with Matchers {
  it should "decompose to correct prime numbers" in {
    PrimeNumberDecompositionServiceImpl.pnd(120L, 2L) shouldBe
      LazyList(2L, 2L, 2L, 3L, 5L)
  }
}
