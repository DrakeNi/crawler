package localdata;

import org.apache.http.HttpStatus;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.fetcher.PageFetchResult;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.parser.ParseData;
import edu.uci.ics.crawler4j.parser.Parser;
import edu.uci.ics.crawler4j.url.WebURL;


/**
 * This class is a demonstration of how crawler4j can be used to download a
 * single page and extract its title and text.
 */
public class Downloader {

        private Parser parser;
        private PageFetcher pageFetcher;

        public Downloader() {
                CrawlConfig config = new CrawlConfig();
                parser = new Parser(config);
                pageFetcher = new PageFetcher(config);
        }

        private Page download(String url) {
                WebURL curURL = new WebURL();
                curURL.setURL(url);
                PageFetchResult fetchResult = null;
                try {
                        fetchResult = pageFetcher.fetchHeader(curURL);
                        if (fetchResult.getStatusCode() == HttpStatus.SC_OK) {
                                try {
                                        Page page = new Page(curURL);
                                        fetchResult.fetchContent(page);
                                        if (parser.parse(page, curURL.getURL())) {
                                                return page;
                                        }
                                } catch (Exception e) {
                                        e.printStackTrace();
                                }
                        }
                } finally {
                        fetchResult.discardContentIfNotConsumed();
                }
                return null;
        }

        public void processUrl(String url) {
                System.out.println("Processing: " + url);
                Page page = download(url);
                if (page != null) {
                        ParseData parseData = page.getParseData();
                        if (parseData != null) {
                                if (parseData instanceof HtmlParseData) {
                                        HtmlParseData htmlParseData = (HtmlParseData) parseData;
                                        System.out.println("Title: " + htmlParseData.getTitle());
                                        System.out.println("Text length: " + htmlParseData.getText().length());
                                        System.out.println("Html length: " + htmlParseData.getHtml().length());
                                }
                        } else {
                                System.out.println("Couldn't parse the content of the page.");
                        }
                } else {
                        System.out.println("Couldn't fetch the content of the page.");
                }
                System.out.println("==============");
        }

        public static void main(String[] args) {
                Downloader downloader = new Downloader();
               downloader.processUrl("http://en.wikipedia.org/wiki/Main_Page/");
               downloader.processUrl("http://www.seu.edu.cn/");
               downloader.processUrl("http://www.yahoo.com/");
        }
}
