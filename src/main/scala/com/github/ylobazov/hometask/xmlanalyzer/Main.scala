package com.github.ylobazov.hometask.xmlanalyzer

import java.io.File

object Main extends App {

  val Usage = "Expected program arguments: <input_origin_file_absolute_path> <input_other_sample_file_absolute_path> <element_id_to_find>"

  val result = process()
  println(result)

  def process(): String = {
    parseArgs.flatMap(XmlDiffer.findElementInDiff) match {
      case Left(error) => error
      case Right(elems) =>
        elems match {
          case Nil => "Target element is not found in diff"
          case head :: tail =>
            val others = if (tail.nonEmpty) {
              s"Other similarities:\n\t" +
              tail.map(e => s"[path]=${e.path}, [score]=${e.hits}").mkString("\n\t")
            } else ""
            val target = s"Target element found in diff. [path]=${head.path}, [score]=${head.hits}. \n\t"
            target + others
        }
    }
  }

  def parseArgs: Either[String, InputParams] = {
    args.toList match {
      case origin :: diff :: id :: _ => Right(InputParams(new File(origin), new File(diff), id))
      case inputFile => Left(Usage)
    }
  }

}


