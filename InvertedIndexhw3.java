himport java.io.IOException;
import java.util.StringTokenizer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class InvertedIndexhw3 {

	
	
  public static class TokenizerMapper extends Mapper<Object, Text, Text, Text>{


    private Text word = new Text();
    Text docID = new Text(); 

    public void map(Object key, Text value, Context context) 
    		throws IOException, InterruptedException {
    	
     	     String line = value.toString();
		
	    StringTokenizer tk = new StringTokenizer(line);
	     String docIDstring = tk.nextToken();
	    docID = new Text(docIDstring);	
	     //get the document ID first from the first token

	    //clean the input data  : 1.remove punctuations 2.remove digits 3.convert to lower case
             line = line.replaceAll("\\p{Punct}"," ");
	     line = line.replaceAll("\\d"," ");
	     line = line.toLowerCase();

  	     StringTokenizer itr = new StringTokenizer(line);
         
	       while (itr.hasMoreTokens()) {
			 word.set(itr.nextToken());
	         	 context.write(word, docID);
	       }
    }
  }

  public static class IntSumReducer extends Reducer<Text,Text,Text,Text> {
  

    public void reduce(Text keyword, Iterable<Text> docIDList, Context output) 
    		throws IOException, InterruptedException {
      
    	HashMap<String,Integer> hashtable = new HashMap<String,Integer>();
    	  Iterator<Text> itr = docIDList.iterator();
    	   int count = 0;
    	   String docID = new String();
    	   
    	   while (itr.hasNext()) {
    		   docID = itr.next().toString();
	 	 
		    if(hashtable.containsKey(docID)){
		    	     count = (hashtable.get(docID));
		    	     count += 1;
		    	     hashtable.put(docID, count);	
	    	    }else{
		   	hashtable.put(docID, 1);
		    }
    	   }
    	   
    	   StringBuffer buf = new StringBuffer("");
	   for(Map.Entry<String, Integer> h: hashtable.entrySet())    
		buf.append(h.getKey() + ":" + h.getValue() + "\t");   
	  
		  
	   Text optext = new Text(buf.toString());
    	   output.write(keyword, optext);
   
    }
  }

  public static void main(String[] args) throws Exception {
	    Configuration conf = new Configuration();
	    Job job = Job.getInstance(conf, "Inverted Index");
	    
	    job.setJarByClass(InvertedIndexhw3.class);
	    job.setMapperClass(TokenizerMapper.class);
	    job.setReducerClass(IntSumReducer.class);
	    job.setOutputKeyClass(Text.class);
	    job.setOutputValueClass(Text.class);
	    
	    FileInputFormat.addInputPath(job, new Path(args[0]));
	    FileOutputFormat.setOutputPath(job, new Path(args[1]));
	    
	    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}


