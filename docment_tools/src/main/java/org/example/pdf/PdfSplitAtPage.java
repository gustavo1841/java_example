package org.example.pdf;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;

public class PdfSplitAtPage {
    public static void main(String[] args) throws IOException {
        File file = new File("D:\\计算机\\JKSJ 高级Java工程师体系课 2.0\\07 并发编程（下）\\资料\\07-并发编程.pdf");

        // load pdf file
        PDDocument document = Loader.loadPDF(file);

        // instantiating Splitter
        Splitter splitter = new Splitter();

        splitter.setStartPage(14);

        // split the pages of a PDF document
        List<PDDocument> Pages = splitter.split(document);

        // Creating an iterator
        Iterator<PDDocument> iterator = Pages.listIterator();

        // saving splits as pdf
        int i = 0;
        while(iterator.hasNext()) {
            PDDocument pd = iterator.next();
            pd.save("E:\\新建文件夹\\"+ ++i +".pdf");
            System.out.println("E:\\新建文件夹\\"+ i +".pdf");
        }

        // close the document
        document.close();
    }
}
