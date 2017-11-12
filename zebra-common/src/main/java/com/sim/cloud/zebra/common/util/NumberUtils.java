package com.sim.cloud.zebra.common.util;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class NumberUtils {

  public static DecimalFormat decimalFormat = new DecimalFormat(".00");

  public static HashMap<Integer, ArrayList<Integer>> standardPartition(ArrayList<Integer> data,
      int setnum) {
    // 结果集
    HashMap<Integer, ArrayList<Integer>> classify = new HashMap<Integer, ArrayList<Integer>>();
    HashMap<Integer, Float> center = new HashMap<Integer, Float>();

    ArrayList<Integer> temp = (ArrayList<Integer>) data.clone();
    Collections.sort(temp);


    float metricOfset = ((float) data.size()) / ((float) setnum);

    for (int i = 0; i < temp.size(); i++) {
      HashMap<Integer, Integer> classdata = new HashMap<Integer, Integer>();
      Integer category = (int) (Math.ceil(((float) i + 0.01) / metricOfset));
      if (classify.containsKey(category)) {
        ArrayList<Integer> subset = classify.get(category);
        subset.add(Integer.valueOf(temp.get(i)));
        classify.put(category, subset);
      } else {
        ArrayList<Integer> subset = new ArrayList<Integer>();
        subset.add(Integer.valueOf(temp.get(i)));
        classify.put(category, subset);
      }

    }


    for (int times = 0; times <= data.size() / 3; times++) {

      for (Map.Entry<Integer, ArrayList<Integer>> h : classify.entrySet()) {
        int category = h.getKey();

        for (int jj = 0; jj < h.getValue().size(); jj++) {
          for (Map.Entry<Integer, ArrayList<Integer>> z : classify.entrySet()) {
            float sum = 0;
            float mean = 0;
            for (int mm = 0; mm < z.getValue().size(); mm++) {
              if ((!(mm == jj && category == z.getKey())) || z.getValue().size() == 1) {
                sum += z.getValue().get(mm);
              }
            }
            if (category == z.getKey() && z.getValue().size() > 1) {
              mean = sum / ((float) z.getValue().size() - 1);
            } else {
              mean = sum / ((float) z.getValue().size());
            }
            center.put(z.getKey(), mean);
          }
          int sub = h.getValue().get(jj);
          float oldcenter = center.get(category);
          int categorynew = h.getKey();
          for (Map.Entry<Integer, Float> m : center.entrySet()) {
            if (Math.abs(sub - m.getValue()) < Math.abs(sub - oldcenter)) {
              categorynew = m.getKey();
            }
          }

          if (categorynew != category) {

            ArrayList<Integer> add = (ArrayList<Integer>) classify.get(categorynew).clone();
            add.add(sub);
            classify.put(categorynew, add);

            ArrayList<Integer> subdata = (ArrayList<Integer>) classify.get(category).clone();
            subdata.remove(jj);
            classify.put(h.getKey(), subdata);
          }
        }
      }
    }
    return classify;

  }

  public static float parseRate(int numerator, int denumerator) {
    float denu = (float) (denumerator * 1.0);
    if (denumerator > 0) {
      return Float.parseFloat(decimalFormat.format(numerator / denu));
    }
    return 0.0f;
  }
  

  public static float parseRate(float numerator, int denumerator) {
    float denu = (float) (denumerator * 1.0);
    if (denumerator > 0) {
      return Float.parseFloat(decimalFormat.format(numerator / denu));
    }
    return 0.0f;
  }
  
  public static float parseRate(float numerator, float denu) {
	    if (denu > 0.0f) {
	      return Float.parseFloat(decimalFormat.format(numerator / denu));
	    }
	    return 0.0f;
	  }
  
  public static int getRateInt(int numerator, int denumerator) {
    float denu = (float) (denumerator * 1.0);
    String res="0.0";
    if (denumerator > 0) {
      res= decimalFormat.format((numerator*100) / denu);
    }
    return new Double(res).intValue();
  }
  
  public static int getRateInt(float numerator, int denumerator) {
	    float denu = (float) (denumerator * 1.0);
	    String res="0.0";
	    if (denumerator > 0) {
	      res= decimalFormat.format((numerator*100) / denu);
	    }
	    return new Double(res).intValue();
	  }
  
  public static String parseRateInt(int numerator, int denumerator) {
    float denu = (float) (denumerator * 1.0);
    if (denumerator > 0) {
     return decimalFormat.format((numerator*100) / denu);
    }
    return "0.0";
  }
  
  public static float formatFloatNum(float mub){
    return Float.parseFloat(decimalFormat.format(mub));
  }
  
  public static String formatFloat(float mub){
	  return decimalFormat.format(mub);
  }

  public static float parseRate(long numerator, long denumerator) {
    float denu = (float) (denumerator * 1.0);
    if (denumerator > 0) {
      return Float.parseFloat(decimalFormat.format(numerator / denu));
    }
    return 0.0f;
  }

  public static float parseFloat(float value, int precision) {
    String pattern = "#.";
    for (int i = 0; i < precision; i++) {
      pattern += "0";
    }
    DecimalFormat df = new DecimalFormat(pattern);
    return Float.parseFloat(df.format(value));
  }

  public static void main(String[] args) {
//    String ss =
//        "43194, 3270, 1785, 3701, 4581, 3798, 22520, 1392, 19322, 1677, 33909, 4000, 2140262, 599035, 37775, 3308, 12534, 1290, 7249, 57555, 7985, 12328";
//    ArrayList<Integer> list = new ArrayList<Integer>();
//    String[] aa = ss.split(",");
//    for (String s : aa) {
//      list.add(Integer.parseInt(s.trim()));
//    }
//    System.out.println(standardPartition(list, 7));

    System.out.println(parseRate(239200, 10000));
  }
}
