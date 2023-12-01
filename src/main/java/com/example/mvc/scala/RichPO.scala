package com.example.mvc.scala

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.kafka.clients.producer.ProducerRecord
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.util.concurrent.{ListenableFuture, ListenableFutureCallback}

/**
 * 给Object扩展常用方法（https://blog.csdn.net/xxyy888/article/details/5797734）
 */
object RichPO {
  /**
   * PO数据库的充血模型
   */
  implicit class POExtensions[T](obj: T)(implicit mapper: BaseMapper[T]) {
    ///插入
    def insert: Int = {
      mapper.insert(obj)
    }

    /**
     * 插入PO
     */
    def ++(): Int = insert

    /**
     * 更新PO
     */
    def >>(): Int = update

    def update: Int = {
      mapper.updateById(obj)
    }

    def delete: Int = {
      mapper.deleteById(POUtils.getTableIdValue(obj))
    }

    /**
     * 删除PO
     */
    def --(): Int = delete

    def insertOrUpdate: Int = {
      val domain = mapper.selectById(POUtils.getTableIdValue(obj))
      if (domain != null) mapper.updateById(obj) else mapper.insert(obj)
    }

    /**
     * 插入或者更新
     *
     * @param mapper
     * @return
     */
    def +>(): Int = insertOrUpdate

    def findById: T = {
      mapper.selectById(POUtils.getTableIdValue(obj))
    }

    /**
     * 根据条件查询，支持PO的等于查询，也支持QueryWrapper的·自定义条件查询
     *
     * @param qwInput
     * @return
     */
    def findByConditon: List[T] = {
      import scala.collection.JavaConverters._
      mapper.selectList(new QueryWrapper(obj)).asScala.toList
    }

    def selectCount: Long = {
      mapper.selectCount(new QueryWrapper(obj))
    }

  }


  /**
   * 数据库查询条件的扩展能力
   */
  implicit class QWExtensions[T](qwInput: QueryWrapper[T])(implicit mapper: BaseMapper[T]) {

    /**
     * 根据条件查询，支持PO的等于查询，也支持QueryWrapper的·自定义条件查询
     *
     * @param qwInput
     * @return
     */
    def findByConditon: List[T] = {
      import scala.collection.JavaConverters._
      mapper.selectList(qwInput).asScala.toList
    }

    def selectCount: Long = {
      mapper.selectCount(qwInput)
    }


    def findAll: List[T] = {
      import scala.collection.JavaConverters._
      mapper.selectList(null).asScala.toList
    }

  }

  /**
   * MQ的扩展能力
   */
  implicit class MQExtensions[T](obj: T)(implicit kafkaTemplate: KafkaTemplate[String, String]) {

    /**
     * 发送kafka消息
     *
     * @param topic topic
     */
    def sendMQ(topic: String): ListenableFuture[SendResult[String, String]] = {
      sendMQ(topic, null)
    }

    def sendMQ(topic: String, key: String): ListenableFuture[SendResult[String, String]] = {
      val ret = kafkaTemplate.send(topic, key, JSONTools.toJson(obj))

      ret.addCallback(new ListenableFutureCallback[SendResult[String, String]] {
        override def onSuccess(result: SendResult[String, String]): Unit = POUtils.log("MQ send success:{}", result)

        override def onFailure(ex: Throwable): Unit = {
          POUtils.warn("MQ send failure:{}", obj.toString, ex)

          // 放到cache队列中
          POUtils.cacheSendFailMQ(topic, key, JSONTools.toJson(obj))
        }
      })

      ret
    }


    def sendMQAndGet(topic: String): SendResult[String, T] = {
      val ret = sendMQ(topic).get()

      // 如果是String类型的直接返回
      if (obj.isInstanceOf[String]) return ret.asInstanceOf[SendResult[String, T]]

      val record = ret.getProducerRecord;

      val newRecord: ProducerRecord[String, T] = new ProducerRecord[String, T](record.topic, record.key, JSONTools.parser(record.value, obj.getClass))

      new SendResult[String, T](newRecord, ret.getRecordMetadata)
    }


  }
}
