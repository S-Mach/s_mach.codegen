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
       ;it1i ,,,:::;;;::1tti      s_mach.codetools
         .t1i .,::;;; ;1tt        Copyright (c) 2016 S-Mach, Inc.
         Lft11ii;::;ii1tfL:       Author: lance.gatlin@gmail.com
          .L1 1tt1ttt,,Li
            ...1LLLL...
*/
package s_mach.codetools.impl

import s_mach.codetools.reflectPrint.{ReflectPrintFormat, ReflectPrint}

import scala.language.higherKinds
import scala.reflect.ClassTag

class ReflectPrintTraversable[A, M[AA] <: Traversable[AA]](implicit
  pA:ReflectPrint[A],
  mClassTag:ClassTag[M[_]]
) extends SimpleReflectPrintImpl[M[A]] {
  val className = mClassTag.runtimeClass.getSimpleName
  override def print(
    ma: M[A]
  )(implicit
    fmt: ReflectPrintFormat
  ): String = {
    if(ma.isEmpty) {
      s"$className.empty"
    } else {
      s"$className(${fmt.newSection { sectionFmt =>
        ma
          .map(a => pA.printApply(a)(sectionFmt))
          .mkString(s",${sectionFmt.newLine}")
      }})"
    }
  }
}
