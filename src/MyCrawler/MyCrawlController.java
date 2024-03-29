package MyCrawler;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class MyCrawlController {

    public static void main(String[] args) throws Exception {
            /*if (args.length != 2) {
                   System.out.println("Needed parameters: ");
                    System.out.println("\t rootFolder (it will contain intermediate crawl data)");
                    System.out.println("\t numberOfCralwers (number of concurrent threads)");
                    return;
            }*/

            /*
             * crawlStorageFolder is a folder where intermediate crawl data is
             * stored.
             */
//            String crawlStorageFolder = args[0];
    	    /* String crawlStorageFolder = "/data/crawl/root";
    	     String storageFolder = "/data/crawl/sandaha";*/
    	     
    	
    	     //Hello Test
    	     String crawlStorageFolder = "/data/crawl/root";
    	     String storageFolder = "/data/crawl/zhubajieseller";

            /*
             * numberOfCrawlers shows the number of concurrent threads that should
             * be initiated for crawling.
             */
//            int numberOfCrawlers = Integer.parseInt(args[1]);
    	    int numberOfCrawlers = 20;

            CrawlConfig config = new CrawlConfig();

            config.setCrawlStorageFolder(crawlStorageFolder);

            /*
             * Be polite: Make sure that we don't send more than 1 request per
             * second (1000 milliseconds between requests).
             */
            config.setPolitenessDelay(1000);

            /*
             * You can set the maximum crawl depth here. The default value is -1 for
             * unlimited depth
             */
            config.setMaxDepthOfCrawling(1);

            /*
             * You can set the maximum number of pages to crawl. The default value
             * is -1 for unlimited number of pages
             */
            config.setMaxPagesToFetch(-1);

            /*
             * Do you need to set a proxy? If so, you can use:
             * config.setProxyHost("proxyserver.example.com");
             * config.setProxyPort(8080);
             * 
             * If your proxy also needs authentication:
             * config.setProxyUsername(username); config.getProxyPassword(password);
             */

            /*
             * This config parameter can be used to set your crawl to be resumable
             * (meaning that you can resume the crawl from a previously
             * interrupted/crashed crawl). Note: if you enable resuming feature and
             * want to start a fresh crawl, you need to delete the contents of
             * rootFolder manually.
             */
            config.setResumableCrawling(false);

            /*
             * Instantiate the controller for this crawl.
             * 
             * 
             */
            
           
            PageFetcher pageFetcher = new PageFetcher(config);
            RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
            RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
            
            
            CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
           
            /*
             * Setting crawlDomains for "shouldvist" pages in MyCrawler��Java
             * 
             */
            
            String[] crawlDomains = new String[] { "http://list.zhubajie.com/weibo/", "http://task.zhubajie.com/" };
            controller.setCustomData(crawlDomains);
             
            /*
             * For each crawl, you need to add some seed urls. These are the first
             * URLs that are fetched and then the crawler starts following links
             * which are found in these pages
             */

            controller.addSeed("http://list.zhubajie.com/weibo/");
            for(int i= 2 ; i<=500;i++)
            	controller.addSeed("http://list.zhubajie.com/weibo/t-p" + String.valueOf(i)+".html");

            
            /*
             * Start the crawl. This is a blocking operation, meaning that your code
             * will reach the line after this only when crawling is finished.
             */
            
            MyCrawler.configure(storageFolder);
            controller.start(MyCrawler.class, numberOfCrawlers);
            System.out.println("Crawler is finished");
    }
}