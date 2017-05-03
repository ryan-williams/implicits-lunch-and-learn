package org.hammerlab

import org.scalautils.ConversionCheckedTripleEquals._

package object implicits {

  {
    case class A(a: Int)
    case class B(b: String)

    implicit def convOpt[T, U](t: Option[T])(implicit conv: T => U): Option[U] = t.map(conv)

    implicit def aToB(a: A): B = B(a.a.toString)

    // Doesn't compile
    implicitly[Option[A] ⇒ Option[B]]

    // Works
    val b: Option[B] = Some(A(4))

    // Doesn't compile
    assert(Some(B("4")) === Some(A(4)))
  }
}
