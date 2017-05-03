package org.hammerlab

import scala.collection.convert.{ WrapAsJava, WrapAsScala }

package object implicitz {

  {
    case class A(a: Int)
    case class B(b: String)

    implicit def convOpt[T, U](t: Option[T])(implicit conv: T => U): Option[U] = t.map(conv)

    implicit def aToB(a: A): B = B(a.a.toString)

    // Doesn't compile
//    implicitly[Option[A] ⇒ Option[B]]

    // Works
    val b: Option[B] = Some(A(4))

    import org.scalautils.ConversionCheckedTripleEquals._

    // Doesn't compile
//    assert(Some(B("4")) === Some(A(4)))
  }

  {
    Seq(1,2,3).take(2)
    Seq(1,2,3).map(_ * 2)
  }

  {
  }


  {
    import spire.math.Numeric
    implicitly[Numeric[Long]]
  }

  {
    import scala.collection.JavaConversions._
    def double(nums: java.util.List[Int]): Seq[Int] =
      nums.map(_ * 2)
  }

  {
    import scala.collection.JavaConverters._
    def double(nums: java.util.List[Int]): java.util.List[Int] =
      nums
        .asScala
        .map(_ * 2)
        .asJava

  }

  {
    def show[T](x : T)(implicit s : Showable[T]): String = s.show(x)  // "public API"

    trait Showable[T] { def show(x: T): String }

    object Showable {

      implicit object IntShowable extends Showable[Int] {
        def show(x: Int) = s"#$x"  // prepend "#"
      }

      implicit object StringShowable extends Showable[String] {
        def show(x: String) = s"'$x'"  // single-quote
      }

      implicit def listShowable[T](implicit s: Showable[T]): Showable[List[T]] =
        new Showable[List[T]] {
          override def show(x: List[T]): String =
            x
            .map(s.show)
            .mkString(",")
        }
    }

    import Showable.{listShowable, IntShowable}

    show(7)            // #7
    show("abc")        // 'abc'
    show(List(1, 2, 3))  // #1,#2,#3

    show(List(1, 2, 3))(listShowable[Int](IntShowable))

    show(List(1, "2", 3))  // compile error!
  }

  {

    def pathExists(path: Path)(implicit conf: Configuration)

    implicit val conf1 = new Configuration
    implicit val conf2 = new Configuration
    pathExists("path")  // ambiguity error
  }
}

