package ImageCrawler;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

/**
 * @author Yasser Ganjisaffar <lastname at gmail dot com>
 */

/*
 * IMPORTANT: Make sure that you update crawler4j.properties file and set
 * crawler.include_images to true
 */

public class ImageCrawlController {

        public static void main(String[] args) throws Exception {
                /*if (args.length < 3) {
                        System.out.println("Needed parameters: ");
                        System.out.println("\t rootFolder (it will contain intermediate crawl data)");
                        System.out.println("\t numberOfCralwers (number of concurrent threads)");
                        System.out.println("\t storageFolder (a folder for storing downloaded images)");
                        return;
                }*/
                String rootFolder = "/data/crawl/root";
                int numberOfCrawlers = 7;
                String storageFolder = "/data/crawl/image";

                CrawlConfig config = new CrawlConfig();

                config.setCrawlStorageFolder(rootFolder);

                /*
                 * Since images are binary content, we need to set this parameter to
                 * true to make sure they are included in the crawl.
                 */
                config.setIncludeBinaryContentInCrawling(true);

                String[] crawlDomains = new String[] { "http://uci.edu/" };

                PageFetcher pageFetcher = new PageFetcher(config);
                RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
                RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
                CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
                for (String domain : crawlDomains) {
                        controller.addSeed(domain);
                }

                ImageCrawler.configure(crawlDomains, storageFolder);

                controller.start(ImageCrawler.class, numberOfCrawlers);
        }

}