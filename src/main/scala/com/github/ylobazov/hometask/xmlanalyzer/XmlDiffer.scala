package com.github.ylobazov.hometask.xmlanalyzer

import java.io.File

import org.jsoup.Jsoup
import org.jsoup.nodes.Element

import scala.annotation.tailrec
import scala.collection.JavaConverters._
import scala.util.Try


object XmlDiffer {

  private val CharsetName = "utf8"

  private type AttributeKeyValue = (String, String)

  def findElementInDiff(params: InputParams): Either[String, List[ItemScore]] = {
    for {
      origin <- readFile(params.origin)
      targetFeatures <- findTargetFeatures(params.idToFind, origin)
      diff <- readFile(params.diff)
      similarities <- findByTargetFeatures(targetFeatures, diff)
    } yield similarities
  }

  private def readFile(file: File): Either[String, Element] = {
    Try(Jsoup.parse(file, CharsetName, file.getAbsolutePath))
      .toEither.left.map(_.getMessage)
  }

  private def findTargetFeatures(id: String, origin: Element): Either[String, Seq[AttributeKeyValue]] = {
    Option(origin.getElementById(id)) match {
      case Some(target) =>
        val attributes = target.attributes().asList().asScala.map(attr => attr.getKey -> attr.getValue).toList
        Right(attributes)
      case None => Left(s"Unable to find element with id=$id")
    }
  }

  private def findByTargetFeatures(features: Seq[AttributeKeyValue], diff: Element): Either[String, List[ItemScore]] = {
    @tailrec
    def iter(attributes: Seq[AttributeKeyValue], elem: Element, res: Seq[ItemScore]): Seq[ItemScore] = {
      attributes match {
        case Nil => res
        case (key, value) :: tail =>
          val elements = elem.getElementsByAttributeValue(key, value).asScala
            .map(elem => ItemScore(findPath(elem), 1))
          iter(tail, elem, res ++ elements)
      }
    }

    val scores = Try {
      iter(features, diff, Nil)
        .groupBy(_.path)
        .map { case (key, occurrences) => ItemScore(key, occurrences.map(_.hits).sum) }
        .toList
        .sortBy(_.hits)(Ordering[Int].reverse)
    }.toEither.left.map(_.getMessage)

    scores
  }


  private def findPath(elem: Element): String = {
    @tailrec
    def iter(elem: Option[Element], res: List[String]): String = {
      elem match {
        case None => res.mkString(">")
        case Some(e) => iter(Option(e.parent()), e.nodeName() :: res)
      }
    }

    iter(Some(elem), Nil)
  }

  private def findAttributes(element: Element): List[AttributeKeyValue] = {
    element.attributes().asList().asScala.map(attr => attr.getKey -> attr.getValue).toList
  }

}
