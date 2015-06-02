package chunked

import java.io.{File, FileReader}
import java.nio.charset.{StandardCharsets, Charset}
import java.nio.file.{Files, Paths}

import benchmarks.ChunkedGeneratorForBenchmark

import scala.collection.immutable.PagedSeq
import scala.io.Source
import scala.util.parsing.input.{CharSequenceReader, PagedSeqReader}
import scala.collection.JavaConverters._

/**
 * Created by alex on 25.04.15.
 */
object MainInterleaved {

  def interleaved() = {
    /*
    val res = BoundaryTextParser.parseBonjour(new BoundaryReader(NumberParser.number, new CharSequenceReader("\n3\nbon\n4\njour")))
    println(res)


    val jsonRes = JsonBoundaryParser.root(new CharSequenceReader("{\"a\":\"b\"}"))
    println(jsonRes)

    //println("\n2\n{\"\n4\nname\n2\n\":\n1\n\"\n8\nvalue\" }\n0")
    val toParse = "\n5\n{\n  \"\n22\nproduct\": \"Live JSON\"}"
    val jsonResBound = JsonBoundaryParser.root(new BoundaryReader(NumberParser.number, new CharSequenceReader(toParse)))
    println(jsonResBound)
*/
    val res2 = JsonParser.parse(new FileReader(new File("testing_files/demoJSON")))
    println(res2)

    val buf = new PagedSeqReader(PagedSeq.fromReader(Source.fromFile(new File("testing_files/generatedChunkedJSON")).bufferedReader()))

    val res3 = JsonParser.parse(new BoundaryReader(NumberParser.number, buf))

    println(res2.equals(res3))

  }

  def chunkedIntoOneDump() = {
    val buf = new PagedSeqReader(PagedSeq.fromReader(Source.fromFile(new File("testing_files/generatedChunkedJSON")).bufferedReader()))
    val x = DumpChunkedIntoString.parse(buf)

    val res2 = JsonParser.parse(new FileReader(new File("testing_files/demoJSON")))

    val res3 = JsonParser.root(new CharSequenceReader(x))
    println(res2.equals(res3.get))
  }
  def main(args: Array[String]) = {
    //ChunkedGeneratorForBenchmark.generate("testing_files/demoJSON", "testing_files/generatedChunkedJSON", 50, true)
    //interleaved()
  }

}
