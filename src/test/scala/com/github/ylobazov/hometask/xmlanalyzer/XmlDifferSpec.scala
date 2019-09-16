package com.github.ylobazov.hometask.xmlanalyzer

import java.io.File

import org.scalatest.FlatSpec

import scala.io.Source

class XmlDifferSpec extends FlatSpec {

  it should "find similarities in diff" in {
    val path =  new File("src/test/resources/samples").getAbsolutePath()

    val params = InputParams(
      new File(path + "/sample-0-origin.html"),
      new File(path + "/sample-1-evil-gemini.html"),
      "make-everything-ok-button"
    )

    val res = XmlDiffer.findElementInDiff(params)


    assert(true)
  }


}
