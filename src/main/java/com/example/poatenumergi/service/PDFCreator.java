package com.example.poatenumergi.service;

import com.example.poatenumergi.model.Food;
import com.example.poatenumergi.model.FoodCategory;
import com.example.poatenumergi.model.FoodDTO;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.util.List;
import java.util.stream.Stream;

public class PDFCreator {
    private static Logger LOGGER = LoggerFactory.getLogger(PDFCreator.class);
    /**
     *
     * @param filename the name of the file.
     * @param menuItems the dishes from the restaurant's menu.
     * @return status message.
     */
    public static String menuToPDF(String filename, List<FoodDTO> menuItems){
       try {
           System.out.println(menuItems);
           Document document = new Document();
           PdfWriter.getInstance(document, new FileOutputStream(filename+".pdf"));

           document.open();
           Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
           Paragraph title = new Paragraph("Menu of "+filename,font);
           document.add(title);
           document.add(Chunk.NEWLINE);
           document.add(new Paragraph("Categories:",font));
           for (FoodCategory el:
                   FoodCategory.values()) {
               document.add(new Paragraph(el.getCode(),font));
               for(FoodDTO foodDTO:menuItems){
                   if (foodDTO.getCategory().equals(el.getCode())){
                       document.add(new Paragraph(foodDTO.getName()));
                   }
               }
           }




           Paragraph p2 = new Paragraph("Come over and taste our dishes, or order in yover to get fabulous discounts! "+filename, font);
           document.add(p2);
           document.close();
       }
       catch (Exception e){
           LOGGER.info("The file: "+filename+ "could not be created.");
           e.printStackTrace();
           return "Could not create PDF.";
       }

       return "Success";
    }
}
