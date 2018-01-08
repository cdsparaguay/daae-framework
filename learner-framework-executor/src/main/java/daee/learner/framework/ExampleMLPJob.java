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

// $example on$

import org.apache.spark.SparkFiles;
import org.apache.spark.ml.classification.MultilayerPerceptronClassificationModel;
import org.apache.spark.ml.classification.MultilayerPerceptronClassifier;
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
// $example off$

/**
 * An example for Multilayer Perceptron Classification.
 */
public class ExampleMLPJob {

    public static void main(String[] args) {
        SparkSession spark = SparkSession
                .builder()
                .appName("JavaMultilayerPerceptronClassifierExample")
                .getOrCreate();

        // $example on$
        // Load training data
        String fileName = SparkFiles.get("sample_multiclass_classification_data.txt");
        Dataset<Row> dataFrame = spark.read().format("libsvm").load(fileName);



        // Split the data into train and test
        Dataset<Row>[] splits = dataFrame.randomSplit(new double[]{0.6, 0.4}, 1234L);
        Dataset<Row> train = splits[0];
        Dataset<Row> test = splits[1];

        // specify layers for the neural network:
        // input layer of size 4 (features), two intermediate of size 5 and 4
        // and output of size 3 (classes)
        int[] layers = new int[] {4, 5, 4, 3};

        // create the trainer and set its parameters
        MultilayerPerceptronClassifier trainer = new MultilayerPerceptronClassifier()
                .setLayers(layers)
                .setBlockSize(128)
                .setSeed(1234L)
                .setMaxIter(100);

        // train the model
        MultilayerPerceptronClassificationModel model = trainer.fit(train);
        // compute accuracy on the test set
        Dataset<Row> result = model.transform(test);
        Dataset<Row> predictionAndLabels = result.select("prediction", "label");
        MulticlassClassificationEvaluator evaluator = new MulticlassClassificationEvaluator()
                .setMetricName("accuracy");

        double prediction = evaluator.evaluate(predictionAndLabels);
        System.out.println("Test set accuracy = " + prediction);
        List<String> jsonData = Arrays.asList(
                "{\"id\":10,\"result\": "+prediction+"}");
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
        // $example off$

        spark.stop();
    }
}