package com.example

import org.apache.spark.sql.types.*
import org.apache.spark.sql.{Row, SparkSession}

object SparkApp:
  def main(args: Array[String]): Unit =
    val spark = SparkSession
      .builder()
      .appName("Scala Spark Template")
      .master("local[*]") // Remove this line when running on cluster
      .getOrCreate()

    try
      // Example: Create a simple DataFrame
      val data = Seq(
        ("Alice", 25),
        ("Bob", 30),
        ("Charlie", 35),
      )

      // Define schema explicitly for Scala 3 compatibility
      val schema = StructType(
        Seq(
          StructField("name", StringType, nullable = false),
          StructField("age", IntegerType, nullable = false),
        ),
      )

      // Convert to Rows and create DataFrame
      val rows = data.map { case (name, age) => Row(name, age) }
      val df = spark.createDataFrame(spark.sparkContext.parallelize(rows), schema)

      println("Sample DataFrame:")
      df.show()

      // Example: Simple transformation
      val adults = df.filter(df("age") >= 18)
      println("Adults:")
      adults.show()

    finally spark.stop()
