package com.github.ylobazov.hometask.xmlanalyzer

import java.io.File

case class InputParams(
                        origin: File,
                        diff: File,
                        idToFind: String
                      )