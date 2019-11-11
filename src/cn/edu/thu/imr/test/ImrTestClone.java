package cn.edu.thu.imr.test;


import cn.edu.thu.imr.IMR;
import cn.edu.thu.imr.entity.TimeSeries;
import cn.edu.thu.imr.util.Assist;
import java.util.ArrayList;
import java.io.FileWriter;  

/**
 * Created by Stoke on 2017/10/7.
 * E-mail address is zaqthss2009@gmail.com
 * Copyright © Stoke. All Rights Reserved.
 *
 * @author Stoke
 */
public class ImrTestClone {

	
	static double getLastRepairValueOnCell(String nameCell,double delta, int maxNumIterations) {
		
		String inputFileName = nameCell;
		 
		Assist assist = new Assist();
	    String splitOp = ",";

	    TimeSeries dirtySeries = assist.readData(inputFileName, 1, splitOp);
	    TimeSeries labelSeries = assist.readData(inputFileName, 2, splitOp);
	    TimeSeries truthSeries = assist.readData(inputFileName, 3, splitOp);
	    ArrayList<Boolean> labelList = assist.readLabel(inputFileName, 4, splitOp);
	    
	    
	    
//	    System.out.println(dirtySeries.getTimeseries().get(0).getVal());
//	    double rmsDirty = assist.calcRMS(truthSeries, dirtySeries, labelList);
//	    System.out.println("Dirty RMS error is " + rmsDirty);

	    int p = 1;
//	    double delta = Double.parseDouble(args[0]);
//	    int maxNumIterations = Integer.parseInt(args[1]);

	    
	 

	    IMR imr = new IMR();
	    TimeSeries resultSeries =
	        imr.mainIMR(dirtySeries, labelSeries, labelList, p, delta, maxNumIterations);

	    double rms = assist.calcRMS(truthSeries, resultSeries, labelList);

//	    System.out.println("RMS error is " + rms);
//	    
//	    System.out.println("resultSeries is " + resultSeries);
	    
	    int lengthResultSeries = resultSeries.getLength();
	    
	    return resultSeries.getTimeseries().get(lengthResultSeries-1).getVal();
	    
	  }
	  

	static String[] createFileName(String[] listAttr) {
		int size = 100*(listAttr.length);
		String[] res = new String[size];
		int index = 0;
		for (int i = 1; i<= 100; i++) {
			
			for (int j=0; j<listAttr.length; j++) {
				String temp = Integer.toString(i).concat("_").concat(listAttr[j]);
				
				res[index] = temp;
				index += 1;
			}
		}
		return res;
		
	}
	public static void writeFile() {
		try{    
	           FileWriter fw=new FileWriter("testout.txt");  
	           for(int i=0; i<10; i++) {
	        	   fw.write(String.valueOf(i));
	        	   fw.write("\n");
	           }
	            
	           fw.close();    
	          }catch(Exception e){System.out.println(e);}    
	          System.out.println("Success...");    
	}
  public static void main(String[] args) {
	  
	  String[] listAttr = new String[] {"WT", "LDL", "HDL", "HR", "DBP", "SBP", "CVP", "RR", "SpO2", "TMP", "ABE", "ACO2", "APH", "Hb", "RBC", "RBCF", "WBC", "MONO", "EOS", "LY", "RDW", "TC"};
	  
	  String[] res = createFileName(listAttr);
	  
	  // ---------***** EXPERIMENTS *********-------------
	  
	  // delta = 2.5, maxNumIterations = 1000 -> 1min07secs
	  // delta = 3.5, maxNumIterations = 800 -> 4d9secs41ms
	  
	  // ---------***** EXPERIMENTS *********-------------
	  
	  try{    
          FileWriter fw=new FileWriter("outputRepairAllCellsDefault.txt");  
          
          
//          double delta = Double.parseDouble(args[0]);
//          int maxNumIterations = Integer.parseInt(args[1]);	
          
          double delta = 0.1;
          int maxNumIterations = 200;
          
          for (int i=0; i<res.length; i++) {
        	  double repairVal = getLastRepairValueOnCell(res[i].concat(".txt"),delta,maxNumIterations);    		 
    		  fw.write(res[i].concat("_").concat(String.valueOf(repairVal)));
    		  fw.write("\n");
    		  System.out.println(String.valueOf(repairVal).concat(" - Cell : ").concat(String.valueOf(i)));
          }
    		 
           
          fw.close();    
         }catch(Exception e){System.out.println(e);}    
         System.out.println("Success...");    
	 
	  
//	  System.out.println("asd");
	  
  }

}
