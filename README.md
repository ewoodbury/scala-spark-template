# Scala Spark Template

A template project for Apache Spark applications using Scala 3.

## Features

- **Scala 3.3.3** - Latest stable Scala 3 version
- **Spark 3.5.1** - Latest Spark version with Scala 2.13 cross-compatibility
- **Assembly plugin** - Create fat JAR for deployment
- **Test setup** - ScalaTest and spark-testing-base (TODO) configured for unit testing
- **Formatting and Linting** - Pre-configured Scalafmt and Scalafix

Note: Spark does not officially support Scala 3. We use cross-compatibility between Scala 2.13 and Scala 3 here to make it work, but I still recommend 2.13 for production use cases. See details in the [Scala 3 with Spark](#scala-3-with-spark) section below.

For a Scala 2.13 version of this template, see [spark-template-scala-2.13](https://github.com/ewoodbury/spark-template-scala-2.13).


## Project Structure

```
├── build.sbt                    # Build configuration
├── project/
│   └── plugins.sbt             # SBT plugins
├── src/
│   ├── main/
│   │   ├── scala/              # Main Scala source files
│   │   └── resources/          # Resources (log4j2.xml, etc.)
│   └── test/
│       ├── scala/              # Test source files
│       └── resources/          # Test resources
```

## Getting Started

### Prerequisites

- Java 17 (recommend [jEnv](https://www.jenv.be/) for managing Java versions)
- SBT 1.x

### Running the Application

1. Compile with `sbt compile`
2. Run tests with `sbt test`
3. Run the sample application with `sbt "runMain com.example.SparkApp"`

### Building for Deployment

Create a fat JAR for cluster deployment:
```bash
sbt assembly
```

The assembled JAR will be created in `target/scala-3.3.3/` and can be submitted to a Spark cluster using `spark-submit`.

## Scala 3 with Spark

This project uses Scala 3 with Spark 3.5, which is built for Scala 2.13. This works due to:

- TASTy (Typed Abstract Syntax Trees) compatibility between Scala 2.13 and Scala 3
- Scala 3's interoperability features
- Using the Scala 2.13-compiled Spark libraries with Scala 3 application code
- Cross-version configuration using `CrossVersion.for3Use2_13`

### Scala 3 Specific Considerations

When working with Spark in Scala 3, you may need to:

1. **Use explicit schemas** instead of relying on automatic type inference for DataFrames
2. **Create DataFrames manually** using `spark.createDataFrame()` with explicit schemas
3. **Be aware of differences** in implicit conversions and type inference

Example of Scala 3 compatible DataFrame creation:
```scala
import org.apache.spark.sql.types._
import org.apache.spark.sql.Row

val schema = StructType(Seq(
  StructField("name", StringType, nullable = false),
  StructField("age", IntegerType, nullable = false)
))

val data = Seq(("Alice", 25), ("Bob", 30))
val rows = data.map { case (name, age) => Row(name, age) }
val df = spark.createDataFrame(spark.sparkContext.parallelize(rows), schema)
```

## Java Compatibility

### Recommended Java Versions
- **Java 17+**: May require additional JVM flags

### Java 17+ Known Issues
If using Java 17 or newer, you may encounter security-related issues when running Spark locally. The project includes necessary JVM flags in the build configuration, but for production deployments, consider using Java 8 or 11.

For local development with Java 17+, you may need to:
1. Use Java 8 or 11 for better compatibility
2. Or accept that some local Spark tests may not work (the application will still compile and run in production)

## Dependencies

- **Spark Core & SQL**: Marked as `provided` to reduce JAR size for cluster deployment
- **Scala XML**: Explicitly added for Scala 3 compatibility
- **ScalaTest**: For unit testing
- **SBT Assembly**: For creating deployable JARs

## Configuration

- Logging is configured via `log4j2.xml` to reduce Spark's verbose output during development
- Spark dependencies are scoped as `provided` for production deployments
- Cross-version compatibility is configured to use Scala 2.13 artifacts with Scala 3
- Assembly merge strategy is configured to handle duplicate files

## Example Usage

See `src/main/scala/SparkApp.scala` for a complete example of:
- Setting up a Spark session
- Creating DataFrames with explicit schemas
- Performing basic transformations
- Proper resource cleanup
