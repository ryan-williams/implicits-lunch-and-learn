# implicits and you

## [Scala Days 2017 KeyNote](https://www.youtube.com/watch?v=Oij5V7LQJsA)

![](https://cl.ly/1B0D3B2l3O25/Screen%20Shot%202017-05-02%20at%209.33.40%20PM.png)

## Context

![](https://cl.ly/0P373A450t2z/Screen%20Shot%202017-05-02%20at%209.28.12%20PM.png)

## Bad Context:

![](https://cl.ly/2g0U1d0C332L/Screen%20Shot%202017-05-02%20at%209.27.49%20PM.png)

## "Parameterize all the things"

![](https://cl.ly/0G3l1u0T0e19/Screen%20Shot%202017-05-02%20at%209.31.23%20PM.png)

## "Too much of a good thing"

![](https://cl.ly/3b1J0w0a1N0w/Screen%20Shot%202017-05-03%20at%2012.00.52%20PM.png)

## "A more direct approach"

![](https://cl.ly/03013B2h3Q2B/Screen%20Shot%202017-05-03%20at%2012.01.22%20PM.png)

## 3 kinds of implicits

### implicit parameters

```scala
def pathExists(path: Path)(implicit conf: Configuration): Boolean = …
```

![](https://cl.ly/122C3r0l2w1X/Screen%20Shot%202017-05-03%20at%2012.00.23%20PM.png)

### implicit conversions

![](https://cl.ly/1r0B0P1Q072t/Screen%20Shot%202017-05-03%20at%2011.59.59%20AM.png)

```scala
implicit def aToB(a: A): B = …
```

### implicit classes

![](https://cl.ly/2P1V0z472t0B/Screen%20Shot%202017-05-03%20at%2011.59.30%20AM.png)

### When should we use implicits?

![](https://cl.ly/2E3t3R182k2k/Screen%20Shot%202017-05-03%20at%2012.02.32%20PM.png)

### Extension Methods

![](https://cl.ly/3C1H2s1A203e/Screen%20Shot%202017-05-03%20at%2012.04.37%20PM.png)

"Other languages have special-cased extension-method functionality, but Scala gets them as a corollary of implicits"


### JavaConversions → JavaConverters

#### Before:

```scala
import scala.collection.JavaConversions._
def double(nums: java.util.List[Int]): java.util.List[Int] =
	nums.map(_ * 2)
```

aka:

```scala
import scala.collection.JavaConversions._
def double(nums: java.util.List[Int]): java.util.List[Int] =
  bufferAsJavaList(asScalaBuffer(nums).map(_ * 2))
```

#### After:

```scala
import scala.collection.JavaConverters._
def double(nums: java.util.List[Int]): java.util.List[Int] =
  nums.asScala.map(_ * 2).asJava
```

## Ocaml Modular Implicits

[paper](https://arxiv.org/pdf/1512.01895.pdf)

[repo](https://github.com/ocamllabs/ocaml-modular-implicits)

```scala
def show[T](x : T)(implicit s : Showable[T]): String
trait Showable[T] { def show(x: T): String }
```

```scala
object IntShowable extends Showable [Int] {
	def show(x: Int) = x.toString
}
show(7)(IntShowable)
```

```scala
implicit object IntShowable extends Showable[Int] {
	def show(x: Int) = x.toString
}
show(7)
```

## shapeless
## physics units conversions
## imperative/functional/declarative

## Scala collections
[guide](http://docs.scala-lang.org/overviews/core/architecture-of-scala-collections.html)
"natural types and maximal sharing of implementation code"
“same-result-type” principle:

```scala
   Seq(1,2,3).take(2): Seq[Int]
  List(1,2,3).take(2): List[Int]
Vector(1,2,3).take(2): Vector[Int]
```

![](https://cl.ly/0p3v0p1J3Z2I/Screen%20Shot%202017-05-02%20at%208.59.37%20PM.png)

## enrichment pattern
- rdds
- contexts
- arrowops
- strings

## implicits: picking up where types leave off

```
function computeVelocities(distances, time) {
	var velocities = [];
	for (var i = 0; i < distances.length; i++) {
		var distance = distances[i];
		var velocity = distance / time;
		velocities.push(velocity);
	}
	return velocities;
}
```

```javascript
function computeVelocities(distances, time) {
	return distances.map(distance => distance / time);
}
```

```javascript
var computeVelocities = 
	(distances, time) => 
		distances.map(
		      distance => distance / time
		);
```

```scala
def computeVelocities(
	distances: Seq[Double], 
	time: Double
) = 
	distances.map(_ / time)
```

```scala
implicit class Distance(val d: Double) extends AnyVal

implicit class Time(val t: Double) extends AnyVal

implicit class Velocity(val v: Double) extends AnyVal

implicit def dToV(distance: Distance)(implicit time: Time) = Velocity.make(distance, time)

object Velocity {
	implicit def make(implicit distance: Distance, time: Time): Velocity = 
		Velocity(distance.d / time.t)
}
```

```scala
implicit def convertSeq[T, U](
	ts: Seq[T]
)(implicit 
	fn: T => U
): Seq[U] = 
	ts.map(fn)
```

## value-classes

### normal/tumor

## type-classes
- numeric/spire
- algebra, group/semigroup etc

