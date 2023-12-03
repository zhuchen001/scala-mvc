package com.example.mvc.demo

import com.example.mvc.scala.RichObject._

object App {
  def main(args: Array[String]): Unit = {
    println("Hello")

    val str = "zhuzhu"

    str.foreach(c => println(c))

    val ret = (1 to 9).reduce(_ + _)

    println(ret)

    // 先根据key进行分组（返回一个map，key是分组id，value是分组后的字符串列表）
    // 然后通过mapValues，对value进行处理，每个value作为输入得到新的value
    val dd = str.map(x => (x, x + "a")).groupBy(_._1).mapValues(_.map(_._2).reduce(_ + _))

    println(dd)

    val list = List(("key1", 1), ("key2", 2), ("key1", 3), ("key2", 4))

    val mergedMap = list.groupBy(_._1).mapValues(_.map(_._2).sum)

    println(mergedMap)

    val domain =  new UserDomain().setId("123").setName("zhuzhu").setAge(20)

    println(domain.toJson)

    val dd1 = null.asInstanceOf[String]

    println(dd1)

    f7(println(_))

    println((xx?).isEmpty)

    //1. 定义一个列表，包含1-10的数字
    val list1 = (1 to 10).toList
    //核心: 通过偏函数结合map使用, 来进行模式匹配
    val list2 = list1.map {
      //2 请将1-3的数字都转换为[1-3]
      case x if x >= 1 && x <= 3 => "[1-3]"
      //3 请将4-8的数字都转换为[4-8]
      case x if x >= 4 && x <= 8 => "[4-8]"
      //4 将其他的数字转换为(8-*]
      case _ => "(8-*]"
    }
    //5. 打印结果.
    println(list2)

  }


  def xx : String = {
    null
  }

  def f7(f: (Int) => Unit) = {
    f(1)
  }

}

