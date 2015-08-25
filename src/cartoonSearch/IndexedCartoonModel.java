package cartoonSearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

/*
 * This class is mainly used for searching the cartoon with the given search 
 * parameter from the target web site and get the picture URL to return as 
 * a part of the result.jsp view. The difference of the cartoon displayed on 
 * desktop or phone is also addressed here in the indexedCartoonSize method.
 * @author jiananjiang
 */
public class IndexedCartoonModel {
    private String pictureTag;
    private String pictureURL;
    
    /*
     * The method is used for getting the picture URL of the required category
     * @param searchTag the category to search for
     */
    public void doIndexedSearch(String searchTag) {
         pictureTag = searchTag;
         String response = "";
         
         
        try {
        	
            // Create a URL for the desired page            
            URL url = new URL("http://thisisindexed.com/category/"+searchTag + "/");
            // Create an HttpURLConnection.  This is useful for setting headers.
            HttpURLConnection connection=(HttpURLConnection)url.openConnection();
            //connection.addRequestProperty("User-agent", "Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2049.0 Safari/537.36");
            // Read all the text returned by the server
            BufferedReader in = new BufferedReader
                    (new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String str;
            while ((str = in.readLine()) != null) {
                response += str;
            }
            in.close();
            
            System.out.println(response);
        } catch (IOException e) {
            
        }
       
        // Create an integerArrayList to store all the pictureURLs from one category
        ArrayList <String> picSources=new ArrayList <String>();
        int temp = 0;
        // find the picture URL to scrape and add all of them in the picSources ArrayList
        for(int i=0;i<response.length();i++){
            
            int startIndex=response.indexOf("src=\"http://thisisindexed.com/wp-content/uploads",temp);
            //When there are still picture URLs to be scraped, add it to picSources
            if(startIndex>=0){
                int endIndex=response.indexOf("\"", startIndex+5);
                //get the whole URL for one picture
                String pic=response.substring(startIndex,endIndex+1);
                picSources.add(pic);
                temp = endIndex;
            }
             
        }

        Random r = new Random();
        if(picSources.isEmpty()){
            pictureURL = "";
        }
        else
        { 
            //Randomly choose a picture from the collection of picture URLs
            //If a picture is not end with ".jpg\"", or the picture is the Jeremy Bentham
            //9 Pleasures of Stimulation image, the loop will continue unless it meets the 
            //requirement
            do{
               pictureURL=picSources.get(r.nextInt(picSources.size()));
                
            }while((!pictureURL.endsWith(".jpg\""))||
                    pictureURL.endsWith("2012/12/stimulation001-282x380.jpg\""));
     
        }
    }
    /*
     * The method is used for return the picture URL which is fit for phone or 
     * laptop
     * @param picsize the type of device
     * @return the picture URL for the corresponding device
     */
   public String indexedCartoonSize(String picsize) {
        
        //The URL for the smaller size picture which is fit for the phone screen is the
        //URL scraped directly from the webpage for that category.
        //For example, src="http://thisisindexed.com/wp-content/uploads/2014/09/card4418-380x227.jpg-380x227.jpg"
        //is the smaller size of the "What a strange phrase" cartoon.
        if(picsize.equals("mobile")){
            return pictureURL;
        }
        //The URL for the larger size picture is derived by cutting the part
        //after the dash before ".jpg". 
        //For example, http://thisisindexed.com/wp-content/uploads/2014/09/card4418-380x227.jpg
        //is the URL for the cartoon of "What a strange phrase" fit for laptop
        else{
            int finalDash = pictureURL.lastIndexOf("-");
            System.out.println("finalDash"+finalDash);
            int finalPoint = pictureURL.lastIndexOf(".");
            System.out.println("finalPoint"+finalPoint);
            return pictureURL.substring(0,finalDash)+pictureURL.substring(finalPoint);
        }
       
    }
    /*
     * This method is used for return the category.
     * @return the category
     */
    public String getPictureTag() {
        return (pictureTag);
    }
   
}
