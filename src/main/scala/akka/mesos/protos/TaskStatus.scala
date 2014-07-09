package akka.mesos.protos

import akka.util.ByteString
import org.apache.mesos.Protos.{ TaskStatus => PBTaskStatus }
import com.google.protobuf.{ ByteString => PBByteString }
import scala.collection.JavaConverters._

case class TaskStatus(
  taskId: TaskID,
  state: TaskState,
  message: Option[String] = None,
  data: Option[ByteString] = None,
  slaveId: Option[SlaveID] = None,
  executorId: Option[ExecutorID] = None,
  timestamp: Option[Double] = None,
  healthy: Option[Boolean] = None
) {
  def toProtos: PBTaskStatus = {
    val builder =
      PBTaskStatus
        .newBuilder
        .setTaskId(taskId.toProtos)
        .setState(state.toProtos)

      message.foreach(builder.setMessage)
      data.foreach(bs => builder.setData(PBByteString.copyFrom(bs.toArray)))
      slaveId.foreach(id => builder.setSlaveId(id.toProtos))
      executorId.foreach(id => builder.setExecutorId(id.toProtos))
      timestamp.foreach(builder.setTimestamp)
      healthy.foreach(builder.setHealthy)

      builder.build()
  }
}