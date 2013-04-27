package Test;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import edu.uci.ics.crawler4j.util.IO;

import java.util.List;
import java.util.regex.Pattern;
import java.io.File;
import java.util.regex.*;

import ImageCrawler.Cryptography;
//import ImageCrawler.ImageCrawler;

/**
 * @author Yasser Ganjisaffar <lastname at gmail dot com>
 */
public class MyCrawler extends WebCrawler {

        private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g" + "|png|tiff?|mid|mp2|mp3|mp4"
                        + "|wav|avi|mov|mpeg|ram|m4v|pdf" + "|rm|smil|wmv|swf|wma|zip|rar|gz))$");

        /**
         * You should implement this function to specify whether the given url
         * should be crawled or not (based on your crawling logic).
         */
       
       
        private static final Pattern taskPatterns = Pattern.compile("http://www.sandaha.com/weibo/works/", Pattern.CASE_INSENSITIVE);
        private static File storageFolder;
        private static String[] crawlDomains;
        
      /*  public static void configure(String[] crawlDomains, String storageFolderName) {
            MyCrawler.crawlDomains = crawlDomains;*/
        public static void configure(String storageFolderName) {
            //MyCrawler.crawlDomains = crawlDomains;

            storageFolder = new File(storageFolderName);
            if (!storageFolder.exists()) {
                    storageFolder.mkdirs();
            }
    }
        
        @Override
        public void onStart() {
                crawlDomains = (String[]) myController.getCustomData();
        }
        
        @Override
        public boolean shouldVisit(WebURL url) {
                String href = url.getURL().toLowerCase();
//              return !FILTERS.matcher(href).matches() && href.startsWith("http://www.sandaha.com/weibo/");
                if (FILTERS.matcher(href).matches()) {
                    return false;
                 }
                for (String crawlDomain : crawlDomains) {
                    if (href.startsWith(crawlDomain)) {
                            return true;
                    }
                 }
                 return false;
        }

        /**
         * This function is called when a page is fetched and ready to be processed
         * by your program.
         */
        @Override
        public void visit(Page page) {
                int docid = page.getWebURL().getDocid();
                String url = page.getWebURL().getURL();
                
                /*
                 * Only crawl taskPatten urls
                 */
                if(!taskPatterns.matcher(url).find()){
                	return;
                }
                		
                Pattern fname= Pattern.compile("/weibo/works/", Pattern.CASE_INSENSITIVE);
                String domain = page.getWebURL().getDomain();
                String path = page.getWebURL().getPath();
                String subDomain = page.getWebURL().getSubDomain();
                String parentUrl = page.getWebURL().getParentUrl();
                
                //remove "/weibo/works/"
                Matcher fmatcher = fname.matcher(path);
                String filename = fmatcher.replaceAll("");
                System.out.println(filename);
                
                //remove '/'
                
                Pattern fname1 = Pattern.compile("/");
                Matcher f2matcher = fname1.matcher(filename);
                String ffname = f2matcher.replaceAll("_");
                System.out.println(ffname);
                
                
                

                System.out.println("Docid: " + docid);
                System.out.println("URL: " + url);
                System.out.println("Domain: '" + domain + "'");
                System.out.println("Sub-domain: '" + subDomain + "'");
                System.out.println("Path: '" + path + "'");
                System.out.println("Parent page: " + parentUrl);

                //String extension = url.substring(url.lastIndexOf("."));
//                String hashedName = Cryptography.MD5(url) + ".html";
                String hashedName = ffname + String.valueOf(docid)+ ".html";
                
                if (page.getParseData() instanceof HtmlParseData) {
                        HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
                        String text = htmlParseData.getText();
//                      IO.writeBytesToFile(text.getBytes(),storageFolder.getAbsolutePath()+"/"+hashedName);
//                      System.out.println("Suessfully saved file:" + storageFolder.getAbsolutePath()+"/"+hashedName);
                        String html = htmlParseData.getHtml();
                        IO.writeBytesToFile(html.getBytes(),storageFolder.getAbsolutePath()+"/"+hashedName);
                        System.out.println("Sucessfully saved file:" + storageFolder.getAbsolutePath()+"\\"+ hashedName);
                        
                        List<WebURL> links = htmlParseData.getOutgoingUrls();

                        System.out.println("Text length: " + text.length());
                        System.out.println("Html length: " + html.length());
                        System.out.println("Number of outgoing links: " + links.size());
                }

                System.out.println("=============");
        }
}