/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.mahout.utils.vectors.text;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
import org.apache.mahout.math.SparseVector;

/**
 * Converts a document in to a SparseVector
 */
public class PartialVectorMerger extends MapReduceBase implements
    Reducer<Text,SparseVector,Text,SparseVector> {
  
  @Override
  public void reduce(Text key,
                     Iterator<SparseVector> values,
                     OutputCollector<Text,SparseVector> output,
                     Reporter reporter) throws IOException {
    
    SparseVector vector =
        new SparseVector(key.toString(), Integer.MAX_VALUE, 10);
    while (values.hasNext()) {
      SparseVector value = values.next();
      value.addTo(vector);
    }
    output.collect(key, vector);
    
  }
  
}
