package com.epl.blog.job;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.util.ArrayList;


public class ParsedSite {

    String urlSite;

    public ParsedSite(String urlSite) {
        this.urlSite = urlSite;
    }

    public String getUrlSite() {
        return urlSite;
    }

    public void setUrlSite(String urlSite) {
        this.urlSite = urlSite;
    }

    public ArrayList<String> parseLink (String classObj) throws IOException {
        this.urlSite = urlSite;
        ArrayList <String> dataList = new ArrayList<String>();
        Document doc = Jsoup.connect(urlSite).get();
        Elements elementsSite = doc.getElementsByClass(classObj);
        for (Element elem: elementsSite) {
            dataList.add(elem.text());
        }
        return dataList;
    }

    public ArrayList<String> parseTable (int numberPosition, String classObj, int countElement) throws IOException {
        this.urlSite = urlSite;
        Document doc = Jsoup.connect(urlSite).get();
        Element table = doc.select("table").first();
        Elements rows = table.select("tr");
        ArrayList <String> dataList = new ArrayList<String>();
        for (int i = 1; i < rows.size(); i++) {
            Element row = rows.get(i);
            Elements cols = row.select("td");
            if (countElement == 1) {
                dataList.add(cols.get(numberPosition).getElementsByClass(classObj).text());
            } else {
              String teamFirst = cols.get(numberPosition).getElementsByClass(classObj).get(0).text();
              String teamSecond= cols.get(numberPosition).getElementsByClass(classObj).get(1).text();
              dataList.add(teamFirst);
              dataList.add(teamSecond);

            }
        }
        return dataList;
    }
}
