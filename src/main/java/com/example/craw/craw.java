package com.example.craw;

import java.io.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class craw {	
	public static void main(String[] args) throws NullPointerException{
		try{
			
		String url;	
		String category="";
		String location="";
		String province="";
		String[] RowKey = new String[1000];
		String[] shopName = new String[1000];
		String[] shopTel = new String[1000];
		String[] shopAddress = new String[1000];
		java.text.NumberFormat nf = new java.text.DecimalFormat("0000");
		
		System.out.println("Please input your category and location and Province...\n");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));    
		String stream = br.readLine();
		String line[] = stream.split(",");
		category = line[0];
    	location = line[1];
    	province = line[2];	
    	
    	String Filename = category+" "+location+" "+province;
    	FileWriter fw = new FileWriter("F:\\"+Filename+".csv");
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write("Rowkey"+","+"Category"+","+"Name"+","+"Tel"+","+"Province"+","+"Address"+"\n");
		
		
    	for(int k=0;k<51;k++){
    		url = "http://www.yellowbook.com/yellow-pages/?what=".concat(category).concat("&where=").concat(location).concat("%2c+").concat(province).concat("&sort=alphabet&page=")+String.valueOf(k+1);
        	Document doc = Jsoup.connect(url).get();
        	for(int i=1;i<21;i++){
        		int j=0;
    			String shopNameTemp="";
    			String shopAddressTempA="";
    			String shopAddressTempB="";
    			String shopAddressTempC="";
    			String shopAddressTempD="";
    			String shopTelTemp="";   			
    			String divName = "divInAreaSummary_"+String.valueOf(i);			
    			Elements node = doc.select("li[id="+divName);
 //=================================================================================================================================================   			
    			//After search a correct node, use select will appear tag, but we can use Jsoup.parse(html).text() to remove the target tag
    			shopNameTemp = node.first().select("a[class=fn]").text();

    			shopAddressTempA = node.first().select("span[class=street-address]").text();
    			shopAddressTempB = node.first().select("span[class=locality]").text();
    			shopAddressTempC = node.first().select("span[class=region]").text();
    			shopAddressTempD = node.first().select("span[class=postal-code]").text();
    			
    			shopTelTemp= node.first().select("div[class=call phone-number]").text();
  //=================================================================================================================================================  				
    			//System.out.println("Name  "+ shopNameTemp);
    			//System.out.println("Address   "+shopAddressTempA+", "+shopAddressTempB+" "+shopAddressTempC+" "+shopAddressTempD);
    			//System.out.println("Tel   "+shopTelTemp);
 //==================================================================================================================================================			
    			shopName[j] = shopNameTemp;
    			shopTel[j] = shopTelTemp;
    			shopAddress[j] = shopAddressTempA+" "+shopAddressTempB+" "+shopAddressTempC+" "+shopAddressTempD;  
    			bw.write(RowKey[j]+","+category+","+shopName[j]+","+shopTel[j]+","+province+","+shopAddress[j]+"\n");
    			System.out.println("Name  "+ shopName[j]);
    			System.out.println("Address   "+shopAddress[j]);
    			System.out.println("Tel   "+shopTel[j]);  			  
    			RowKey[j] = province+"|"+category+"|"+"AA"+nf.format(j+1);
				System.out.println(shopName[j]+" "+shopTel[j]+" "+shopAddress[j]);					
    			j++;
    			bw.flush();			
    		}       	
    	}	  	
      
      br.close();
      bw.close();
		}catch(IOException e){e.printStackTrace();}
    	
	}	
}
