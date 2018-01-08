
/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package daee.learner.framework;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
// $example on$
import java.util.*;

import org.apache.spark.api.java.JavaDoubleRDD;
import org.apache.spark.mllib.linalg.Matrix;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.stat.Statistics;
import org.apache.spark.sql.*;

// $example off$

public class ExampleJob {
    public static void main(String[] args) throws InterruptedException {


        SparkSession spark = SparkSession
                .builder()
                .appName("Java Spark SQL data sources example")
//                .master("spark://51.15.53.198:6066")
              //  .config("spark.jars", "ftp://dengue:dengue@51.15.53.198/postgresql-9.1-901-1.jdbc4.jar")
                .getOrCreate();
        JavaSparkContext jsc = new JavaSparkContext(spark.sparkContext());

        // $example on$
        JavaDoubleRDD seriesX = jsc.parallelizeDoubles(
                Arrays.asList(1.0, 2.0, 3.0, 3.0, 5.0));  // a series

        // must have the same number of partitions and cardinality as seriesX
        JavaDoubleRDD seriesY = jsc.parallelizeDoubles(
                Arrays.asList(11.0, 22.0, 33.0, 33.0, 555.0));

        // compute the correlation using Pearson's method. Enter "spearman" for Spearman's method.
        // If a method is not specified, Pearson's method will be used by default.
        Double correlation = Statistics.corr(seriesX.srdd(), seriesY.srdd(), "pearson");
        System.out.println("Correlation is: " + correlation);
        // note that each Vector is a row and not a column
        JavaRDD<Vector> data = jsc.parallelize(
                Arrays.asList(
                        Vectors.dense(1.0, 10.0, 100.0),
                        Vectors.dense(2.0, 20.0, 200.0),
                        Vectors.dense(5.0, 33.0, 366.0)
                )
        );

        // calculate the correlation matrix using Pearson's method.
        // Use "spearman" for Spearman's method.
        // If a method is not specified, Pearson's method will be used by default.
        Matrix correlMatrix = Statistics.corr(data.rdd(), "pearson");


        List<String> jsonData = Arrays.asList(
                "{\"id\":2,\"result\": 666}");
        Dataset<String> anotherPeopleDataset = spark.createDataset(jsonData, Encoders.STRING());
        Dataset<Row> anotherPeople = spark.read().json(anotherPeopleDataset);
        anotherPeople.show();
        Properties p = new Properties();
        p.setProperty("user","postgres");
        p.setProperty("password","postgres");
        anotherPeople.write()
                .option("driver","org.postgresql.Driver")
                .mode("append")
                .jdbc("jdbc:postgresql://51.15.53.198:8502/postgres","public.test", p);

        System.out.println("------------------------ CORRELACION -------------"+correlMatrix.toString());
        jsc.stop();


    }

    private static void runJdbcDatasetExample(Dataset<Row> toSave) {
        // $example on:jdbc_dataset$
        // Note: JDBC loading and saving can be achieved via either the load/save or jdbc methods
        // Loading data from a JDBC source



    }

}