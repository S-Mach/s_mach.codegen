/*
                    ,i::,
               :;;;;;;;
              ;:,,::;.
            1ft1;::;1tL
              t1;::;1,
               :;::;               _____       __  ___              __
          fCLff ;:: tfLLC         / ___/      /  |/  /____ _ _____ / /_
         CLft11 :,, i1tffLi       \__ \ ____ / /|_/ // __ `// ___// __ \
         1t1i   .;;   .1tf       ___/ //___// /  / // /_/ // /__ / / / /
       CLt1i    :,:    .1tfL.   /____/     /_/  /_/ \__,_/ \___//_/ /_/
       Lft1,:;:       , 1tfL:
       ;it1i ,,,:::;;;::1tti      s_mach.codetools.play_json
         .t1i .,::;;; ;1tt        Copyright (c) 2016 S-Mach, Inc.
         Lft11ii;::;ii1tfL:       Author: lance.gatlin@gmail.com
          .L1 1tt1ttt,,Li
            ...1LLLL...
*/
package s_mach.codetools

object ExampleUsage {

  object ValueClass {
    import s_mach.codetools._

    implicit class Name(
      val underlying: String
    ) extends AnyVal with IsValueClass[String]

    // codetools standardizes IsValueClass.toString to underlying.toString
    def printName(n: Name) = println(n)
    def printString(s: String) = println(s)
    def printStrings(ss: List[String]) = println(ss)

    // Scala value-class auto wraps String => Name
    printName("Gary Oldman")
    // codetools adds implicit conversion from Name => String
    printString(Name("Gary Oldman"))

    // No implicit conversion from M[Name] => M[String] since it would hide copying
    val names = List(Name("Gary Oldman"),Name("Christian Bale"),Name("Philip Seymour Hoffman"))
    printStrings(names.map(_.underlying))
  }

  object DistinctTypeAlias {
    import s_mach.codetools._

    trait NameTag
    type Name = String with NameTag with IsDistinctTypeAlias[String]
    import scala.language.implicitConversions
    @inline implicit def Name(name: String) = name.asInstanceOf[Name]

    def printName(n: Name) = println(n)
    def printString(s: String) = println(s)
    def printStrings(ss: List[String]) = println(ss)

    // implicit def above provides trivial conversion String => Name
    printName("Gary Oldman")
    // No conversion needed since Name is an String
    printString(Name("Gary Oldman"))

    // Covariance of List allows List[Name] to be upcast to List[Int] (no copying)
    val names = List(Name("Gary Oldman"),Name("Christian Bale"),Name("Philip Seymour Hoffman"))
    printStrings(names)
  }


}